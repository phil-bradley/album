/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.page;

import ie.philb.album.AppContext;
import ie.philb.album.AppListener;
import ie.philb.album.ui.pagesizer.IsoPageSizer;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.ImagePanel;
import ie.philb.album.ui.imagelibrary.ImageEntrySelectionListener;
import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

/**
 *
 * @author Philip.Bradley
 */
public class PagePanel extends AppPanel implements ImageEntrySelectionListener {

    private ImagePanel imagePanel = new ImagePanel(null);
    private ImageIcon icon = null;
    private boolean isSelected = false;

    public PagePanel() {
        background(Color.WHITE);
        setLayout(null);
        add(imagePanel);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                logger.info("Setting image entry listener to " + PagePanel.this);
                AppContext.INSTANCE.setImageEntryListener(PagePanel.this);
                setSelected(true);
            }
        });

        AppContext.INSTANCE.addListener(new AppListener() {
            @Override
            public void listenerSelected(ImageEntrySelectionListener listener) {
                setSelected(false);
            }

        });
    }

    private void setSelected(boolean selected) {
        this.isSelected = selected;
        repaint();
        revalidate();
        repaint();
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

        Color penColor = (isSelected) ? Color.orange : Color.BLUE;
        g.setColor(penColor);
        g.drawRect(x, y, width, height);

        imagePanel.setSize(width, height);
        imagePanel.setBounds(x, y, width, height);
    }

    @Override
    public void imageSelected(ImageLibraryEntry entry) {

        icon = entry.getIcon();
        imagePanel.setIcon(icon);

        repaint();
        revalidate();
        repaint();
    }
}
