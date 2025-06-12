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
import java.io.File;
import java.io.IOException;
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
    private File imageFile;
    private double zoomFactor = 1;
    private Point offset = new Point(0, 0);

    public PageEntryModel(PageCell cell) {
        this.cell = cell;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
        try {
            if (imageFile != null) {
                this.imageIcon = new ImageIcon(imageFile.getCanonicalPath());
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to set image", ex);
        }
        setZoomFactor(1);
        fireImageUpdated();
    }

    public File getImageFile() {
        return imageFile;
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

    private BufferedImage getPlacholderImage(Dimension viewSize) {

        // Return a placholder image only if it fits inside the available
        // area with a margin of 10%
        BufferedImage img = ImageUtils.getPlaceholderImage();

        int hMargin = viewSize.width / 10;
        int vMargin = viewSize.height / 10;
        int margin = Math.max(vMargin, hMargin);

        if (canFitImageWithMargin(img, viewSize, margin)) {
            return img;
        } else {
            return null;
        }
    }

    public BufferedImage getViewImage(Dimension viewSize) {

        if (imageIcon == null) {
            return getPlacholderImage(viewSize);
        }

        BufferedImage scaled = getImageWithViewZoomScale(getBestFitZoomFactor(viewSize));

        int cropWidth = Math.min(viewSize.width, scaled.getWidth());
        int cropHeight = Math.min(viewSize.height, scaled.getHeight());
        Dimension cropSize = new Dimension(cropWidth, cropHeight);

//        int xOffset = 0;
//        int yOffset = 0;
//
//        if (viewSize.width < scaled.getWidth()) {
//            xOffset = (scaled.getWidth() - viewSize.width) / 2;
//        }
//
//        if (viewSize.height < scaled.getHeight()) {
//            yOffset = (scaled.getHeight() - viewSize.height) / 2;
//        }
        LOG.info("Image has size {}x{} offset is {}", scaled.getWidth(), scaled.getHeight(), offset);
        BufferedImage cropped = ImageUtils.getSubimage(scaled, offset, cropSize);
        //scaled.getSubimage(offset.x, offset.y, cropWidth - offset.x, cropHeight - offset.y);
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

    private boolean canFitImageWithMargin(BufferedImage viewImage, Dimension viewSize, int margin) {

        int availableWidth = viewSize.width - (2 * margin);
        int availableHeight = viewSize.height - (2 * margin);

        if (viewImage.getWidth() > availableWidth) {
            return false;
        }

        if (viewImage.getHeight() > availableHeight) {
            return false;
        }

        return true;
    }
}
