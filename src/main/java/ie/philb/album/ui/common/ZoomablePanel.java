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
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Thanasis1101
 * @version 1.0
 *
 * See https://github.com/Thanasis1101/Zoomable-Java-Panel
 *
 */
public class ZoomablePanel extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener {

    private BufferedImage image;

    private double zoomFactor = 1;
    private double prevZoomFactor = 1;
    private boolean zoomer;
    private boolean isDragging;
    private boolean released;
    private double xOffset = 0;
    private double yOffset = 0;
    private int xDiff;
    private int yDiff;
    private Point startPoint;
    private Point currPoint;

    private final List<ZoomablePanelListener> listeners = new ArrayList<>();

    public ZoomablePanel() {
        this(null);
    }

    public ZoomablePanel(BufferedImage image) {

        this.image = image;
        initComponent();
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    private void initComponent() {
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

//        if (zoomer) {
//            AffineTransform at = new AffineTransform();
//
//            double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
//            double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();
//
//            double zoomDiv = zoomFactor / prevZoomFactor;
//
//            xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
//            yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;
//
//            at.translate(xOffset, yOffset);
//            at.scale(zoomFactor, zoomFactor);
//            prevZoomFactor = zoomFactor;
//            g2.transform(at);
//            zoomer = false;
//        }
//
//        if (isDragging) {
//            AffineTransform at = new AffineTransform();
//            at.translate(xOffset + xDiff, yOffset + yDiff);
//            at.scale(zoomFactor, zoomFactor);
//            g2.transform(at);
//
            if (released) {
                xOffset += xDiff;
                yOffset += yDiff;

                xDiff = 0;
                yDiff = 0;
            }
//            if (released) {
//                isDragging = false;
//            }
//
//        }

        int x = (int) (xOffset + xDiff);
        int y = (int) (yOffset + yDiff);

        System.out.println("xOffset: " + xOffset + ", xDiff: " + xDiff + ", x: " + x);

        // All drawings go here
        g2.drawImage(image,
                0, 0, getBounds().width, getBounds().height,
                -x, -y, getBounds().width - x, getBounds().height - y,
                this);

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
        xDiff = currPoint.x - startPoint.x;
        yDiff = currPoint.y - startPoint.y;

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
