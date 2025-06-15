/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.AppContext;
import ie.philb.album.model.PageCell;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageEntryModelListener;
import ie.philb.album.model.PageGeometryMapper;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.Resources;
import ie.philb.album.ui.dnd.PageEntryViewTransferHandler;
import ie.philb.album.util.ImageUtils;
import static ie.philb.album.util.ImageUtils.getImageSize;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author philb
 */
public class PageEntryView extends AppPanel implements PageEntryModelListener {

    private static final Logger LOG = LoggerFactory.getLogger(PageEntryView.class);

    private Point mouseDragStartPoint = new Point(0, 0);
    private Point mouseDragPreviousPoint;
    private Point viewOffset = new Point(0, 0);

    private final PageEntryModel pageEntryModel;
    private boolean isSelected = false;
    private boolean isPreviewMode = false;
    private final PageView pageView;
    private boolean canResize = false;

    public PageEntryView(PageView pageView, PageEntryModel entryModel) {

        super();

        background(Color.white);
        setFocusable(true);
        this.pageView = pageView;
        this.pageEntryModel = entryModel;
        this.pageEntryModel.addListener(this);

        setTransferHandler(new PageEntryViewTransferHandler());
        updateBorder();
    }

    public PageEntryModel getPageEntryModel() {
        return pageEntryModel;
    }

    public void centerImage() {

        if (pageEntryModel.getImage() == null) {
            return;
        }

        PageGeometryMapper geometryMapper = getPageGeometryMapper();
        BufferedImage image = pageEntryModel.getScaledImage(getSize(), geometryMapper);
        setViewOffset(ImageUtils.getCenteredCoordinates(getImageSize(image), getSize()));
        pageEntryModel.setCentered(true);
    }

    public void resetImagePosition() {
        setViewOffset(new Point(0, 0));
    }

    private void setViewOffset(Point offset) {
        this.viewOffset = offset;

        PageGeometryMapper geometryMapper = getPageGeometryMapper();
        Point modelOffset = new Point(geometryMapper.viewUnitsToPoints(viewOffset.x), geometryMapper.viewUnitsToPoints(viewOffset.y));
        pageEntryModel.setImageViewOffset(modelOffset);

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Dimension viewSize = new Dimension(getBounds().width, getBounds().height);
        BufferedImage viewImage = pageEntryModel.getViewImage(viewSize, getPageGeometryMapper());

        PageGeometryMapper geometryMapper = getPageGeometryMapper();

        Point offset = new Point(0, 0);//  ImageUtils.getCenteredCoordinates(viewImage, viewSize);
        offset.x += geometryMapper.pointsToViewUnits(pageEntryModel.getOffset().x);
        offset.y += geometryMapper.pointsToViewUnits(pageEntryModel.getOffset().y);

        LOG.info("ViewSize: {}x{}, Model offset is {},{} scaling to {},{}", viewSize.width, viewSize.height, pageEntryModel.getOffset().x, pageEntryModel.getOffset().y, offset.x, offset.y);
        int x = Math.max(0, offset.x);
        int y = Math.max(0, offset.y);

        g.drawImage(viewImage, x, y, null);

    }

    private void updateBorder() {
        setBorder(BorderFactory.createLineBorder(getBorderColor()));
        repaint();
    }

    private Color getBorderColor() {
        return isSelected ? Resources.COLOR_PHOTO_BORDER_SELECTED : Resources.COLOR_PHOTO_BORDER;
    }

    public void setSelected(boolean b) {

        if (isPreviewMode) {
            return;
        }

        this.isSelected = b;
        updateBorder();
    }

    public PageCell getPageCell() {
        return pageEntryModel.getCell();
    }

    @Override
    public void imageUpdated() {
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent me) {

        if (isPreviewMode) {
            return;
        }

        AppContext.INSTANCE.pageEntrySelected(pageView, this);

        if (pageEntryModel.getImage() == null) {
            return;
        }

        canResize = true;
        mouseDragStartPoint = MouseInfo.getPointerInfo().getLocation();
        mouseDragPreviousPoint = mouseDragStartPoint;

        this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        LOG.info("Start drag at " + mouseDragStartPoint);
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        canResize = false;
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseDragged(MouseEvent me) {

        if (isPreviewMode) {
            return;
        }

        if (pageEntryModel.getImage() == null) {
            return;
        }

        Point mouseDragCurrentPoint = MouseInfo.getPointerInfo().getLocation();
        LOG.info("Dragged from " + mouseDragStartPoint + " to " + mouseDragCurrentPoint);

        int xDragOffset = mouseDragCurrentPoint.x - mouseDragPreviousPoint.x;
        int yDragOffset = mouseDragCurrentPoint.y - mouseDragPreviousPoint.y;

        Point offset = new Point(viewOffset.x + xDragOffset, viewOffset.y + yDragOffset);
        setViewOffset(offset);

        mouseDragPreviousPoint = mouseDragCurrentPoint;
        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        if (!canResize) {
            return;
        }

        if (isPreviewMode) {
            return;
        }

        if (pageEntryModel.getImage() == null) {
            return;
        }

        //Zoom in
        if (e.getWheelRotation() < 0) {
            zoomIn();
            repaint();
        }
        //Zoom out
        if (e.getWheelRotation() > 0) {
            zoomOut();
            repaint();
        }
    }

    public void setPreviewMode(boolean previewMode) {
        this.isPreviewMode = previewMode;
        setFocusable(!isPreviewMode);
    }

    @Override
    public void pageEntrySelected(PageView pageView, PageEntryView pageEntryView) {
        setSelected(this.equals(pageEntryView));
    }

    private PageGeometryMapper getPageGeometryMapper() {
        Dimension viewSize = new Dimension(getBounds().width, getBounds().height);
        PageGeometryMapper geometryMapper = new PageGeometryMapper(pageView.getPageModel(), viewSize);
        return geometryMapper;
    }

    public void zoomToCoverFit() {
        resetImagePosition();
        pageEntryModel.zoomToCoverFit(getSize());
        centerImage();
    }

    public void zoomToFit() {
        pageEntryModel.resetZoom();
        centerImage();
    }

    public PageView getPageView() {
        return pageView;
    }

    public void zoomIn() {
        pageEntryModel.zoomIn();
        if (pageEntryModel.isCentered()) {
            centerImage();
        }
    }

    public void zoomOut() {
        pageEntryModel.zoomOut();
        if (pageEntryModel.isCentered()) {
            centerImage();
        }
    }
}
