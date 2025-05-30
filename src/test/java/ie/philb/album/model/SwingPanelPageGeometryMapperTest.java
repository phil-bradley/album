/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import java.awt.Dimension;
import java.awt.Point;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class SwingPanelPageGeometryMapperTest {

    private static final Dimension PANEL_SIZE = new Dimension(1000, PageSize.A4_Landscape.heigthFromWidth(1000));

    @Test
    void givenSingleCell_expectCellAtPositionZeroZero() {

        PageGeometry pg = PageGeometry.square(1);

        PageModel pageModel = new PageModel(pg, PageSize.A4_Landscape).withMarginMillis(0);
        assertEquals(1, pageModel.getCellCount());

        SwingPanelPageGeometryMapper mapper = new SwingPanelPageGeometryMapper(pageModel, PANEL_SIZE);

        PageCell cell = pg.getCells().get(0);
        Point location = mapper.getLocationOnPanel(cell);
        assertEquals(new Point(0, 0), location);
    }

    @Test
    void givenSingleCell_expectCellSizeMatchesPageSize() {

        PageGeometry pg = PageGeometry.square(1);

        PageModel pageModel = new PageModel(pg, PageSize.A4_Landscape).withMarginMillis(0);
        assertEquals(1, pageModel.getCellCount());

        SwingPanelPageGeometryMapper mapper = new SwingPanelPageGeometryMapper(pageModel, PANEL_SIZE);

        PageCell cell = pg.getCells().get(0);
        Dimension cellSizeMillis = mapper.getCellSizeMillis(cell);
        assertEquals(PageSize.A4_Landscape.size(), cellSizeMillis);
    }

    @Test
    void givenSingleCell_andMargin_expectCellSizeMatchesPageSize() {

        PageGeometry pg = PageGeometry.square(1);
        int margin = 10;

        PageModel pageModel = new PageModel(pg, PageSize.A4_Landscape).withMarginMillis(margin);
        assertEquals(1, pageModel.getCellCount());

        SwingPanelPageGeometryMapper mapper = new SwingPanelPageGeometryMapper(pageModel, PANEL_SIZE);

        PageCell cell = pg.getCells().get(0);
        Dimension cellSizeMillis = mapper.getCellSizeMillis(cell);

        double expectedWidth = pageModel.getPageSize().width() - (2 * margin);
        double expectedHeight = pageModel.getPageSize().height() - (2 * margin);

        assertEquals(expectedWidth, cellSizeMillis.width, "Validate width");
        assertEquals(expectedHeight, cellSizeMillis.height, "Validate height");
    }

    @Test
    void givenSingleCell_andNoMargin_expectScaledCellSizeMatchesPanelSize() {

        PageGeometry pg = PageGeometry.square(1);

        PageModel pageModel = new PageModel(pg, PageSize.A4_Landscape).withMarginMillis(0);

        SwingPanelPageGeometryMapper mapper = new SwingPanelPageGeometryMapper(pageModel, PANEL_SIZE);

        PageCell cell = pg.getCells().get(0);
        Dimension size = mapper.getSizeOnPanel(cell);

        double computedWidth = (double) (size.width);
        double computedHeight = (double) (size.height);

        assertThat(computedWidth, closeTo(PANEL_SIZE.width, 10));
        assertThat(computedHeight, closeTo(PANEL_SIZE.height, 10));
    }

    @Test
    void givenSingleCell_andMargin_expectScaledCellSizeMatchesPanelSize() {

        PageGeometry pg = PageGeometry.square(1);
        int margin = 10;

        PageModel pageModel = new PageModel(pg, PageSize.A4_Landscape).withMarginMillis(margin);

        SwingPanelPageGeometryMapper mapper = new SwingPanelPageGeometryMapper(pageModel, PANEL_SIZE);

        PageCell cell = pg.getCells().get(0);
        Dimension size = mapper.getSizeOnPanel(cell);

        double expectedWidth = PANEL_SIZE.width - mapper.millisToPx(margin) * 2;
        double expectedHeight = PANEL_SIZE.height - mapper.millisToPx(margin) * 2;

        double computedWidth = (double) (size.width);
        double computedHeight = (double) (size.height);

        assertThat(computedWidth, closeTo(expectedWidth, 5));
        assertThat(computedHeight, closeTo(expectedHeight, 5));
    }

    @Test
    void given_1x2_Cell_andNoMargin_expectCellSizeMatchesPageSize() {

        PageGeometry pg = PageGeometry.rectangle(1, 2);

        PageModel pageModel = new PageModel(pg, PageSize.A4_Landscape).withMarginMillis(0);
        assertEquals(2, pageModel.getCellCount());

        SwingPanelPageGeometryMapper mapper = new SwingPanelPageGeometryMapper(pageModel, PANEL_SIZE);

        PageCell cell = pg.getCells().get(0);
        Dimension cellSizeMillis = mapper.getCellSizeMillis(cell);

        double expectedWidth = pageModel.getPageSize().width();
        double expectedHeight = pageModel.getPageSize().height() / 2;

        assertEquals(expectedWidth, cellSizeMillis.width, "Validate width");
        assertEquals(expectedHeight, cellSizeMillis.height, "Validate height");
    }

    @Test
    void given_2x1_Cell_andNoMargin_expectCellSizeMatchesPageSize() {

        PageGeometry pg = PageGeometry.rectangle(2, 1);

        PageModel pageModel = new PageModel(pg, PageSize.A4_Landscape).withMarginMillis(0);
        assertEquals(2, pageModel.getCellCount());

        SwingPanelPageGeometryMapper mapper = new SwingPanelPageGeometryMapper(pageModel, PANEL_SIZE);

        PageCell cell = pg.getCells().get(0);
        Dimension cellSizeMillis = mapper.getCellSizeMillis(cell);

        double expectedWidth = pageModel.getPageSize().width() / 2;
        double expectedHeight = pageModel.getPageSize().height();

        assertEquals(expectedWidth, cellSizeMillis.width, "Validate width");
        assertEquals(expectedHeight, cellSizeMillis.height, "Validate height");
    }

    @Test
    void given_1x2_Cells_andNoMargin_expectCellSizeScaledToPanel() {
        PageGeometry pg = PageGeometry.rectangle(1, 2);
        PageModel pageModel = new PageModel(pg, PageSize.A4_Landscape).withMarginMillis(0);

        int panelHeight = 1000;
        int panelWidth = PageSize.A4_Landscape.widthFromHeight(panelHeight);

        Dimension panelSize = new Dimension(panelWidth, panelHeight);
        SwingPanelPageGeometryMapper mapper = new SwingPanelPageGeometryMapper(pageModel, panelSize);

        PageCell cell00 = pg.getCells().get(0);
        assertEquals(new Dimension(1, 1), cell00.size());
        assertEquals(new Point(0, 0), cell00.location());

        Dimension scaledSize = mapper.getSizeOnPanel(cell00);

        double expectedWidth = panelWidth;
        double expectedHeight = panelHeight / 2;
        double computedWidth = scaledSize.width;
        double computedHeight = scaledSize.height;

        assertThat(computedHeight, closeTo(expectedHeight, 1));
        assertThat(computedWidth, closeTo(expectedWidth, 1));
    }

    @Test
    void given_2x1_Cells_andNoMargin_expectCellSizeScaledToPanel() {
        PageGeometry pg = PageGeometry.rectangle(2, 1);
        PageModel pageModel = new PageModel(pg, PageSize.A4_Landscape).withMarginMillis(0);

        int panelHeight = 1000;
        int panelWidth = PageSize.A4_Landscape.widthFromHeight(panelHeight);

        Dimension panelSize = new Dimension(panelWidth, panelHeight);
        SwingPanelPageGeometryMapper mapper = new SwingPanelPageGeometryMapper(pageModel, panelSize);

        PageCell cell00 = pg.getCells().get(0);
        assertEquals(new Dimension(1, 1), cell00.size());
        assertEquals(new Point(0, 0), cell00.location());

        Dimension scaledSize = mapper.getSizeOnPanel(cell00);

        double expectedWidth = panelWidth / 2;
        double expectedHeight = panelHeight;
        double computedWidth = scaledSize.width;
        double computedHeight = scaledSize.height;

        assertThat(computedHeight, closeTo(expectedHeight, 3));
        assertThat(computedWidth, closeTo(expectedWidth, 3));
    }

    @Test
    void given_2x1_Cells_andMargin_expectCellSizeScaledToPanel() {
        PageGeometry pg = PageGeometry.rectangle(2, 1);
        PageModel pageModel = new PageModel(pg, PageSize.A4_Landscape).withMarginMillis(10);

        SwingPanelPageGeometryMapper mapper = new SwingPanelPageGeometryMapper(pageModel, PANEL_SIZE);
        double scalingFactor = ((double) (pageModel.getPageSize().height())) / ((double) PANEL_SIZE.height);

        /*
        * The page has is 297x210mm with a margin of 10mm.
        * So each cell has

        * Height = 210mm - 20mm = 190mm
        *
        *            297mm - 30mm
        * Width =    ------------  = 133.5mm
        *                  2
        *
        *     --------------------------------
        *     |             10mm             |
        *     |   ----------   ----------    |
        *     | 10|        |10 |        | 10 |
        *     | mm|        |mm |        | mm | 210px
        *     |   ----------   ----------    |
        *     |            10mm              |
        *     |------------------------------|*
        *                <- 297px ->
        *
        *  Conversion factor to px = 210 / 707 = 0.29702
        *  so the expected cell height = 190 / 0.29702 = 640
        *  and expected cell width = 133 / 0.29702 = 448
         */
        PageCell cell00 = pg.getCells().get(0);
        assertEquals(new Dimension(1, 1), cell00.size());
        assertEquals(new Point(0, 0), cell00.location());

        Dimension unscaledSize = mapper.getCellSizeMillis(cell00);
        Dimension scaledSize = mapper.getSizeOnPanel(cell00);
        System.out.println("Got size " + unscaledSize + ", scaled size " + scaledSize);

        double expectedHeight = 190 / scalingFactor;
        double expectedWidth = 133 / scalingFactor;
        double computedHeight = scaledSize.height;
        double computedWidth = scaledSize.width;

        assertThat("Validate height", computedHeight, closeTo(expectedHeight, 1));
        assertThat("Validate width", computedWidth, closeTo(expectedWidth, 1));
    }
}
