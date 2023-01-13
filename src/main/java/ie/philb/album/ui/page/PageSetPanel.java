/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.page;

import ie.philb.album.ui.pagesizer.IsoPageSizer;
import ie.philb.album.ui.common.AppPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philip.Bradley
 */
public class PageSetPanel extends AppPanel {

    private static final Logger logger = LoggerFactory.getLogger(PageSetPanel.class);

    private final List<PagePanel> pages = new ArrayList<>();

    public PageSetPanel() {
        background(Color.GRAY);
        setLayout(null);

        addPage(new PagePanel());
        addPage(new PagePanel());
        addPage(new PagePanel());
        addPage(new PagePanel());
        addPage(new PagePanel());

    }

    public final void addPage(PagePanel page) {
        pages.add(page);
        add(page);
        revalidate();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    public void positionPages() {

        int defaultInset = 10;
        int parentWidth = getParent().getWidth();
        int parentHeight = getParent().getHeight();

        IsoPageSizer ips = new IsoPageSizer();

        int maxPageWidth = parentWidth - defaultInset * 2;
        int maxPageHeight = parentHeight - defaultInset * 2;

        int pageWidth = Math.min(maxPageWidth, ips.getWidthFromHeight(maxPageHeight));
        int pageHeight = Math.min(maxPageHeight, ips.getHeightFromWidth(maxPageWidth));

        int horizontalInset = defaultInset;
        int verticalInset = (parentHeight - pageHeight) / 2;

        for (PagePanel page : pages) {
            page.setWidth(pageWidth);
            page.setBounds(horizontalInset, verticalInset, page.getWidth(), page.getHeight());
            horizontalInset += pageWidth + defaultInset;
        }

        setPreferredSize(new Dimension(horizontalInset, parentHeight));
        logger.info("Resized: Page container has size {}x{}, Page has size {}x{}", horizontalInset, parentHeight, pageWidth, pageHeight);

    }

}
