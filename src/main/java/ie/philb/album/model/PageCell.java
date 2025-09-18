/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import java.awt.Dimension;
import java.awt.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 *
 * @author philb
 *
 * Provides an abstract representation of a space in the page. A page consists
 * of a number of cells, an example with 5 cells is shown below.
 *
 * ------------- | A | B | | |-------| C | | D | E | | -------------
 *
 * Cell A has position 0,1 and size 1,1 Cell B has position 1,1 and size 1,1
 * Cell C has position 2,0 and size 1,2 Cell D has position 0,0 and size 1,1
 * Cell E has position 0,1 and size 1,1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true, fluent = true)
public class PageCell {

    private Dimension size;
    private Point location;
    private PageEntryType pageEntryType;

    public PageCell(Dimension size, Point location) {
        this(size, location, PageEntryType.Image);
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public PageEntryType getPageEntryType() {
        return pageEntryType;
    }

    public void setPageEntryType(PageEntryType pageEntryType) {
        this.pageEntryType = pageEntryType;
    }

    @Override
    public String toString() {
        return "PageCell size=" + size.width + "x" + size.height + " at {" + location.x + ", " + location.y + "}";
    }
}
