/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.util;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JComponent;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author philb
 */
public class TestUtils {

    public static BufferedImage getTestImage() throws IOException {
        return ImageUtils.readBufferedImage(getTestImageFile());
    }

    public static File getTestImageFile() {
        String resourcePath = TestUtils.class.getResource("/test_275x183.jpg").getPath();
        return new File(resourcePath);
    }

    public static MouseEvent createMouseClickEvent(JComponent component) {
        return createMouseClickEvent(component, new Point(1, 1));
    }

    public static MouseEvent createMouseClickEvent(JComponent component, Point location) {

        MouseEvent mouseEvent = new MouseEvent(
                component,
                MouseEvent.MOUSE_CLICKED,
                System.currentTimeMillis(),
                0, // Modifiers
                location.x, location.y, // Coordinates
                1, // Click count
                true,
                MouseEvent.BUTTON1);

        return mouseEvent;
    }

    public static void assertClose(Rectangle expected, Rectangle value, int tolerance) {

        String sizeMsg = "Expected size " + value.width + "x" + value.height + " to be within " + tolerance + " of " + expected.width + "x" + expected.height;
        String locationMsg = "Expected location {" + value.x + "," + value.y + "} to be within " + tolerance + " of {" + expected.x + "," + expected.y + "}";

        assertClose(expected.x, value.x, tolerance, locationMsg);
        assertClose(expected.y, value.y, tolerance, locationMsg);
        assertClose(expected.width, value.width, tolerance, sizeMsg);
        assertClose(expected.height, value.height, tolerance, sizeMsg);
    }

    public static void assertClose(Dimension expected, Dimension value, int tolerance) {
        String msg = "Expected size " + value.width + "x" + value.height + " to be within " + tolerance + " of " + expected.width + "x" + expected.height;

        assertClose(expected.width, value.width, tolerance, msg);
        assertClose(expected.height, value.height, tolerance, msg);
    }

    public static void assertClose(int expected, int value, int tolerance) {
        String msg = "Expected " + value + " to be within " + tolerance + " of " + expected;
        assertClose(expected, value, tolerance, msg);
    }

    public static void assertClose(int expected, int value, int tolerance, String msg) {
        int delta = Math.abs(value - expected);
        assertTrue(delta <= Math.abs(tolerance), msg);
    }
}
