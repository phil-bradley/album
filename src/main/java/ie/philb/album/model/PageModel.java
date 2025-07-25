/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import ie.philb.album.util.MathUtils;
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

    private final List<PageModelListener> listeners = new ArrayList<>();

    private int pageId;
    private int verticalMargin = 10;
    private int horizontalMargin = 10;
    private int gutter = 0;
    private PageGeometry geometry;
    private final List<PageEntryModel> pageEntries = new ArrayList<>();
    private final PageSize pageSize;

    public PageModel(PageGeometry geometry, PageSize pageSize) {
        this.pageSize = pageSize;
        setGeometry(geometry);
    }

    private PageModel(PageSize pageSize) {
        this.pageSize = pageSize;
    }

    public void addListener(PageModelListener l) {
        this.listeners.add(l);
    }

    public void removeListener(PageModelListener l) {
        this.listeners.remove(l);
    }

    private void fireModelUpdated() {
        for (PageModelListener l : listeners) {
            l.pageUpdated(this);
        }
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        if (this.pageId != pageId) {
            this.pageId = pageId;
            fireModelUpdated();
        }
    }

    public int getGutter() {
        return gutter;
    }

    public void setGutter(int gutter) {
        if (this.gutter != gutter) {
            this.gutter = gutter;
            fireModelUpdated();
        }
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

        fireModelUpdated();
    }

    public int getVerticalMargin() {
        return verticalMargin;
    }

    public int getHorizontalMargin() {
        return horizontalMargin;
    }

    public void setVerticalMargin(int margin) {
        if (this.verticalMargin != margin) {
            this.verticalMargin = margin;
            fireModelUpdated();
        }
    }

    public void setHorizontalMargin(int margin) {
        if (this.horizontalMargin != margin) {
            this.horizontalMargin = margin;
            fireModelUpdated();
        }
    }

    public void setMargin(int margin) {
        setVerticalMargin(margin);
        setHorizontalMargin(margin);
    }

    public PageModel withMargin(int margin) {
        setMargin(margin);
        return this;
    }

    public PageModel withGutter(int gutter) {
        setGutter(gutter);
        return this;
    }

    public int getUnitCellHeightPoints() {
        int verticalCellCount = geometry.verticalCellCount();
        int totalMarginPoints = verticalMargin * (verticalCellCount + 1);
        int availableHeight = pageSize.height() - totalMarginPoints;
        int cellHeight = availableHeight / geometry.verticalCellCount();
        return cellHeight;
    }

    public int getUnitCellWidthPoints() {
        int horizontalCellCount = geometry.horizontalCellCount();
        int totalMarginPoints = horizontalMargin * (horizontalCellCount + 1);
        int availableWidth = getPageSize().width() - (totalMarginPoints + gutter);
        int cellWidth = availableWidth / geometry.horizontalCellCount();
        return cellWidth;
    }

    public Point getCellPositionPoints(PageCell cell) {
        int gutterOffset = MathUtils.isEven(pageId) ? gutter : 0; // Title page has pageId =1, followed by virtual blank
        int posX = (getUnitCellWidthPoints() * cell.location().x) + (horizontalMargin * (cell.location().x + 1)) + gutterOffset;
        int posY = (getUnitCellHeightPoints() * cell.location().y) + (verticalMargin * (cell.location().y + 1));

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

        int cellHeightPoints = (cell.size().height * unitHeight) + (verticalMarginCount * verticalMargin);
        int cellWidthPoints = (cell.size().width * unitWidth) + (horizontalMarginCount * horizontalMargin);

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

    public static PageModel blank(PageSize pageSize) {
        return new PageModel(pageSize);
    }
}
