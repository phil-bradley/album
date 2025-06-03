/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import java.awt.Graphics2D;
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
    private BufferedImage zoomedImage = null;
    private double zoomFactor = 1;

    public PageEntryModel(PageCell cell) {
        this.cell = cell;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
        setZoomFactor(1);
        this.zoomedImage = createZoomedImage();

        for (var l : listeners) {
            l.imageUpdated();
        }
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

    public void setZoomFactor(double zoomFactor) {

        if (zoomFactor != this.zoomFactor) {
            this.zoomFactor = zoomFactor;
            this.zoomedImage = createZoomedImage();
        }

    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    private BufferedImage createZoomedImage() {

        if (zoomFactor <= 0) {
            return null;
        }

        int zoomedWidth = (int) (imageIcon.getIconWidth() * zoomFactor);
        int zoomedHeight = (int) (imageIcon.getIconHeight() * zoomFactor);

        BufferedImage zoomed = new BufferedImage(zoomedWidth, zoomedHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = zoomed.createGraphics();
        g.drawImage(imageIcon.getImage(), 0, 0, zoomedWidth, zoomedHeight, null);
        g.dispose();

        return zoomed;
    }

    public BufferedImage getZoomedImage() {
        return zoomedImage;
    }

    public BufferedImage getImageWithViewZoomScale(double viewZoomScale) {

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

    public void addListener(PageEntryModelListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(PageEntryModelListener listener) {
        this.listeners.remove(listener);
    }
}
