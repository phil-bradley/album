/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import ie.philb.album.util.MathUtils;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author philb
 */
public class PageGeometry {

    public static final int MAX_CELL_WIDTH = 12;
    public static final int MAX_CELL_HEIGHT = 12;

    private final List<PageCell> cells = new ArrayList<>();

    private PageGeometry() {
    }

    public List<PageCell> getCells() {
        return Collections.unmodifiableList(cells);
    }

    public int horizontalCellCount() {

        int maxWidth = 1;

        for (PageCell cell : cells) {
            int cellOffset = cell.location().x;
            maxWidth = Math.max(maxWidth, cellOffset + cell.size().width);
        }

        return maxWidth;
    }

    public int verticalCellCount() {

        int maxHeight = 1;

        for (PageCell cell : cells) {
            int cellOffset = cell.location().y;
            maxHeight = Math.max(maxHeight, cellOffset + cell.size().height);
        }

        return maxHeight;
    }

    public static PageGeometry rectangle(int width, int height) {
        return rectangle(PageEntryType.Image, width, height);
    }

    /*
     * Creates a rectangular layout, width x height cells
     */
    public static PageGeometry rectangle(PageEntryType pageEntryType, int width, int height) {

        validateWidthAndHeight(width, height);

        PageGeometry pageGeometry = new PageGeometry();

        Dimension pageSize = new Dimension(1, 1);

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                Point location = new Point(w, h);
                pageGeometry.cells.add(new PageCell(pageSize, location, pageEntryType));
            }
        }

        return pageGeometry;
    }

    public static PageGeometry square(PageEntryType pageEntryType, int size) {
        return rectangle(pageEntryType, size, size);
    }

    public static PageGeometry square(int size) {
        return rectangle(PageEntryType.Image, size, size);
    }

    /*
     * Allows for non rectangular arrangement.
     * For examples, calling this with arguments 2, 1, 3 would result
     * in the layout below
     *
     *     -------------
     *     |  A  |  B  |
     *     |-----------|
     *     |     C     |
     *     |-----------|
     *     | D | E | F |
     *     -------------
     *
     * The LCM of 2, 1, 3 = 6 and therefore, the page has a width
     * of 6 cells.
     * Cells A,B have width = 3
     * Cell C has width = 6
     * Cells D,E,F have width = 2
     *
     * All cells have height = 1
     */
    public static PageGeometry withRows(PageEntryType pageEntryType, int... cellCounts) {

        PageGeometry pageGeometry = new PageGeometry();

        int pageCellWidth = 1;

        for (int cellCount : cellCounts) {
            pageCellWidth = MathUtils.lcm(pageCellWidth, cellCount);
        }

        for (int row = 0; row < cellCounts.length; row++) {
            for (int col = 0; col < cellCounts[row]; col++) {
                int cellWidth = pageCellWidth / cellCounts[row];
                Dimension cellSize = new Dimension(cellWidth, 1);
                Point cellLocation = new Point(col * cellWidth, row);

                pageGeometry.cells.add(new PageCell(cellSize, cellLocation, pageEntryType));
            }
        }

        return pageGeometry;
    }

    public static PageGeometry withRows(int... cellCounts) {
        return withRows(PageEntryType.Image, cellCounts);
    }

    public static PageGeometry withColumns(PageEntryType pageEntryType, int... cellCounts) {

        PageGeometry pageGeometry = new PageGeometry();

        int pageCellHeight = 1;

        for (int cellCount : cellCounts) {
            pageCellHeight = MathUtils.lcm(pageCellHeight, cellCount);
        }

        for (int col = 0; col < cellCounts.length; col++) {
            for (int row = 0; row < cellCounts[col]; row++) {
                int cellHeight = pageCellHeight / cellCounts[col];
                Dimension cellSize = new Dimension(1, cellHeight);

                pageGeometry.cells.add(new PageCell(cellSize, new Point(col, row * cellHeight), pageEntryType));
            }
        }

        return pageGeometry;
    }

    public static PageGeometry withColumns(int... cellCounts) {
        return withColumns(PageEntryType.Image, cellCounts);
    }

    private static void validateWidthAndHeight(int width, int height) {

        if (width <= 0) {
            throw new RuntimeException("Cannot create page of width " + width + " cells");
        }

        if (height <= 0) {
            throw new RuntimeException("Cannot create page of height " + height + " cells");
        }

        if (width > MAX_CELL_WIDTH) {
            throw new RuntimeException("Cannot create page of width " + width + " cells");
        }

        if (height > MAX_CELL_HEIGHT) {
            throw new RuntimeException("Cannot create page of height " + height + " cells");
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.cells);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final PageGeometry other = (PageGeometry) obj;

        if (this.cells.size() != other.cells.size()) {
            return false;
        }

        int i = 0;
        for (PageCell cell : cells) {
            PageCell otherCell = other.cells.get(i);

            if (!cell.equals(otherCell)) {
                return false;
            }
            i++;
        }

        return true;
    }

}
