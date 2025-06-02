/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import java.awt.Dimension;
import java.awt.Point;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author philb
 */
public class PageGeometryMapper {

    public enum OriginLocation {
        NorthEast,
        SouthEast,
        NorthWest,
        SouthWest
    }

    private static final MathContext MATH_CONTEXT = new MathContext(20, RoundingMode.HALF_EVEN);
    private final PageModel pageModel;
    private final Dimension viewSize;
    private OriginLocation originLocation = OriginLocation.NorthWest;  // Default for Swing panels

    public PageGeometryMapper(PageModel pageModel, Dimension viewSize) {
        this.pageModel = pageModel;
        this.viewSize = viewSize;
    }

    public OriginLocation getOriginLocation() {
        return originLocation;
    }

    public void setOriginLocation(OriginLocation originLocation) {
        this.originLocation = originLocation;
    }

    public Dimension getSizeOnView(PageCell cell) {
        Dimension cellSizeMillis = getCellSizeMillis(cell);

        int width = millisToViewUnits(cellSizeMillis.width);
        int height = millisToViewUnits(cellSizeMillis.height);

        return new Dimension(width, height);
    }

    public Point getLocationOnView(PageCell cell) {

        Point locationMillis = getCellPositionMillis(cell);
        Dimension sizeMillis = getCellSizeMillis(cell);

        int x;
        int y;

        if (originLocation == OriginLocation.NorthEast || originLocation == OriginLocation.SouthEast) {
            x = millisToViewUnits((pageModel.getPageSize().width() - locationMillis.x) - sizeMillis.width);
        } else {
            x = millisToViewUnits(locationMillis.x);
        }

        if (originLocation == OriginLocation.SouthEast || originLocation == OriginLocation.SouthWest) {
            y = millisToViewUnits((pageModel.getPageSize().height() - locationMillis.y) - sizeMillis.height);
        } else {
            y = millisToViewUnits(locationMillis.y);
        }

        return new Point(x, y);
    }

    public int getUnitCellHeightMillis() {
        int verticalCellCount = pageModel.getGeometry().verticalCellCount();
        int totalMarginMillis = pageModel.getMarginMillis() * (verticalCellCount + 1);
        int availableHeight = pageModel.getPageSize().height() - totalMarginMillis;
        int cellHeight = availableHeight / pageModel.getGeometry().verticalCellCount();
        return cellHeight;
    }

    public int getUnitCellWidthMillis() {
        int horizontalCellCount = pageModel.getGeometry().horizontalCellCount();
        int totalMarginMillis = pageModel.getMarginMillis() * (horizontalCellCount + 1);
        int availableWidth = pageModel.getPageSize().width() - totalMarginMillis;
        int cellWidth = availableWidth / pageModel.getGeometry().horizontalCellCount();
        return cellWidth;
    }

    public Dimension getCellSizeMillis(PageCell cell) {
        // An entry might span multiple cells. We need to take the margin between
        // cells into account when computing the size
        // For example, if the entry spans three cells horizontally, then the
        // cell width = 3*unitWidth + 2*margin

        int unitHeight = getUnitCellHeightMillis();
        int unitWidth = getUnitCellWidthMillis();
        int horizontalMarginCount = cell.size().width - 1;
        int verticalMarginCount = cell.size().height - 1;
        int margin = pageModel.getMarginMillis();

        int cellHeightMillis = (cell.size().height * unitHeight) + (verticalMarginCount * margin);
        int cellWidthMillis = (cell.size().width * unitWidth) + (horizontalMarginCount * margin);

        return new Dimension(cellWidthMillis, cellHeightMillis);
    }

    private Point getCellPositionMillis(PageCell cell) {
        int posX = (getUnitCellWidthMillis() * cell.location().x) + (pageModel.getMarginMillis() * (cell.location().x + 1));
        int posY = (getUnitCellHeightMillis() * cell.location().y) + (pageModel.getMarginMillis() * (cell.location().y + 1));

        return new Point(posX, posY);
    }

    public int millisToViewUnits(int v) {
        BigDecimal scale = getMillisToViewUnitsScale();

        if (BigDecimal.ONE.equals(scale)) {
            return v;
        }

        BigDecimal millis = new BigDecimal(v, MATH_CONTEXT);
        BigDecimal px = millis.divide(scale, MATH_CONTEXT);
        return px.setScale(0, RoundingMode.HALF_EVEN).intValue();
    }

    private BigDecimal getMillisToViewUnitsScale() {

        if (pageModel.getPageSize().height() == viewSize.height) {
            return BigDecimal.ONE;
        }

        BigDecimal pageHeightMillis = new BigDecimal(pageModel.getPageSize().height(), MATH_CONTEXT);
        BigDecimal panelHeight = new BigDecimal(viewSize.height, MATH_CONTEXT);
        BigDecimal millisToViewUnits = pageHeightMillis.divide(panelHeight, MATH_CONTEXT);
        return millisToViewUnits;
    }
}
