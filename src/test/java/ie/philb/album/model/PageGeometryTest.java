/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
class PageGeometryTest {

    @Test
    void givenSquareGeometry_expectLayoutAsSquare() {

        PageGeometry pg = PageGeometry.square(3);
        assertEquals(9, pg.getCells().size());

        assertEquals(3, pg.horizontalCellCount());
        assertEquals(3, pg.verticalCellCount());
    }

    @Test
    void givenRecangularGeometry_expectLayoutAsRectangle() {

        PageGeometry pg = PageGeometry.rectangle(2, 4);
        assertEquals(8, pg.getCells().size());

        assertEquals(2, pg.horizontalCellCount());
        assertEquals(4, pg.verticalCellCount());
    }

    @Test
    void givenZeroWidth_expectedException() {

        Exception thrown = assertThrows(
                RuntimeException.class,
                () -> {
                    PageGeometry.rectangle(0, 4);
                }
        );

        assertTrue(thrown.getMessage().contains("Cannot create page of width"));
    }

    @Test
    void givenZeroHeight_expectedException() {

        Exception thrown = assertThrows(
                RuntimeException.class,
                () -> {
                    PageGeometry.rectangle(4, 0);
                }
        );

        assertTrue(thrown.getMessage().contains("Cannot create page of height"));
    }

    @Test
    void givenMaxWidthExceeded_expectedException() {

        Exception thrown = assertThrows(
                RuntimeException.class,
                () -> {
                    PageGeometry.rectangle(PageGeometry.MAX_CELL_WIDTH + 1, 4);
                }
        );

        assertTrue(thrown.getMessage().contains("Cannot create page of width"));
    }

    @Test
    void givenMaxHeightExceeded_expectedException() {

        Exception thrown = assertThrows(
                RuntimeException.class,
                () -> {
                    PageGeometry.rectangle(4, PageGeometry.MAX_CELL_HEIGHT + 1);
                }
        );

        assertTrue(thrown.getMessage().contains("Cannot create page of height"));
    }

    @Test
    void givenRaggedWidth_expectMaxWidthEqualsLcm() {

        PageGeometry pg = PageGeometry.withRows(5, 7, 3);

        // 105 = LCM(5, 7, 3)
        assertEquals(105, pg.horizontalCellCount());
        assertEquals(3, pg.verticalCellCount());
    }

    @Test
    void givenRaggedHeight_expectMaxHeightEqualsLcm() {

        PageGeometry pg = PageGeometry.withColumns(2, 9, 4);

        // 36 = LCM(2, 9, 4)
        assertEquals(36, pg.verticalCellCount());
        assertEquals(3, pg.horizontalCellCount());
    }

    @Test
    void givenSquareGeometry_expectSquareSizeAndLocation() {

        PageGeometry pg = PageGeometry.square(2);

        List<PageCell> cells = pg.getCells();
        assertEquals(4, cells.size());

        PageCell[] expected = {
            new PageCell(new Dimension(1, 1), new Point(0, 0)),
            new PageCell(new Dimension(1, 1), new Point(0, 1)),
            new PageCell(new Dimension(1, 1), new Point(1, 0)),
            new PageCell(new Dimension(1, 1), new Point(1, 1))
        };

        assertThat(cells, containsInAnyOrder(expected));
    }

    @Test
    void givenRectangularGeometry_expectRectangularSizeAndLocation() {

        PageGeometry pg = PageGeometry.rectangle(2, 3);

        List<PageCell> cells = pg.getCells();
        assertEquals(6, cells.size());

        PageCell[] expected = {
            new PageCell(new Dimension(1, 1), new Point(0, 0)),
            new PageCell(new Dimension(1, 1), new Point(0, 1)),
            new PageCell(new Dimension(1, 1), new Point(0, 2)),
            new PageCell(new Dimension(1, 1), new Point(1, 0)),
            new PageCell(new Dimension(1, 1), new Point(1, 1)),
            new PageCell(new Dimension(1, 1), new Point(1, 2))
        };

        assertThat(cells, containsInAnyOrder(expected));
    }

    @Test
    void givenRaggedWidth_expectRaggedSizeAndLocation() {

        PageGeometry pg = PageGeometry.withRows(2, 3);

        List<PageCell> cells = pg.getCells();
        assertEquals(5, cells.size());

        PageCell[] expected = {
            new PageCell(new Dimension(3, 1), new Point(0, 0)),
            new PageCell(new Dimension(3, 1), new Point(3, 0)),
            new PageCell(new Dimension(2, 1), new Point(0, 1)),
            new PageCell(new Dimension(2, 1), new Point(2, 1)),
            new PageCell(new Dimension(2, 1), new Point(4, 1))
        };

        assertThat(cells, containsInAnyOrder(expected));
    }

    @Test
    void givenRaggedHeight_expectRaggedSizeAndLocation() {

        PageGeometry pg = PageGeometry.withColumns(2, 3);

        List<PageCell> cells = pg.getCells();
        assertEquals(5, cells.size());

        PageCell[] expected = {
            new PageCell(new Dimension(1, 3), new Point(0, 0)),
            new PageCell(new Dimension(1, 3), new Point(0, 3)),
            new PageCell(new Dimension(1, 2), new Point(1, 0)),
            new PageCell(new Dimension(1, 2), new Point(1, 2)),
            new PageCell(new Dimension(1, 2), new Point(1, 4))
        };

        assertThat(cells, containsInAnyOrder(expected));
    }
}
