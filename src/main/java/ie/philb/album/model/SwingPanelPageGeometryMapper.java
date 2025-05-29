/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import java.awt.Dimension;
import java.awt.Point;

/**
 *
 * @author philb
 */
public class SwingPanelPageGeometryMapper {

    private final PageModel pageModel;
    private final Dimension panelSize;

    public SwingPanelPageGeometryMapper(PageModel pageModel, Dimension panelSize) {
        this.pageModel = pageModel;
        this.panelSize = panelSize;
    }

    public Dimension getSizeOnPanel(PageCell cell) {
        double millisToPx = getMillisToPixelScale();
        Dimension cellSizeMillis = getCellSizeMillis(cell);

        int width = (int) (cellSizeMillis.width / millisToPx);
        int height = (int) (cellSizeMillis.height / millisToPx);

        return new Dimension(width, height);
    }

    public Point getLocationOnPanel(PageCell cell) {
        double millisToPx = getMillisToPixelScale();
        Point locationMillis = getCellPositionMillis(cell);

        int x = (int) (locationMillis.x / millisToPx);
        int y = (int) (locationMillis.y / millisToPx);

        return new Point(x, y);
    }

    private int getUnitCellHeightMillis() {
        int verticalCellCount = pageModel.getGeometry().verticalCellCount();
        int totalMarginMillis = pageModel.getMarginMillis() * (verticalCellCount + 1);
        int availableHeight = pageModel.getPageSize().height() - totalMarginMillis;
        return availableHeight / pageModel.getGeometry().verticalCellCount();
    }

    private int getUnitCellWidthMillis() {
        int horizontalCellCount = pageModel.getGeometry().horizontalCellCount();
        int totalMarginMillis = pageModel.getMarginMillis() * (horizontalCellCount + 1);
        int availableWidth = pageModel.getPageSize().width() - totalMarginMillis;
        return availableWidth / pageModel.getGeometry().horizontalCellCount();
    }

    private Dimension getCellSizeMillis(PageCell cell) {
        int cellHeightMillis = (cell.size().height * getUnitCellHeightMillis()) + (cell.size().height - 1 * pageModel.getMarginMillis());
        int cellWidthMillis = (cell.size().width * getUnitCellWidthMillis()) + (cell.size().width - 1 * pageModel.getMarginMillis());

        return new Dimension(cellWidthMillis, cellHeightMillis);
    }

    private Point getCellPositionMillis(PageCell cell) {
        int posX = (getUnitCellWidthMillis() * cell.location().x) + (pageModel.getMarginMillis() * (cell.location().x + 1));
        int posY = (getUnitCellHeightMillis() * cell.location().y) + (pageModel.getMarginMillis() * (cell.location().y + 1));

        return new Point(posX, posY);
    }

    private double getMillisToPixelScale() {
        double millisToPx = (double) (pageModel.getPageSize().height()) / (double) panelSize.getHeight();
        return millisToPx;
    }
}
