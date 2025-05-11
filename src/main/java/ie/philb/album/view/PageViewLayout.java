/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.ui.page.PageSpecification;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author philb
 */
public class PageViewLayout {

    private final PageSpecification pageSpecification;
    private final List<PageEntryCoordinates> coordinates;

    private final int rows;
    private final int columns;
    private Insets margin = new Insets(10, 10, 10, 10);

    // Assume 10mm gap between cells vertically and horizontally
    private int cellVerticalGap = 10;
    private int cellHorizontalGap = 10;

    public PageViewLayout(PageSpecification pageSpecification, int rows, int columns) {
        this.coordinates = new ArrayList<>(rows * columns);
        this.pageSpecification = pageSpecification;
        this.rows = rows;
        this.columns = columns;

        initCoordinates();
    }

    public Insets getMargin() {
        return margin;
    }

    public PageViewLayout withMargin(int margin) {
        return withMargin(new Insets(margin, margin, margin, margin));
    }

    public PageViewLayout withMargin(Insets margin) {
        this.margin = margin;
        initCoordinates();
        return this;
    }

    public int getCellVerticalGap() {
        return cellVerticalGap;
    }

    public PageViewLayout withCellVerticalGap(int cellVerticalGap) {
        this.cellVerticalGap = cellVerticalGap;
        initCoordinates();
        return this;
    }

    public int getCellHorizontalGap() {
        return cellHorizontalGap;
    }

    public PageViewLayout withCellHorizontalGap(int cellHorizontalGap) {
        this.cellHorizontalGap = cellHorizontalGap;
        initCoordinates();
        return this;
    }

    public PageViewLayout withCellGap(int gap) {
        this.cellHorizontalGap = gap;
        this.cellVerticalGap = gap;
        initCoordinates();
        return this;
    }

    public int entryCount() {
        return (rows * columns);
    }

    private void initCoordinates() {

        this.coordinates.clear();

        int offsetX = margin.left;
        int offsetY = margin.top;
        int width = getCellWidth();
        int height = getCellHeight();

        for (int y = 0; y < getRows(); y++) {
            for (int x = 0; x < getColumns(); x++) {
                PageEntryCoordinates pec = new PageEntryCoordinates(offsetX, offsetY, width, height);
                this.coordinates.add(pec);
                offsetX += width + cellHorizontalGap;
            }
            offsetX = margin.left;
            offsetY += height + cellVerticalGap;
        }

        System.out.println("Coordinates: " + coordinates);
    }

    private int getCellWidth() {

        int interiorPageWidth = pageSpecification.width() - (margin.left + margin.right);
        int totalGapWidth = (getColumns() - 1) * cellHorizontalGap;

        int cellSpace = interiorPageWidth - totalGapWidth;
        return cellSpace / getColumns();
    }

    private int getCellHeight() {
        int interiorPageHeight = pageSpecification.height() - (margin.top + margin.bottom);
        int totalGapHeight = (getRows() - 1) * cellVerticalGap;

        int cellSpace = interiorPageHeight - totalGapHeight;
        return cellSpace / getRows();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<PageEntryCoordinates> getEntryCoordinates() {
        return coordinates;
    }

    public PageSpecification getPageSpecification() {
        return pageSpecification;
    }

    @Override
    public String toString() {
        return "PageViewLayout{" + "pageSpecification=" + pageSpecification + ", coordinates=" + coordinates + ", rows=" + rows + ", columns=" + columns + ", margin=" + margin + ", cellVerticalGap=" + cellVerticalGap + ", cellHorizontalGap=" + cellHorizontalGap + '}';
    }

}
