/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import java.awt.Dimension;

/**
 *
 * @author philb
 */
public enum PageSize {

    A4_Portrait(595, 842, "A4 Portrait"),
    A4_Landscape(842, 595, "A4 Landscape"),
    US_Letter_Portrait(612, 792, "US Letter Portrait"),
    US_Letter_Landscape(792, 612, "US Letter Landscape");

    private final int width;
    private final int height;
    private final String description;

    PageSize(int width, int height, String description) {
        this.width = width;
        this.height = height;
        this.description = description;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public String description() {
        return description;
    }
    
    public Dimension size() {
        return new Dimension(width, height);
    }

    public int heigthFromWidth(int w) {
        double heightToWidthRatio = (double) height / (double) width;
        return (int) (heightToWidthRatio * w);
    }

    public int widthFromHeight(int h) {
        double heightToWidthRatio = (double) height / (double) width;
        return (int) ((double) h / heightToWidthRatio);
    }
    
    @Override
    public String toString() {
        return description;
    }
}
