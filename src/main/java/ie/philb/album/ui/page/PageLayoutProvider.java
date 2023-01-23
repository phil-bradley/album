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
public class PageLayoutProvider {

    private final int STD_MARGIN = 10; // 10 mm

    // Load this from resources/files.
    // For the moment, return a hard coded list
    public List<PageLayout> getPageLayouts() {

        int padding = 8;

        List<PageLayout> pageLayouts = new ArrayList<>();
        pageLayouts.add(createA4Grid(1, 1, padding));
        pageLayouts.add(createA4Grid(1, 2, padding));
        pageLayouts.add(createA4Grid(1, 3, padding));
        pageLayouts.add(createA4Grid(2, 1, padding));
        pageLayouts.add(createA4Grid(2, 2, padding));
        pageLayouts.add(createA4Grid(2, 3, padding));

        return pageLayouts;
    }

    private PageLayout createA4Grid(int rows, int columns, int padding) {

        String name = "Grid : " + rows + " x " + columns;

        PageLayout pageLayout = new PageLayout(name, PageSpecification.A4Landscape);
        pageLayout.inset(STD_MARGIN);

        Point offset = new Point(STD_MARGIN, STD_MARGIN);

        int entryHeight = getEntrySize(PageSpecification.A4Landscape.height(), STD_MARGIN, padding, rows);

        for (int y = 0; y < rows; y++) {

            for (int x = 0; x < columns; x++) {

                int entryWidth = getEntrySize(PageSpecification.A4Landscape.width(), STD_MARGIN, padding, columns);

                Dimension entrySize = new Dimension(entryWidth, entryHeight);

                PageEntry pageEntry = new PageEntryBuilder().size(entrySize).offset(offset).build();
                pageLayout.getPageEntries().add(pageEntry);

                offset.x += entryWidth + padding;
            }

            offset.x = STD_MARGIN;
            offset.y += entryHeight + padding;
        }

        return pageLayout;
    }

    // Given a  page width (or height), work out the width (or height) of a 
    // single entry accounting for margin and padding
    private int getEntrySize(int pageSize, int margin, int padding, int entries) {

        int sizeInsideMargin = pageSize - margin * 2;
        int totalPadding = (entries - 1) * padding;
        int unpaddedSize = sizeInsideMargin - totalPadding;
        int entrySize = unpaddedSize / entries;

        return entrySize;
    }

}
