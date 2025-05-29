/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;
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
        pageModel.setMarginMillis(0);

        int panelWidth = 1200;
        int panelHeight = PageSize.A4_Landscape.heigthFromWidth(panelWidth);

        Dimension panelSize = new Dimension(panelWidth, panelHeight);
        SwingPanelPageGeometryMapper mapper = new SwingPanelPageGeometryMapper(pageModel, panelSize);

        List<PageCell> cells = pg.getCells();
        assertEquals(1, cells.size());

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
}
