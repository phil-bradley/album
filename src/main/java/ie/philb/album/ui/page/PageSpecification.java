/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ie.philb.album.ui.page;

import java.awt.Dimension;

/**
 *
 * @author Philip.Bradley
 */
public enum PageSpecification {

    A4Portrait(210, 297),
    A4Landscape(297, 210),
    USLetterPortrait(216, 279),
    USLetterLandscape(279, 216);

    private final int width;
    private final int height;

    PageSpecification(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int height() {
        return height;
    }

    public int width() {
        return width;
    }

    public Dimension size() {
        return new Dimension(width, height);
    }

}
