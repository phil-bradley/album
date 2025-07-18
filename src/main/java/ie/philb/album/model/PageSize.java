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

    A4_Portrait(595, 842),
    A4_Landscape(842, 595),
    US_Letter_Portrait(612, 792),
    US_Letter_Landscape(792, 612);

    private final int width;
    private final int height;

    PageSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
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
}
