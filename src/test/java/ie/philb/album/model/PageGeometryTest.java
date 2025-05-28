/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class PageGeometryTest {

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
}
