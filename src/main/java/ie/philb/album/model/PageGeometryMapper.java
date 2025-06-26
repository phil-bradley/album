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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author philb
 */
public class PageGeometryMapper {

    private static final Logger LOG = LoggerFactory.getLogger(PageGeometryMapper.class);

    public enum OriginLocation {
        NorthEast,
        SouthEast,
        NorthWest,
        SouthWest;

        public boolean isNorth() {
            return this.equals(NorthEast) || this.equals(NorthWest);
        }

        public boolean isSouth() {
            return this.equals(SouthEast) || this.equals(SouthWest);
        }

        public boolean isEast() {
            return this.equals(SouthEast) || this.equals(NorthEast);
        }

        public boolean isWest() {
            return this.equals(SouthWest) || this.equals(NorthEast);
        }
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

    public Dimension getCellSizeOnView(PageEntryModel pageEntryModel) {
        PageCell cell = pageEntryModel.getCell();
        Dimension cellSizePoints = pageModel.getCellSizePoints(cell);

        int width = pointsToViewUnits(cellSizePoints.width);
        int height = pointsToViewUnits(cellSizePoints.height);

        return new Dimension(width, height);
    }

    public Point getCellLocationOnView(PageCell cell) {

        Point locationPoints = pageModel.getCellPositionPoints(cell);
        Dimension sizePoints = pageModel.getCellSizePoints(cell);

        int xPoints = locationPoints.x;
        int yPoints = locationPoints.y;

        if (originLocation.isEast()) {
            xPoints = (pageModel.getPageSize().width() - locationPoints.x) - sizePoints.width;
        }

        if (originLocation.isSouth()) {
            yPoints = (pageModel.getPageSize().height() - locationPoints.y) - sizePoints.height;
        }

        int xView = pointsToViewUnits(xPoints);
        int yView = pointsToViewUnits(yPoints);

        return new Point(xView, yView);
    }

    public int viewUnitsToPoints(int v) {
        BigDecimal scale = getPointsToViewUnitsScale();

        if (BigDecimal.ONE.equals(scale)) {
            return v;
        }

        BigDecimal viewUnits = new BigDecimal(v, MATH_CONTEXT);
        BigDecimal points = viewUnits.multiply(scale, MATH_CONTEXT);
        return points.setScale(0, RoundingMode.HALF_EVEN).intValue();
    }

    public Point locationAsPointsToViewUnits(Point location) {
        int x = pointsToViewUnits(location.x);
        int y = pointsToViewUnits(location.y);
        return new Point(x, y);
    }

    public int pointsToViewUnits(int v) {
        BigDecimal scale = getPointsToViewUnitsScale();

        if (BigDecimal.ONE.equals(scale)) {
            return v;
        }

        if (BigDecimal.ZERO.equals(scale)) {
            return 0;
        }

        BigDecimal points = new BigDecimal(v, MATH_CONTEXT);
        BigDecimal px = points.divide(scale, MATH_CONTEXT);

        return px.setScale(0, RoundingMode.HALF_EVEN).intValue();
    }

    public BigDecimal getPointsToViewUnitsScale() {

        if (pageModel.getPageSize().height() == viewSize.height) {
            return BigDecimal.ONE;
        }

        if (viewSize.height == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal pageHeightPoints = new BigDecimal(pageModel.getPageSize().height(), MATH_CONTEXT);
        BigDecimal viewHeight = new BigDecimal(viewSize.height, MATH_CONTEXT);
        BigDecimal pointsToViewUnits = pageHeightPoints.divide(viewHeight, MATH_CONTEXT);
        return pointsToViewUnits;
    }
}
