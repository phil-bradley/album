/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.page;

import ie.philb.album.AppContext;
import ie.philb.album.ui.pagesizer.IsoPageSizer;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.BoundsChecker;
import ie.philb.album.ui.common.ImagePanel;
import ie.philb.album.ui.common.ZoomablePanel;
import ie.philb.album.ui.common.ZoomablePanelListener;
import ie.philb.album.ui.imagelibrary.ImageEntrySelectionListener;
import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import ie.philb.album.util.ImageUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a page in the book with 1 or more image entries in a M x N
 * arrangement
 *
 * @author Philip.Bradley
 */
public class PagePanel extends AppPanel implements ImageEntrySelectionListener, ZoomablePanelListener {

    private final List<PagePanelEntry> pagePanelEntries = new ArrayList<>();
    private boolean isPageSelected = false;
    private PagePanelEntry selectedEntry = null;

    private final PageLayout pageLayout;

    public PagePanel(PageLayout pageLayout) {
        this.pageLayout = pageLayout;
        background(Color.WHITE);
        setLayout(null);

        for (PageEntry entry : pageLayout.getPageEntries()) {
            PagePanelEntry ppe = new PagePanelEntry();
            ppe.imagePanel = new ImagePanel();
            ppe.imagePanel.addListener(this);
            ppe.pageEntry = entry;
            pagePanelEntries.add(ppe);
            add(ppe.imagePanel);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                PagePanel.this.selectedEntry = getSelectedEntry(getMousePosition());

                logger.info("Setting image entry listener to " + PagePanel.this);
                AppContext.INSTANCE.setImageEntryListener(PagePanel.this);
                setPageSelected(true);
            }
        });

        AppContext.INSTANCE.addListener((ImageEntrySelectionListener listener) -> {
            setPageSelected(false);
        });
    }

    private PagePanelEntry getSelectedEntry(Point mousePosition) {

        for (PagePanelEntry pagePanelEntry : pagePanelEntries) {

            Rectangle r = pagePanelEntry.imagePanel.getBounds();
            BoundsChecker boundsChecker = new BoundsChecker(r);

            if (boundsChecker.isBounded(mousePosition)) {
                return pagePanelEntry;
            }
        }

        return null;
    }

    private void setPageSelected(boolean selected) {
        this.isPageSelected = selected;
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

        if (imagePanel.getIcon() == null) {
            imagePanel.setIcon(pageEntry.getIcon());
        }

        imagePanel.setSize(scaledWidth, scaledHeight);
        imagePanel.setBounds(x, y, scaledWidth, scaledHeight);
        imagePanel.repaint();

        Color penColor = Color.LIGHT_GRAY;

        if (isPageSelected && selectedEntry == pagePanelEntry) {
            penColor = Color.ORANGE;
        }

        g.setColor(penColor);
        g.drawRect(x, y, scaledWidth, scaledHeight);

        //logger.info("Component has size " + getSize() + ", drawrect size {} {}", scaledWidth, scaledHeight);
    }

    @Override
    public void imageSelected(ImageLibraryEntry entry) {

        for (PagePanelEntry pagePanelEntry : pagePanelEntries) {

            if (selectedEntry == pagePanelEntry) {
                pagePanelEntry.pageEntry.setIcon(entry.getIcon());
                pagePanelEntry.imagePanel.setIcon(entry.getIcon());
            }
        }

        repaint();
        revalidate();
        repaint();
    }

    @Override
    public void zoomPanelClicked(MouseEvent event) {
        PagePanel.this.selectedEntry = getSelectedEntry(getMousePosition());

        logger.info("Setting image entry listener to " + PagePanel.this);
        AppContext.INSTANCE.setImageEntryListener(PagePanel.this);
        setPageSelected(true);
    }

    class PagePanelEntry {

        ImagePanel imagePanel;
        PageEntry pageEntry;
    }
}
