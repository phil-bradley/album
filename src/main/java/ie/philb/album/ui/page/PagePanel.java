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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philip.Bradley
 */
public class PagePanel extends AppPanel implements ImageEntrySelectionListener {

    private final List<PagePanelEntry> pagePanelEntries = new ArrayList<>();
    private boolean isSelected = false;
    private final PageLayout pageLayout;

    public PagePanel(PageLayout pageLayout) {
        this.pageLayout = pageLayout;
        background(Color.WHITE);
        setLayout(null);

        for (PageEntry entry : pageLayout.getPageEntries()) {
            PagePanelEntry ppe = new PagePanelEntry();
            ppe.imagePanel = new ImagePanel(null);
            ppe.pageEntry = entry;
            pagePanelEntries.add(ppe);
            add(ppe.imagePanel);
        }

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

        for (PagePanelEntry ppe : pagePanelEntries) {
            paintEntry(g, ppe);
        }

    }

    private void paintEntry(Graphics g, PagePanelEntry pagePanelEntry) {

        PageEntry pageEntry = pagePanelEntry.pageEntry;
        ImagePanel imagePanel = pagePanelEntry.imagePanel;

        // First find the scaling factor to convert from mm to our size on screen
        double millisToPx = (double) (pageLayout.getPageSpecification().width()) / (double) getWidth();

        int scaledWidth = (int) (pageEntry.getWidth() / millisToPx);
        int scaledHeight = (int) (pageEntry.getHeight() / millisToPx);

        int x = (int) (pageEntry.getOffsetX() / millisToPx);
        int y = (int) (pageEntry.getOffsetY() / millisToPx);

//        int marginOffsetX = (int) ((double) (pageLayout.getInsets().left) / millisToPx);
//        int marginOffsetY = (int) ((double) (pageLayout.getInsets().top) / millisToPx);

//        int x = entryOffsetX + marginOffsetX;
//        int y = entryOffsetY + marginOffsetY;

        imagePanel.setSize(scaledWidth, scaledHeight);
        imagePanel.setBounds(x, y, scaledWidth, scaledWidth);

        Color penColor = (isSelected) ? Color.orange : Color.BLUE;
        g.setColor(penColor);
        g.drawRect(x, y, scaledWidth, scaledHeight);

        logger.info("Component has size " + getSize() + ", drawrect size {} {}", scaledWidth, scaledHeight);
    }

    @Override
    public void imageSelected(ImageLibraryEntry entry) {

        for (PagePanelEntry pagePanelEntry : pagePanelEntries) {
            pagePanelEntry.imagePanel.setIcon(entry.getIcon());
        }

        repaint();
        revalidate();
        repaint();
    }

    class PagePanelEntry {

        ImagePanel imagePanel;
        PageEntry pageEntry;
    }
}
