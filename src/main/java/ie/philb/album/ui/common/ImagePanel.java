/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philip.Bradley
 */
public class ImagePanel extends AppPanel implements MouseWheelListener, MouseListener, MouseMotionListener {

    private static final Logger LOG = LoggerFactory.getLogger(ImagePanel.class);

    private final List<ZoomablePanelListener> listeners = new ArrayList<>();

    private ImageIcon imageIcon;
    private BufferedImage zoomedImage;
    private ImagePanelFill fill = ImagePanelFill.BestFit;
    private double zoomFactor = 1;
    private double prevZoomFactor = 1;
    private boolean zoomer;
    private boolean isDragging;
    private boolean released;
    private int xOffset = 0;
    private int yOffset = 0;
    private int xDiff;
    private int yDiff;
    private Point startPoint;
    private Point currPoint;
    private boolean isPlaceholderImage = true;
    private boolean hasManualZoom = false;

    public ImagePanel() {
        this(null);
        setPlaceholderImage();
    }

    public ImagePanel(ImageIcon icon) {
        setIcon(icon);
        initComponent();

//        addComponentListener(new ResizeListener());
    }

    private void initComponent() {
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    private void setPlaceholderImage() {

        this.zoomFactor = 1;
        this.imageIcon = new ImageIcon(this.getClass().getResource("/ie/philb/album/placeholder.png"));
        this.zoomedImage = getZoomedImage();
        this.isPlaceholderImage = true;

    }

    private void setZoomFactor(double zoomFactor) {

        if (hasManualZoom) {
            return;
        }

        LOG.info("Zoom factor is {}, setting to {}", this.zoomFactor, zoomFactor);

        if (this.zoomFactor == 0.9411764705882353 && zoomFactor == 0.855614973262032) {
            LOG.info("");
        }
        this.zoomFactor = zoomFactor;

        if (zoomFactor != 0) {
            this.zoomedImage = getZoomedImage();
        }
    }

    public final void setIcon(ImageIcon icon) {

        this.imageIcon = icon;

        if (icon != null) {
            setZoomFactor(getBestFitZoomFactor());
            isPlaceholderImage = false;
        }

        repaint();
    }

    private BufferedImage getZoomedImage() {

        int zoomedWidth = (int) (imageIcon.getIconWidth() * zoomFactor);
        int zoomedHeight = (int) (imageIcon.getIconHeight() * zoomFactor);

        BufferedImage zoomed = new BufferedImage(zoomedWidth, zoomedHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = zoomed.createGraphics();
        g.drawImage(imageIcon.getImage(), 0, 0, zoomedWidth, zoomedHeight, null);
        g.dispose();

        return zoomed;
    }

    public ImageIcon getIcon() {
        return imageIcon;
    }

    public ImagePanelFill getFill() {
        return fill;
    }

    public void setFill(ImagePanelFill fill) {
        this.fill = fill;
    }

    private int getAvailableWidth() {
        int availableWidth = getBounds().width;
        return availableWidth;
    }

    private int getAvailableHeight() {
        int availableHeight = getBounds().height;
        return availableHeight;
    }

    // This is the zoom factor at which the image is as large as possible
    // without any cropping
    private double getBestFitZoomFactor() {

        if (imageIcon == null) {
            return 1;
        }

        int iconWidth = imageIcon.getIconWidth();
        int iconHeight = imageIcon.getIconHeight();

        double availableWidth = getAvailableWidth();
        double availableHeight = getAvailableHeight();

        // Component not yet sized, cannot compute zoom factor
        if (availableWidth == 0 || availableHeight == 0) {
            return 0;
        }

        double bestFitZoom = Math.min(availableWidth / iconWidth, availableHeight / iconHeight);

        int targetWidth = (int) (iconWidth * bestFitZoom);
        int targetHeight = (int) (iconHeight * bestFitZoom);

        //LOG.info("Got best fit zoom factor {}, size {}x{}, target {}x{}, Available {}x{}", bestFitZoom, iconWidth, iconHeight, targetWidth, targetHeight, (int) availableWidth, (int) availableHeight);
        return bestFitZoom;

    }

    private void drawCropped(Graphics g) {

        BufferedImage cropped = getCroppedImage();

        // Centre image if it's less tall or less wide than the available space
        int x = (getAvailableWidth() - cropped.getWidth()) / 2;
        int y = (getAvailableHeight() - cropped.getHeight()) / 2;

        g.drawImage(cropped, x, y, null);
    }

    public BufferedImage getCroppedImage() {

        int cropWidth = getCropWidth();
        int cropHeight = getCropHeight();

        int w = xOffset + xDiff;
        int h = yOffset + yDiff;

        BufferedImage cropped = zoomedImage.getSubimage(0, 0, cropWidth, cropHeight);
        return cropped;
    }

    private int getCropWidth() {
        int boundWidth = getBounds().width;
        int cropWidth = Math.min(boundWidth, zoomedImage.getWidth());
        return cropWidth;
    }

    private int getCropHeight() {
        int boundHeight = getBounds().height;
        int cropHeight = Math.min(boundHeight, zoomedImage.getHeight());
        return cropHeight;
    }

    @Override
    protected void paintComponent(Graphics g) {

        if (zoomFactor == 0) {
            setZoomFactor(getBestFitZoomFactor());
        }

        super.paintComponent(g);

        if (imageIcon == null) {
            return;
        }

        if (zoomFactor == 0) {
            return;
        }

        drawCropped(g);
    }

    private void resetImage() {
        xOffset = 0;
        xDiff = 0;
        yOffset = 0;
        yDiff = 0;
        repaint();
    }

    public void zoomIn() {
        hasManualZoom = false;
        setZoomFactor(zoomFactor * 1.1);
        hasManualZoom = true;
        repaint();
    }

    public void zoomOut() {
        hasManualZoom = false;
        setZoomFactor(zoomFactor / 1.1);
        hasManualZoom = true;
        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        zoomer = true;

        if (e.getWheelRotation() < 0) {
            zoomIn();
        }

        if (e.getWheelRotation() > 0) {
            zoomOut();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

//        currPoint = e.getLocationOnScreen();
//
//        int xDragDiff = startPoint.x - currPoint.x;
//        int yDragDiff = startPoint.y - currPoint.y;
//
//        int cropWidth = getCropWidth();
//        int cropHeight = getCropHeight();
//
//        int w = xOffset + xDragDiff;
//        int h = yOffset + yDragDiff;
//
//        if ((w > 0) && ((2 * w) + cropWidth < getScaledImage().getWidth())) {
//            xDiff = xDragDiff;
//        }
//
//        if ((h > 0) && ((2 * h) + cropHeight < getScaledImage().getHeight())) {
//            yDiff = yDragDiff;
//        }
//
//        isDragging = true;
//        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getClickCount() == 2) {
            resetImage();
            return;
        }

        for (ZoomablePanelListener l : listeners) {
            l.zoomPanelClicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        released = false;
        startPoint = MouseInfo.getPointerInfo().getLocation();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        released = true;
        isDragging = false;

        xOffset += xDiff;
        yOffset += yDiff;

        xDiff = 0;
        yDiff = 0;

        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void addListener(ZoomablePanelListener l) {
        listeners.add(l);
    }

    public void removeListener(ZoomablePanelListener l) {
        listeners.remove(l);
    }

//    class ResizeListener extends ComponentAdapter {
//
//        public void componentResized(ComponentEvent e) {
//
//            if (isPlaceholderImage) {
//                return;
//            }
//
//            setZoomFactor(getBestFitZoomFactor());
//        }
//    }
}
