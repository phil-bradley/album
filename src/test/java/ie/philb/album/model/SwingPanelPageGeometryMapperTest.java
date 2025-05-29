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

    @Test
    void givenSingleCell_expectCellAtPositionZeroZero() {

        PageGeometry pg = PageGeometry.square(1);

        PageModel pageModel = new PageModel(pg, PageSize.A4_Landscape);
        assertEquals(1, pageModel.getCellCount());

        pageModel.setMarginMillis(0);

        int panelWidth = 1200;
        int panelHeight = PageSize.A4_Landscape.heigthFromWidth(panelWidth);

        Dimension panelSize = new Dimension(panelWidth, panelHeight);
        SwingPanelPageGeometryMapper mapper = new SwingPanelPageGeometryMapper(pageModel, panelSize);

        PageCell cell = pg.getCells().get(0);
        Point location = mapper.getLocationOnPanel(cell);
        assertEquals(new Point(0, 0), location);
    }

    @Test
    void givenSingleCell_expectCellWidthAndHeightMatchesPanel() {

        PageGeometry pg = PageGeometry.square(1);

        PageModel pageModel = new PageModel(pg, PageSize.A4_Landscape);
        pageModel.setMarginMillis(0);

        int panelWidth = 1200;
        int panelHeight = PageSize.A4_Landscape.heigthFromWidth(panelWidth);

        Dimension panelSize = new Dimension(panelWidth, panelHeight);
        SwingPanelPageGeometryMapper mapper = new SwingPanelPageGeometryMapper(pageModel, panelSize);

        PageCell cell = pg.getCells().get(0);
        Dimension size = mapper.getSizeOnPanel(cell);

        double computedWidth = (double) (size.width);
        double computedHeight = (double) (size.height);

        assertThat(computedWidth, closeTo(panelSize.width, 10));
        assertThat(computedHeight, closeTo(panelSize.height, 10));
    }

    @Test
    void foo() {
        PageGeometry pg = PageGeometry.rectangle(2, 1);
        PageModel pageModel = new PageModel(pg, PageSize.A4_Landscape);
        pageModel.setMarginMillis(10);

        int panelHeight = 1000;
        int panelWidth = PageSize.A4_Landscape.widthFromHeight(panelHeight);

        Dimension panelSize = new Dimension(panelWidth, panelHeight);
        SwingPanelPageGeometryMapper mapper = new SwingPanelPageGeometryMapper(pageModel, panelSize);

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
        *  Conversion factor to px = 210 / 1000 = 0.21
        *  so the expected cell height = 190 / 0.21
        *  and expected cell width = 133.5 / 0.21
         */
        PageCell cell00 = pg.getCells().get(0);
        assertEquals(new Dimension(1, 1), cell00.size());
        assertEquals(new Point(0, 0), cell00.location());

        Dimension scaledSize = mapper.getSizeOnPanel(cell00);

        double expectedWidth = 135.5 / 0.21;
        double expectedHeight = 190 / 0.21;
        double computedWidth = scaledSize.width;
        double computedHeight = scaledSize.height;

        assertThat(computedHeight, closeTo(expectedHeight, 1));
        assertThat(computedWidth, closeTo(expectedWidth, 1));
    }
}
