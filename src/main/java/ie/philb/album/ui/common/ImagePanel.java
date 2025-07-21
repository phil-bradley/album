/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 *
 * Based on
 * https://github.com/Thanasis1101/Zoomable-Java-Panel
 *
 */
package ie.philb.album.ui.common;

import ie.philb.album.util.ImageUtils;
import java.awt.Dimension;
import java.awt.Graphics;
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
        this.scaledImage = ImageUtils.scaleImageToFit(sourceImage, getSize());

        revalidate();
        repaint();
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

        return ImageUtils.getBestFitScaleFactor(sourceImage, new Dimension(getAvailableWidth(), getAvailableHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (scaledImage == null) {
            this.scaledImage = ImageUtils.scaleImageToFit(sourceImage, getSize());
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
            scaledImage = ImageUtils.scaleImageToFit(sourceImage, getSize());
            revalidate();
        }
    }
}
