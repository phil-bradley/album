/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.page;

import java.awt.Dimension;
import java.awt.Point;

/**
 *
 * @author Philip.Bradley
 */
public class PageEntryBuilder {

    private int width;
    private int height;
    private int offsetX = 0;
    private int offsetY = 0;

    public PageEntryBuilder size(PageSpecification pageSpecification) {
        return size(pageSpecification.size());
    }
    
    public PageEntryBuilder size(Dimension size) {
        width((int) size.getWidth());
        height((int) size.getHeight());
        return this;
    }

    public PageEntryBuilder width(int width) {

        if (width <= 0) {
            throw new IllegalArgumentException("Unexpected width " + width);
        }

        this.width = width;
        return this;
    }

    public PageEntryBuilder height(int height) {

        if (height <= 0) {
            throw new IllegalArgumentException("Unexpected height " + height);
        }

        this.height = height;
        return this;
    }

    public PageEntryBuilder offset(Point point) {

        offsetX(point.x);
        offsetY(point.y);
        return this;
    }

    public PageEntryBuilder offsetX(int x) {

        if (x < 0) {
            throw new IllegalArgumentException("Unexpected X offset " + x);
        }

        this.offsetX = x;
        return this;
    }

    public PageEntryBuilder offsetY(int y) {

        if (y < 0) {
            throw new IllegalArgumentException("Unexpected Y offset " + y);
        }

        this.offsetY = y;
        return this;
    }

    public PageEntry build() {
        return new PageEntry(width, height, offsetX, offsetY);
    }
}
