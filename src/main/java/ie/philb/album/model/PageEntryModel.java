/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import ie.philb.album.util.ImageUtils;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author philb
 */
public class PageEntryModel {

    private static final Logger LOG = LoggerFactory.getLogger(PageEntryModel.class);

    private final List<PageEntryModelListener> listeners = new ArrayList<>();
    private final PageCell cell;
    private ImageIcon imageIcon;
    private double zoomFactor = 1;
    private Point offset = new Point(0, 0);

    public PageEntryModel(PageCell cell) {
        this.cell = cell;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
        setZoomFactor(1);
        fireImageUpdated();
    }

    public PageCell getCell() {
        return cell;
    }

    public void zoomIn() {
        setZoomFactor(zoomFactor * 1.1);
    }

    public void zoomOut() {
        setZoomFactor(zoomFactor / 1.1);
    }

    public void resetZoom() {
        setZoomFactor(1);
        resetOffset();
    }

    public void zoomToCoverFit(Dimension viewSize) {
        setZoomFactor(getCoverFitZoomFactor(viewSize));
        resetOffset();
    }

    public void setZoomFactor(double zoomFactor) {

        if (zoomFactor != this.zoomFactor) {
            this.zoomFactor = zoomFactor;
            fireImageUpdated();
        }
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    private BufferedImage getImageWithViewZoomScale(double viewZoomScale) {

        if (imageIcon == null) {
            return null;
        }

        if (zoomFactor <= 0) {
            return null;
        }

        double combinedZoomFactor = zoomFactor * viewZoomScale;
        int zoomedWidth = (int) (imageIcon.getIconWidth() * combinedZoomFactor);
        int zoomedHeight = (int) (imageIcon.getIconHeight() * combinedZoomFactor);

        BufferedImage zoomed = new BufferedImage(zoomedWidth, zoomedHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = zoomed.createGraphics();
        g.drawImage(imageIcon.getImage(), 0, 0, zoomedWidth, zoomedHeight, null);
        g.dispose();

        return zoomed;
    }

    public BufferedImage getViewImage(Dimension viewSize) {

        if (imageIcon == null) {
            return ImageUtils.getPlaceholderImage();
        }

        BufferedImage scaled = getImageWithViewZoomScale(getBestFitZoomFactor(viewSize));

        int cropWidth = Math.min(viewSize.width, scaled.getWidth());
        int cropHeight = Math.min(viewSize.height, scaled.getHeight());

        int xOffset = 0;
        int yOffset = 0;

        if (viewSize.width < scaled.getWidth()) {
            xOffset = (scaled.getWidth() - viewSize.width) / 2;
        }

        if (viewSize.height < scaled.getHeight()) {
            yOffset = (scaled.getHeight() - viewSize.height) / 2;
        }

        BufferedImage cropped = scaled.getSubimage(xOffset, yOffset, cropWidth, cropHeight);
        return cropped;
    }

    // This is the zoom factor at which the image is as large as possible
    // without any cropping
    private double getBestFitZoomFactor(Dimension viewSize) {

        if (imageIcon == null) {
            return 1;
        }

        // Component not yet sized, cannot compute zoom factor
        if (viewSize.width == 0 || viewSize.height == 0) {
            return 0;
        }

        double iconWidth = imageIcon.getIconWidth();
        double iconHeight = imageIcon.getIconHeight();

        double bestFitZoom = Math.min(viewSize.width / iconWidth, viewSize.height / iconHeight);
        int scaledWidth = (int) (iconWidth * bestFitZoom);
        int scaledHeight = (int) (iconHeight * bestFitZoom);

        LOG.info("Got best fit zoom factor {}, size {}x{}, Available {}x{}, Scaled {}x{}", bestFitZoom, iconWidth, iconHeight, viewSize.width, viewSize.height, scaledWidth, scaledHeight);
        return bestFitZoom;

    }

    // This is the smallest zoom factor at which the image fills the available space,
    // cropped if necessary (cover fit)
    private double getCoverFitZoomFactor(Dimension viewSize) {

        if (imageIcon == null) {
            return 1;
        }

        double imageWidth = (double) imageIcon.getIconWidth();
        double imageHeight = (double) imageIcon.getIconHeight();
        double viewWidth = (double) viewSize.getWidth();
        double viewHeight = (double) viewSize.getHeight();

        double viewAspectRatio = viewWidth / viewHeight;

        // Scale the height so that the width fits
        double targetHeight = imageWidth / viewAspectRatio;
        double verticalScale = targetHeight / imageHeight;

        // Scale the width so that the height fits
        double targetWidth = imageHeight * viewAspectRatio;
        double horizontalScale = targetWidth / imageWidth;

        return Math.max(verticalScale, horizontalScale);
    }

    public void addListener(PageEntryModelListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(PageEntryModelListener listener) {
        this.listeners.remove(listener);
    }

    private void fireImageUpdated() {
        for (var l : listeners) {
            l.imageUpdated();
        }
    }

    public void addImageViewOffset(Point dragOffset) {
        this.offset.x += dragOffset.x;
        this.offset.y += dragOffset.y;
    }

    public void resetOffset() {
        this.offset.x = 0;
        this.offset.y = 0;
        fireImageUpdated();
    }

    public Point getOffset() {
        return offset;
    }
}
