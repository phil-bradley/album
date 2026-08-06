/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.Context;
import ie.philb.album.model.PageCell;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageEntryModelListener;
import ie.philb.album.model.PageEntryType;
import ie.philb.album.model.PageGeometryMapper;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.common.textcontrol.TextControl;
import ie.philb.album.ui.common.textcontrol.TextControlChangeListener;
import ie.philb.album.ui.common.textcontrol.TextControlModel;
import ie.philb.album.ui.dnd.PageEntryViewTransferHandler;
import ie.philb.album.ui.resources.Colors;
import ie.philb.album.util.ImageUtils;
import static ie.philb.album.util.ImageUtils.getImageSize;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author philb
 */
public class PageEntryView extends AppPanel implements PageEntryModelListener, TextControlChangeListener {

    private static final Logger LOG = LoggerFactory.getLogger(PageEntryView.class);

    protected Point mouseDragStartPoint = new Point(0, 0);
    protected Point mouseDragPreviousPoint;
    protected Point viewOffset = new Point(0, 0);

    private final PageEntryModel pageEntryModel;
    private boolean isSelected = false;
    private boolean isPreviewMode = false;
    private final PageView pageView;
    private boolean canResize = false;
    private final TextControl textControl;

    public PageEntryView(Context context, PageView pageView, PageEntryModel pageEntryModel) {

        super(context);

        this.pageView = pageView;
        this.pageEntryModel = pageEntryModel;

        textControl = new TextControl(pageEntryModel.getTextControlModel());
        textControl.setPhysicalSize(pageEntryModel.getPhysicalSize());

        if (pageEntryModel.getPageEntryType() == PageEntryType.Text) {
            addTextControl();
        }

        background(Color.white);
        setFocusable(true);

        this.pageEntryModel.addListener(this);
        this.pageEntryModel.getTextControlModel().addChangeListener(this);

        initKeyBindings();
        setTransferHandler(new PageEntryViewTransferHandler(context));
        updateBorder();
    }

    private void initKeyBindings() {

        InputMap im = getInputMap(WHEN_FOCUSED);

        ActionMap am = getActionMap();
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "panUp");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "panDown");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "panLeft");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "panRight");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0), "zoomIn");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0), "zoomOut");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "clearImage");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "clearImage");

        am.put("panUp", new PanAction(0, -1));
        am.put("panDown", new PanAction(0, 1));
        am.put("panLeft", new PanAction(-1, 0));
        am.put("panRight", new PanAction(1, 0));

        am.put("zoomIn", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoomIn();
            }
        });

        am.put("zoomOut", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoomOut();
            }
        });

        am.put("clearImage", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearImage();
            }
        });
    }

    @Override
    public void mouseClicked(MouseEvent me) {

        requestFocusInWindow();

        if (pageEntryModel.getPageEntryType() != PageEntryType.Image) {
            return;
        }

        if (pageEntryModel.getImage() == null) {
            return;
        }

        if (me.getClickCount() == 2) {
            toggleZoom();
        }
    }

    // If zoomed to fit then zoom to cover and vice versa
    private void toggleZoom() {

        Dimension boundarySize = getSize();
        Dimension imageSize = ImageUtils.getImageSize(pageEntryModel.getScaledImage(boundarySize, getPageGeometryMapper()));

        if (ImageUtils.isSnappedFitMinimum(imageSize, boundarySize)) {
            zoomToCoverFit();
            return;
        }

        zoomToFit();
    }

    private void addTextControl() {

        // Check if control already added
        if (this.isAncestorOf(textControl)) {
            return;
        }

        add(textControl, new GridBagCellConstraints().weight(1).fillBoth());
        textControl.setSize(getSize());
        repaint();
    }

    public PageEntryModel getPageEntryModel() {
        return pageEntryModel;
    }

    public void centerImage() {

        PageGeometryMapper geometryMapper = getPageGeometryMapper();

        BufferedImage image;

        if (pageEntryModel.getImage() == null) {
            image = ImageUtils.getPlaceholderImage();
        } else {
            image = pageEntryModel.getScaledImage(getSize(), geometryMapper);

        }

        setViewOffset(ImageUtils.getCenteredCoordinates(getImageSize(image), getSize()));
        pageEntryModel.setCentered(true);
    }

    public void resetViewOffset() {
        setViewOffset(new Point(0, 0));
    }

    private void setViewOffset(Point offset) {

        if (!Objects.equals(offset, this.viewOffset)) {
            this.viewOffset = offset;

            PageGeometryMapper geometryMapper = getPageGeometryMapper();
            Point modelOffset = new Point(geometryMapper.viewUnitsToPoints(viewOffset.x), geometryMapper.viewUnitsToPoints(viewOffset.y));
            pageEntryModel.setImageViewOffset(modelOffset);

            System.out.println("Updated offset: " + this.viewOffset);
        }

        revalidate();
        repaint();
    }

    protected BufferedImage getViewImage() {
        Dimension viewSize = new Dimension(getBounds().width, getBounds().height);
        BufferedImage viewImage = pageEntryModel.getViewImage(viewSize, getPageGeometryMapper());
        return viewImage;
    }

    protected Point getViewImageOffset() {

        if (pageEntryModel.getImage() == null) {
            return ImageUtils.getCenteredCoordinates(ImageUtils.getImageSize(ImageUtils.getPlaceholderImage()), getSize());
        }

        PageGeometryMapper geometryMapper = getPageGeometryMapper();

        Point offset = geometryMapper.locationAsPointsToViewUnits(pageEntryModel.getImageViewOffset());

        int x = Math.max(0, offset.x);
        int y = Math.max(0, offset.y);

        return new Point(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (pageEntryModel.getPageEntryType() == PageEntryType.Text) {
            return;
        }

        BufferedImage viewImage = getViewImage();

        if (viewImage != null) {
            Point viewImageOffset = getViewImageOffset();
            g.drawImage(ImageUtils.transparentToWhiteBackground(viewImage), viewImageOffset.x, viewImageOffset.y, null);
        }
    }

    private void updateEditor() {

        if (isPreviewMode) {
            return;
        }

        if (!isSelected) {
            textControl.setEditEnabled(false);
        }
    }

    private void updateBorder() {
        setBorder(BorderFactory.createLineBorder(getBorderColor()));
    }

    private Color getBorderColor() {
        return isSelected ? Colors.COLOR_PHOTO_BORDER_SELECTED : Colors.COLOR_PHOTO_BORDER;
    }

    public void setSelected(boolean b) {

        if (isPreviewMode) {
            return;
        }

        this.isSelected = b;
        updateBorder();
        updateEditor();

        requestFocusInWindow();
    }

    public boolean isSelected() {
        return isSelected;
    }

    public PageCell getPageCell() {
        return pageEntryModel.getCell();
    }

    @Override
    public void imageUpdated() {
        repaint();
    }

    @Override
    public void textUpdated() {
        if (pageEntryModel.getPageEntryType() == PageEntryType.Text) {
            addTextControl();

            if (!isPreviewMode) {
                textControl.setEditEnabled(true);
                textControl.requestFocus();
            }

        } else {
            textControl.setEditEnabled(false);
            remove(textControl);
        }

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent me) {

        if (isPreviewMode) {
            return;
        }

        context.session().pageEntrySelected(pageView, this);

        if (pageEntryModel.getImage() == null) {
            return;
        }

        canResize = true;
        mouseDragStartPoint = me.getPoint();

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

        Point mouseDragCurrentPoint = me.getPoint();
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
        if (textControl != null) {
            textControl.setPreviewMode(previewMode);
        }
        setFocusable(!isPreviewMode);
    }

    @Override
    public void pageEntrySelected(PageView pageView, PageEntryView pageEntryView) {

        boolean pageMatches = false;
        boolean cellMatches = false;

        if (pageView != null && pageEntryView != null) {
            pageMatches = (this.pageView.getPageModel().getPageId() == pageEntryView.getPageView().getPageModel().getPageId());
            cellMatches = this.pageEntryModel.getCell().location().equals(pageEntryView.getPageEntryModel().getCell().location());
        }

        boolean isSelectedPageEntryView = pageMatches && cellMatches;
        setSelected(isSelectedPageEntryView);
    }

    private PageGeometryMapper getPageGeometryMapper() {
        PageGeometryMapper geometryMapper = new PageGeometryMapper(pageView.getPageModel(), pageView.getSize());
        return geometryMapper;
    }

    public void zoomToCoverFit() {
        resetViewOffset();
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

    @Override
    public void textEditSelected(TextControlModel textControlModel) {
        if (!isPreviewMode) {
            context.session().getEventBus().pageEntrySelected(pageView, this);
        }
    }

    @Override
    public String toString() {
        return "Page " + pageView.getPageModel().getPageId() + ", Cell: " + pageEntryModel.getCell() + ", isPreview: " + isPreviewMode;
    }

    public void setPhysicalToViewScalingFactor(double physicalToViewScalingFactor) {
        textControl.setPhysicalToViewScalingFactor(physicalToViewScalingFactor);
    }

    public void clearImage() {
        pageEntryModel.setImageFile(null);
        resetViewOffset();
    }

    public void shiftOffset(int x, int y) {

        setViewOffset(new Point(
                viewOffset.x + x,
                viewOffset.y + y
        ));
    }

    class PanAction extends AbstractAction {

        private final int dx;
        private final int dy;

        public PanAction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            
            if (pageEntryModel.getImage() == null) {
                return;
            }
            
            setViewOffset(new Point(
                    viewOffset.x + dx,
                    viewOffset.y + dy
            ));
        }

    }
}
