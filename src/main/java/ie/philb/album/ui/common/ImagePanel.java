/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
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

/**
 *
 * @author Philip.Bradley
 */
public class ImagePanel extends AppPanel implements MouseWheelListener, MouseListener, MouseMotionListener {

    private final List<ZoomablePanelListener> listeners = new ArrayList<>();

    private ImageIcon imageIcon;
    private ImagePanelFill fill = ImagePanelFill.CropToFit;
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

    public ImagePanel() {
        this(null);
    }

    public ImagePanel(ImageIcon icon) {
        this.imageIcon = icon;

        initComponent();
    }

    private void initComponent() {
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public void setIcon(ImageIcon icon) {
        this.imageIcon = icon;
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
        Insets insets = getInsets();
        int availableWidth = getBounds().width - insets.left - insets.right;
        return availableWidth;
    }

    private int getAvailableHeight() {
        Insets insets = getInsets();
        int availableHeight = getBounds().height - insets.top - insets.bottom;
        return availableHeight;
    }

    private double getScale() {

        int iconWidth = imageIcon.getIconWidth();
        int iconHeight = imageIcon.getIconHeight();

        if (fill == ImagePanelFill.CropToFit) {
            return Math.min(iconWidth / (double) getAvailableWidth(), iconHeight / (double) getAvailableHeight());

        }

        if (fill == ImagePanelFill.BestFit) {
            return Math.max(iconWidth / (double) getAvailableWidth(), iconHeight / (double) getAvailableHeight());
        }

        return 1;
    }

    private BufferedImage getScaledImage() {

        if (imageIcon == null) {
            return null;
        }

        int iconWidth = imageIcon.getIconWidth();
        int iconHeight = imageIcon.getIconHeight();

        double scale = getScale();

        int scaledWidth = (int) (iconWidth / scale);
        int scaledHeight = (int) (iconHeight / scale);

        BufferedImage scaled = getScaledInstance(imageIcon.getImage(), scaledWidth, scaledHeight);
        return scaled;
    }

    private void drawBestFit(BufferedImage image, Graphics g) {
        int x = (getAvailableWidth() - image.getWidth()) / 2 + getInsets().left;
        int y = 0;
        g.drawImage(image, x, y, null);
    }

    private int getCropWidth(BufferedImage image) {
        int boundWidth = getBounds().width;
        int cropWidth = Math.min(boundWidth, image.getWidth());
        return cropWidth;
    }

    private int getCropHeight(BufferedImage image) {
        int boundHeight = getBounds().height;
        int cropHeight = Math.min(boundHeight, image.getHeight());
        return cropHeight;
    }

    private void drawCropped(BufferedImage image, Graphics g) {

        int cropWidth = getCropWidth(image);
        int cropHeight = getCropHeight(image);

        int w = xOffset + xDiff;
        int h = yOffset + yDiff;

        Image cropped = image.getSubimage(w, h, cropWidth + w, cropHeight + h);

        int x = (getAvailableWidth() - cropWidth) / 2 + getInsets().left;
        int y = 0;

        g.drawImage(cropped, x, y, null);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (imageIcon == null) {
            return;
        }

        BufferedImage scaled = getScaledImage();

        if (fill == ImagePanelFill.BestFit) {
            drawBestFit(scaled, g);
        }

        if (fill == ImagePanelFill.CropToFit) {
            drawCropped(scaled, g);
        }

    }

    private BufferedImage getScaledInstance(Image img, int width, int height) {

        BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaled.createGraphics();
        g.drawImage(img, 0, 0, width, height, null);
        g.dispose();

        return scaled;
    }

    private void resetImage() {
        xOffset = 0;
        xDiff = 0;
        yOffset = 0;
        yDiff = 0;
        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        zoomer = true;

        //Zoom in
        if (e.getWheelRotation() < 0) {
            zoomFactor *= 1.1;
            repaint();
        }
        //Zoom out
        if (e.getWheelRotation() > 0) {
            zoomFactor /= 1.1;
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        currPoint = e.getLocationOnScreen();

        int xDragDiff = startPoint.x - currPoint.x;
        int yDragDiff = startPoint.y - currPoint.y;

        int cropWidth = getCropWidth(getScaledImage());
        int cropHeight = getCropHeight(getScaledImage());

        int w = xOffset + xDragDiff;
        int h = yOffset + yDragDiff;

        if ((w > 0) && ((2 * w) + cropWidth < getScaledImage().getWidth())) {
            xDiff = xDragDiff;
        }

        if ((h > 0) && ((2 * h) + cropHeight < getScaledImage().getHeight())) {
            yDiff = yDragDiff;
        }

        isDragging = true;
        repaint();

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
}
