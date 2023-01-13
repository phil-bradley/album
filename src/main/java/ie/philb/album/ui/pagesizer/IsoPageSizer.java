/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.pagesizer;

/**
 *
 * @author Philip.Bradley
 */
public class IsoPageSizer implements PageSizer {

    private static final double ASPECT_RATIO = 1.41421356237; // Sqrt 2 for ISO A series (A4, A3 etc)

    @Override
    public int getWidthFromHeight(int height) {
        int width = (int) (height * ASPECT_RATIO);
        return width;
    }

    @Override
    public int getHeightFromWidth(int width) {
        int height = (int) (width / ASPECT_RATIO);
        return height;
    }

}
