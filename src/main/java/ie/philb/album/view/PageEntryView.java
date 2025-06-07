/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.AppContext;
import ie.philb.album.model.PageCell;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageEntryModelListener;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.Resources;
import ie.philb.album.ui.dnd.PageEntryViewTransferHandler;
import ie.philb.album.util.ImageUtils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
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

    private Point mouseDragStartPoint;
    private Point mouseDragPreviousPoint;

    private final PageEntryModel pageEntryModel;
    private boolean isSelected = false;
    private boolean isPreviewMode = false;

    public PageEntryView(PageEntryModel entryModel) {

        super();
        background(Color.white);
        setFocusable(true);
        this.pageEntryModel = entryModel;
        this.pageEntryModel.addListener(this);

        setTransferHandler(new PageEntryViewTransferHandler());
        updateBorder();
    }

    public PageEntryModel getPageEntryModel() {
        return pageEntryModel;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Dimension viewSize = new Dimension(getBounds().width, getBounds().height);
        BufferedImage viewImage = pageEntryModel.getViewImage(viewSize);

        Point offset = ImageUtils.getCenteredCoordinates(viewImage, viewSize);
        offset.x += pageEntryModel.getOffset().x;
        offset.y += pageEntryModel.getOffset().y;

        g.drawImage(viewImage, offset.x, offset.y, null);
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

        if (isSelected) {
            AppContext.INSTANCE.imageEntrySelected(this);
        }
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

        setSelected(true);

        if (pageEntryModel.getImageIcon() == null) {
            return;
        }

        mouseDragStartPoint = MouseInfo.getPointerInfo().getLocation();
        mouseDragPreviousPoint = mouseDragStartPoint;
        LOG.info("Start drag at " + mouseDragStartPoint);
    }

    @Override
    public void mouseDragged(MouseEvent me) {

        if (isPreviewMode) {
            return;
        }

        if (pageEntryModel.getImageIcon() == null) {
            return;
        }

        Point mouseDragCurrentPoint = MouseInfo.getPointerInfo().getLocation();
        LOG.info("Dragged from " + mouseDragStartPoint + " to " + mouseDragCurrentPoint);

        int xOffset = mouseDragCurrentPoint.x - mouseDragPreviousPoint.x;
        int yOffset = mouseDragCurrentPoint.y - mouseDragPreviousPoint.y;

        Point dragOffset = new Point(xOffset, yOffset);
        pageEntryModel.addImageViewOffset(dragOffset);

        mouseDragPreviousPoint = mouseDragCurrentPoint;
        repaint();
    }

    public void setPreviewMode(boolean previewMode) {
        this.isPreviewMode = previewMode;
    }
}
