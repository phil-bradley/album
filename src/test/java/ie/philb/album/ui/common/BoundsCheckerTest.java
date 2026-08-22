/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ie.philb.album.ui.common;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class BoundsCheckerTest {

    @Test
    void givenPointInsideBound_whenCallIsBounded_expectTrue() {

        Point origin = new Point(50, 20);
        Dimension size = new Dimension(100, 200);

        Rectangle rectangle = new Rectangle(origin, size);
        BoundsChecker boundsChecker = new BoundsChecker(rectangle);

        assertTrue(boundsChecker.isBounded(origin));
        assertTrue(boundsChecker.isBounded(new Point(origin.x + 1, origin.y + 1)));
        assertTrue(boundsChecker.isBounded(new Point(origin.x + size.width, origin.y + size.height)));
    }

    @Test
    void givenPointNotInsideBound_whenCallIsBounded_expectFalse() {

        Point origin = new Point(50, 20);
        Dimension size = new Dimension(100, 200);

        Rectangle rectangle = new Rectangle(origin, size);
        BoundsChecker boundsChecker = new BoundsChecker(rectangle);

        assertFalse(boundsChecker.isBounded(new Point(origin.x - 1, origin.y - 1)));
        assertFalse(boundsChecker.isBounded(new Point(origin.x + size.width + 1, origin.y + size.height + 1)));
    }
}
