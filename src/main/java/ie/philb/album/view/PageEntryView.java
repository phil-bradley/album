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
import java.awt.Color;
import java.awt.Graphics;
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

    private final PageEntryModel pageEntryModel;
    private boolean isSelected = false;
    private double bestFitScalingFactor;

    public PageEntryView(PageEntryModel entryModel) {

        super();
        setFocusable(true);
        this.pageEntryModel = entryModel;
        this.pageEntryModel.addListener(this);
        updateBorder();
    }

    @Override
    protected void paintComponent(Graphics g) {

//        if (pageEntryModel.getCell().location().equals(new Point(1, 2))) {
//            System.out.println("Painting " + pageEntryModel.getCell().location());
//        }
        if (pageEntryModel.getZoomedImage() == null) {
            return;
        }

        this.bestFitScalingFactor = getBestFitFactor();

        drawCropped(g);
        super.paintComponent(g);
    }

    private void drawCropped(Graphics g) {

        BufferedImage cropped = getCroppedImage();

        // Centre image if it's less tall or less wide than the available space
        int x = (getAvailableWidth() - cropped.getWidth()) / 2;
        int y = (getAvailableHeight() - cropped.getHeight()) / 2;

        g.drawImage(cropped, x, y, null);
    }

    private BufferedImage getCroppedImage() {

        BufferedImage scaled = pageEntryModel.getImageWithViewZoomScale(bestFitScalingFactor);

        int cropWidth = Math.min(getBounds().width, scaled.getWidth());
        int cropHeight = Math.min(getBounds().height, scaled.getHeight());

        BufferedImage cropped = scaled.getSubimage(0, 0, cropWidth, cropHeight);
        return cropped;
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
    private double getBestFitFactor() {

        BufferedImage zoomed = pageEntryModel.getZoomedImage();

        if (zoomed == null) {
            return 1;
        }

        int iconWidth = zoomed.getWidth();
        int iconHeight = zoomed.getHeight();

        double availableWidth = getAvailableWidth();
        double availableHeight = getAvailableHeight();

        // Component not yet sized, cannot compute zoom factor
        if (availableWidth == 0 || availableHeight == 0) {
            return 0;
        }

        double bestFitZoom = Math.min(availableWidth / iconWidth, availableHeight / iconHeight);
        int scaledWidth = (int) (iconWidth * bestFitZoom);
        int scaledHeight = (int) (iconHeight * bestFitZoom);

        LOG.info("Got best fit zoom factor {}, size {}x{}, Available {}x{}, Scaled {}x{}", bestFitZoom, iconWidth, iconHeight, (int) availableWidth, (int) availableHeight, scaledWidth, scaledHeight);
        return bestFitZoom;

    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        setSelected(!isSelected);
        repaint();
    }

    private void updateBorder() {
        setBorder(BorderFactory.createLineBorder(getBorderColor()));
        repaint();
    }

    private Color getBorderColor() {
        return isSelected ? Resources.COLOR_PHOTO_BORDER_SELECTED : Resources.COLOR_PHOTO_BORDER;
    }

//    public void setImage(ImageIcon image) {
//        this.pageEntryModel.setImageIcon(image);
//        repaint();
//    }
    public void setSelected(boolean b) {
        this.isSelected = b;
        updateBorder();

        if (isSelected) {
            AppContext.INSTANCE.imageEntrySelected(this);
        }
    }

    public PageCell getPageCell() {
        return pageEntryModel.getCell();
    }

    public void zoomIn() {
        this.pageEntryModel.zoomIn();
    }

    public void zoomOut() {
        this.pageEntryModel.zoomOut();
    }

    @Override
    public void imageUpdated() {
        LOG.info("Image updated on cell {} with view size {}, isVisible: {}", pageEntryModel.getCell(), getSize(), isVisible());
        repaint();
        paintComponent(getGraphics());
    }
}
