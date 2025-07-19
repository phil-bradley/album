/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 *
 * Based on
 * https://github.com/Thanasis1101/Zoomable-Java-Panel
 *
 */
package ie.philb.album.ui.common;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author Philip.Bradley
 *
 * Draws an image centered at "best fit" zoom, that is, as large as it can be
 * while fitting within the boundary of the panel
 */
public class ImagePanel extends AppPanel {

    private BufferedImage sourceImage;
    private BufferedImage scaledImage;

    public ImagePanel(BufferedImage image) {
        setImage(image);
        addComponentListener(new ResizeListener());
    }

    public final void setImage(BufferedImage image) {
        this.sourceImage = image;
        this.scaledImage = createScaledImage();

        revalidate();
        repaint();
    }

    private BufferedImage createScaledImage() {

        if (sourceImage == null) {
            return null;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return null;
        }

        double scaleFactor = getBestFitScaleFactor();

        // Don't scale up
        if (scaleFactor > 1) {
            scaleFactor = 1;
        }

        int scaledWidth = (int) (sourceImage.getWidth() * scaleFactor);
        int scaledHeight = (int) (sourceImage.getHeight() * scaleFactor);

        BufferedImage scaled = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaled.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, scaledWidth, scaledHeight);
        g.drawImage(sourceImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();

        return scaled;
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
    private double getBestFitScaleFactor() {

        if (sourceImage == null) {
            return 1;
        }

        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();

        double availableWidth = getAvailableWidth();
        double availableHeight = getAvailableHeight();

        // Component not yet sized, cannot compute zoom factor
        if (availableWidth == 0 || availableHeight == 0) {
            return 0;
        }

        double bestFitZoom = Math.min(availableWidth / width, availableHeight / height);
        return bestFitZoom;

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (scaledImage == null) {
            this.scaledImage = createScaledImage();
        }

        if (scaledImage == null) {
            return;
        }

        // Centre image if it's less tall or less wide than the available space
        int x = (getAvailableWidth() - scaledImage.getWidth()) / 2;
        int y = (getAvailableHeight() - scaledImage.getHeight()) / 2;

        g.drawImage(scaledImage, x, y, null);
    }

    class ResizeListener extends ComponentAdapter {

        @Override
        public void componentResized(ComponentEvent e) {
            scaledImage = createScaledImage();
            revalidate();
        }
    }
}
