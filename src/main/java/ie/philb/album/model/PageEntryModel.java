/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import ie.philb.album.AppContext;
import ie.philb.album.ui.common.filters.BrightnessFilter;
import ie.philb.album.ui.common.filters.GrayScaleFilter;
import ie.philb.album.ui.common.textcontrol.TextControlModel;
import ie.philb.album.util.ImageUtils;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private BufferedImage image = null;
    private File imageFile;
    private double zoomFactor = 1;
    private Point offset = new Point(0, 0);
    private boolean isCentered = false;
    private Dimension physicalSize = new Dimension(0, 0);
    private final TextControlModel textControlModel = new TextControlModel();
    private boolean isGrayScale = false;
    private LocalDateTime lastChanged = LocalDateTime.now();
    private final BrightnessFilter brightnessFilter = new BrightnessFilter();
    private final GrayScaleFilter grayScaleFilter = new GrayScaleFilter();

    public PageEntryModel(PageCell cell) {
        this.cell = cell;
    }

    public PageEntryType getPageEntryType() {
        return cell.pageEntryType();
    }

    public void setPageEntryType(PageEntryType pageEntryType) {
        if (pageEntryType != this.cell.pageEntryType()) {
            this.cell.pageEntryType(pageEntryType);
            fireTextUpdated();
        }
    }

    public Dimension getPhysicalSize() {
        return physicalSize;
    }

    public void setPhysicalSize(Dimension physicalSize) {
        this.physicalSize = physicalSize;
    }

    public int getBrightnessAdjustment() {
        return brightnessFilter.getBrightnessAdjustment();
    }

    public void setBrightnessAdjustment(int brightnessAdjustment) {
        brightnessFilter.setBrightnessAdjustment(brightnessAdjustment);
        fireImageUpdated();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
        try {
            if (imageFile != null) {
                image = ImageUtils.readBufferedImage(imageFile);
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
        resetOffset();
        setZoomFactor(getCoverFitZoomFactor(viewSize));
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

        if (image == null) {
            return ImageUtils.getEmptyImage();
        }

        if (zoomFactor <= 0) {
            return ImageUtils.getEmptyImage();
        }

        double combinedZoomFactor = zoomFactor * viewZoomScale;
        int zoomedWidth = (int) (image.getWidth() * combinedZoomFactor);
        int zoomedHeight = (int) (image.getHeight() * combinedZoomFactor);

        if (zoomedWidth == 0 || zoomedHeight == 0) {
            return ImageUtils.getEmptyImage();
        }

        BufferedImage zoomed = new BufferedImage(zoomedWidth, zoomedHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = zoomed.createGraphics();
        g.drawImage(image, 0, 0, zoomedWidth, zoomedHeight, null);
        g.dispose();

        return brightnessFilter(grayScaleFilter(zoomed));
    }

    private BufferedImage grayScaleFilter(BufferedImage image) {

        if (isGrayScale()) {
            return grayScaleFilter.filter(image);
        }

        return image;
    }

    private BufferedImage brightnessFilter(BufferedImage image) {
        return brightnessFilter.filter(image);
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

    public BufferedImage getScaledImage(Dimension viewSize, PageGeometryMapper geometryMapper) {
        BufferedImage scaled = getImageWithViewZoomScale(getBestFitZoomFactor(viewSize));
        return scaled;
    }

    public BufferedImage getViewImage(Dimension viewSize, PageGeometryMapper geometryMapper) {

        if ((image == null) && (getPageEntryType() == PageEntryType.Image)) {
            return getPlacholderImage(viewSize);
        }

        BufferedImage scaled = getScaledImage(viewSize, geometryMapper);

        Point viewOffset = new Point();
        viewOffset.x = geometryMapper.pointsToViewUnits(offset.x);
        viewOffset.y = geometryMapper.pointsToViewUnits(offset.y);

        LOG.info("Image has size {}x{} offset is {}, cropping to {}x{}", scaled.getWidth(), scaled.getHeight(), viewOffset, viewSize.width, viewSize.height);
        BufferedImage cropped = ImageUtils.getSubimage(scaled, viewOffset, viewSize);
        return cropped;
    }

    // This is the zoom factor at which the image is as large as possible
    // without any cropping
    private double getBestFitZoomFactor(Dimension viewSize) {

        if (image == null) {
            return 1;
        }

        // Component not yet sized, cannot compute zoom factor
        if (viewSize.width == 0 || viewSize.height == 0) {
            return 0;
        }

        double width = image.getWidth();
        double height = image.getHeight();

        double bestFitZoom = Math.min(viewSize.width / width, viewSize.height / height);
        return bestFitZoom;

    }

    // This is the smallest zoom factor at which the image fills the available space,
    // cropped if necessary (cover fit)
    private double getCoverFitZoomFactor(Dimension viewSize) {

        if (image == null) {
            return 1;
        }

        double imageWidth = (double) image.getWidth();
        double imageHeight = (double) image.getHeight();
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

        lastChanged = LocalDateTime.now();

        for (var l : listeners) {
            l.imageUpdated();
        }

        AppContext.INSTANCE.pageEntryUpdated(this);
    }

    private void fireTextUpdated() {

        lastChanged = LocalDateTime.now();

        for (var l : listeners) {
            l.textUpdated();
        }

        AppContext.INSTANCE.pageEntryUpdated(this);
    }

    public void setImageViewOffset(Point offset) {

        if (!Objects.equals(offset, this.offset)) {
            this.offset = offset;
            this.isCentered = false;
            fireImageUpdated();
        }
    }

    public void resetOffset() {
        setImageViewOffset(new Point(0, 0));
    }

    public Point getImageViewOffset() {
        return offset;
    }

    private boolean canFitImageWithMargin(BufferedImage viewImage, Dimension viewSize, int margin) {

        int availableWidth = viewSize.width - (2 * margin);
        int availableHeight = viewSize.height - (2 * margin);

        if (viewImage.getWidth() > availableWidth) {
            return false;
        }

        return viewImage.getHeight() <= availableHeight;
    }

    public boolean isCentered() {
        return isCentered;
    }

    public void setCentered(boolean b) {
        this.isCentered = b;
    }

    public TextControlModel getTextControlModel() {
        return textControlModel;
    }

    public boolean isGrayScale() {
        return isGrayScale;
    }

    public void setGrayScale(boolean isGray) {
        this.isGrayScale = isGray;
        fireImageUpdated();
    }

    LocalDateTime getLastChangeDate() {
        return lastChanged;
    }

    @Override
    public String toString() {
        return "PageEntryModel{" + "cell=" + cell + '}';
    }
}
