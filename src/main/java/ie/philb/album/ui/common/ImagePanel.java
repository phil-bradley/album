/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 *
 * Based on
 * https://github.com/Thanasis1101/Zoomable-Java-Panel
 *
 */
package ie.philb.album.ui.common;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philip.Bradley
 */
public class ImagePanel extends AppPanel {

    private static final Logger LOG = LoggerFactory.getLogger(ImagePanel.class);

    private ImageIcon imageIcon;
    private BufferedImage zoomedImage;
    private double zoomFactor = 1;

    public ImagePanel(ImageIcon icon) {
        setIcon(icon);
        addComponentListener(new ResizeListener());
    }

    private void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;

        if (zoomFactor != 0) {
            this.zoomedImage = createZoomedImage();
        }
    }

    public final void setIcon(ImageIcon icon) {

        this.imageIcon = icon;

        if (icon != null) {
            setZoomFactor(getBestFitZoomFactor());
        }

        repaint();
    }

    private BufferedImage createZoomedImage() {

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

    class ResizeListener extends ComponentAdapter {

        @Override
        public void componentResized(ComponentEvent e) {
            setZoomFactor(getBestFitZoomFactor());
            revalidate();
        }
    }
}
