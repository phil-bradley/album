/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.ImagePanel;
import ie.philb.album.ui.common.Resources;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

/**
 *
 * @author philb
 */
public class PageEntryView extends AppPanel {

    private ImagePanel imagePanel;

    public PageEntryView() {
        imagePanel = new ImagePanel(new ImageIcon("/home/philb/Pictures/3.jpeg"));
//        background(Color.orange);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // First find the scaling factor to convert from mm to our size on screen
//        double millisToPx = (double) (pageLayout.getPageSpecification().width()) / (double) getWidth();
//
//        int scaledWidth = (int) (pageEntry.getWidth() / millisToPx);
//        int scaledHeight = (int) (pageEntry.getHeight() / millisToPx);
//
//        int x = (int) (pageEntry.getOffsetX() / millisToPx);
//        int y = (int) (pageEntry.getOffsetY() / millisToPx);
//
//        if (imagePanel.getIcon() == null) {
//            imagePanel.setIcon(pageEntry.getIcon());
//        }
//
//        imagePanel.setSize(scaledWidth, scaledHeight);
//        imagePanel.setBounds(x, y, scaledWidth, scaledHeight);
//        imagePanel.repaint();
//
        Color penColor = Resources.COLOR_PHOTO_BORDER;
//
//        if (isPageSelected && selectedEntry == pagePanelEntry) {
//            penColor = Resources.COLOR_PHOTO_BORDER_SELECTED;
//        }
//
//        g.setColor(penColor);
//        g.drawRect(0, 0, 100, 100);
        //logger.info("Component has size " + getSize() + ", drawrect size {} {}", scaledWidth, scaledHeight);
    }
}
