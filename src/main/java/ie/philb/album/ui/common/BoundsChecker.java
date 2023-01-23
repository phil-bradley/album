/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common;

import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Philip.Bradley
 */
public class BoundsChecker {

    private final Rectangle rectangle;

    public BoundsChecker(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public boolean isBounded(Point point) {

        if (point.x < rectangle.x) {
            return false;
        }

        if (point.x > rectangle.x + rectangle.width) {
            return false;
        }

        if (point.y < rectangle.y) {
            return false;
        }

        if (point.y > rectangle.y + rectangle.height) {
            return false;
        }

        return true;
    }
}
