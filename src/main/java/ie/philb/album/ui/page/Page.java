/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.page;

import ie.philb.album.AppContext;
import ie.philb.album.ui.pagesizer.IsoPageSizer;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.ImagePanel;
import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 *
 * @author Philip.Bradley
 */
public class Page extends AppPanel {

    private ImagePanel imagePanel = new ImagePanel(null);
    private ImageIcon icon = null;

    public Page() {
        background(Color.WHITE);
        setLayout(null);
        add(imagePanel);

        AppContext.INSTANCE.addListener((ImageLibraryEntry entry) -> {
            icon = entry.getIcon();
            imagePanel.setIcon(icon);

            repaint();
            revalidate();
            repaint();
        });
    }

    public void setHeight(int height) {
        setSize(new IsoPageSizer().getWidthFromHeight(height), height);
    }

    public void setWidth(int width) {
        setSize(width, new IsoPageSizer().getHeightFromWidth(width));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int w = getWidth();
        int h = getHeight();

        int inset = 10;
        int width = w - inset * 2;
        int height = h - inset * 2;

        int x = inset;
        int y = inset;

        g.setColor(Color.BLUE);
        g.drawRect(x, y, width, height);

        imagePanel.setSize(width, height);
        imagePanel.setBounds(x, y, width, height);
    }
}
