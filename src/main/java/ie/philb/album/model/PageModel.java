/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author philb
 */
public class PageModel {

    private int marginMillis = 10;
    private PageGeometry geometry;
    private final List<PageEntryModel> pageEntries = new ArrayList<>();
    private final PageSize pageSize;

    public PageModel(PageGeometry geometry, PageSize pageSize) {
        this.pageSize = pageSize;
        setGeometry(geometry);
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

        try {
            if (index >= getCellCount()) {
                throw new IllegalArgumentException("Cannot add image at position " + index + " with page entry count = " + getCellCount());
            }

            PageEntryModel pem = pageEntries.get(index);
            pem.setImageIcon(new ImageIcon(file.getCanonicalPath()));
        } catch (IOException ex) {
            Logger.getLogger(PageModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void setGeometry(PageGeometry geometry) {
        this.geometry = geometry;
        this.pageEntries.clear();

        for (PageCell cell : geometry.getCells()) {
            pageEntries.add(new PageEntryModel(cell));
        }
    }

    public int getMarginMillis() {
        return marginMillis;
    }

    public void setMarginMillis(int marginMillis) {
        this.marginMillis = marginMillis;
    }

    private int getUnitCellWidthMillis() {
        int horizontalCellCount = geometry.horizontalCellCount();
        int totalMarginMillis = marginMillis * (horizontalCellCount + 1);
        int availableWidth = pageSize.width() - totalMarginMillis;
        return availableWidth / geometry.horizontalCellCount();
    }

    private int getUnitCellHeightMillis() {
        int verticalCellCount = geometry.verticalCellCount();
        int totalMarginMillis = marginMillis * (verticalCellCount + 1);
        int availableHeight = pageSize.height() - totalMarginMillis;
        return availableHeight / geometry.verticalCellCount();
    }

    public Dimension getCellSizeMillis(PageCell cell) {

        int cellHeightMillis = (cell.size().height * getUnitCellHeightMillis()) + (cell.size().height - 1 * marginMillis);
        int cellWidthMillis = (cell.size().width * getUnitCellWidthMillis()) + (cell.size().width - 1 * marginMillis);

        return new Dimension(cellWidthMillis, cellHeightMillis);
    }

    public Point getCellPositionMillis(PageCell cell) {
        int posX = (getUnitCellWidthMillis() * cell.location().x) + (marginMillis * (cell.location().x + 1));
        int posY = (getUnitCellHeightMillis() * cell.location().y) + (marginMillis * (cell.location().y + 1));

        return new Point(posX, posY);
    }
}
