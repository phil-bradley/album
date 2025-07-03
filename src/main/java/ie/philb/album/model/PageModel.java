/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author philb
 */
public class PageModel {

    private int pageId;
    private int marginPoints = 10;
    private PageGeometry geometry;
    private final List<PageEntryModel> pageEntries = new ArrayList<>();
    private final PageSize pageSize;

    public PageModel(PageGeometry geometry, PageSize pageSize) {
        this.pageSize = pageSize;
        setGeometry(geometry);
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public PageGeometry getGeometry() {
        return geometry;
    }

    public PageSize getPageSize() {
        return pageSize;
    }

    public List<PageEntryModel> getPageEntries() {
        return Collections.unmodifiableList(pageEntries);
    }

    public int getCellCount() {
        return geometry.getCells().size();
    }

    public void setImage(File file, int index) {

        if (index >= getCellCount()) {
            throw new IllegalArgumentException("Cannot add image at position " + index + " with page entry count = " + getCellCount());
        }

        PageEntryModel pem = pageEntries.get(index);
        pem.setImageFile(file);

    }

    public final void setGeometry(PageGeometry geometry) {

        List<PageEntryModel> oldEntries = new ArrayList<>();
        oldEntries.addAll(getPageEntries());

        this.geometry = geometry;
        this.pageEntries.clear();

        int i = 0;

        for (PageCell cell : geometry.getCells()) {
            PageEntryModel pem = new PageEntryModel(cell);
            Dimension physicalSize = getCellSizePoints(cell);
            pem.setPhysicalSize(physicalSize);

            if (i < oldEntries.size()) {
                PageEntryModel oldEntry = oldEntries.get(i);
                pem.setImageFile(oldEntry.getImageFile());
                pem.setZoomFactor(oldEntry.getZoomFactor());
            }

            pageEntries.add(pem);
            i++;
        }
    }

    public int getMargin() {
        return marginPoints;
    }

    public void setMargin(int margin) {
        this.marginPoints = margin;
    }

    public PageModel withMargin(int margin) {
        setMargin(margin);
        return this;
    }

    public int getUnitCellHeightPoints() {
        int verticalCellCount = geometry.verticalCellCount();
        int totalMarginPoints = marginPoints * (verticalCellCount + 1);
        int availableHeight = pageSize.height() - totalMarginPoints;
        int cellHeight = availableHeight / geometry.verticalCellCount();
        return cellHeight;
    }

    public int getUnitCellWidthPoints() {
        int horizontalCellCount = geometry.horizontalCellCount();
        int totalMarginPoints = marginPoints * (horizontalCellCount + 1);
        int availableWidth = getPageSize().width() - totalMarginPoints;
        int cellWidth = availableWidth / geometry.horizontalCellCount();
        return cellWidth;
    }

    public Point getCellPositionPoints(PageCell cell) {
        int posX = (getUnitCellWidthPoints() * cell.location().x) + (marginPoints * (cell.location().x + 1));
        int posY = (getUnitCellHeightPoints() * cell.location().y) + (marginPoints * (cell.location().y + 1));

        return new Point(posX, posY);
    }

    public Dimension getCellSizePoints(PageCell cell) {
        // An entry might span multiple cells. We need to take the margin between
        // cells into account when computing the size
        // For example, if the entry spans three cells horizontally, then the
        // cell width = 3*unitWidth + 2*margin

        int unitHeight = getUnitCellHeightPoints();
        int unitWidth = getUnitCellWidthPoints();
        int horizontalMarginCount = cell.size().width - 1;
        int verticalMarginCount = cell.size().height - 1;

        int cellHeightPoints = (cell.size().height * unitHeight) + (verticalMarginCount * marginPoints);
        int cellWidthPoints = (cell.size().width * unitWidth) + (horizontalMarginCount * marginPoints);

        return new Dimension(cellWidthPoints, cellHeightPoints);
    }
    
    LocalDateTime getLastChangeDate() {
        LocalDateTime lastChangeDate = LocalDateTime.MIN;
        
        for (PageEntryModel pem : pageEntries) {
            if (pem.getLastChangeDate().isAfter(lastChangeDate)) {
                lastChangeDate = pem.getLastChangeDate();
            }
        }
        
        return lastChangeDate;
    }
}
