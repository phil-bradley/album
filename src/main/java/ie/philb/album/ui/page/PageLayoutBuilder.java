/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.page;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philip.Bradley
 */
public class PageLayoutBuilder {

    private final int STD_MARGIN = 10; // 10 mm

    // Load this from resources/files.
    // For the moment, return a hard coded list
    public List<PageLayout> getPageLayouts() {

        List<PageLayout> pageLayouts = new ArrayList<>();
        pageLayouts.add(createA4FullPageSingle());

        return pageLayouts;
    }

    private PageLayout createA4FullPageSingle() {

        int entryWidth = PageSpecification.A4.width() - 2 * STD_MARGIN;
        int entryHeight = PageSpecification.A4.height() - 2 * STD_MARGIN;
        Dimension entrySize = new Dimension(entryWidth, entryHeight);

        PageEntry pageEntry = new PageEntryBuilder().size(entrySize).build();

        String layoutName = "A4 Single/Full";

        PageLayout pageLayout = new PageLayout(layoutName, PageSpecification.A4);
        pageLayout.inset(STD_MARGIN);
        pageLayout.getPageEntries().add(pageEntry);

        return pageLayout;
    }

    private PageLayout createA4Double() {

        int verticalDivideWidth = 10;

        int entryWidth = (PageSpecification.A4.width() - (2 * STD_MARGIN + verticalDivideWidth)) / 2;
        int entryHeight = PageSpecification.A4.height() - 2 * STD_MARGIN;

        Dimension entrySize = new Dimension(entryWidth, entryHeight);

        Point rightOffset = new Point(entryHeight + verticalDivideWidth, 0);

        PageEntry pageEntryLeft = new PageEntryBuilder().size(entrySize).build();
        PageEntry pageEntryRight = new PageEntryBuilder().size(entrySize).offset(rightOffset).build();

        String layoutName = "A4 Double/Full";

        PageLayout pageLayout = new PageLayout(layoutName, PageSpecification.A4);
        pageLayout.inset(STD_MARGIN);
        pageLayout.getPageEntries().add(pageEntryLeft);
        pageLayout.getPageEntries().add(pageEntryRight);

        return pageLayout;
    }
}
