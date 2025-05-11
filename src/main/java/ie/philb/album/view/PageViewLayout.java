/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.ui.page.PageSpecification;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author philb
 */
public class PageViewLayout {

    private final PageSpecification pageSpecification;
    private final List<PageEntryCoordinates> coordinates;

    private final int x;
    private final int y;

    public PageViewLayout(PageSpecification pageSpecification, int x, int y) {
        this.coordinates = new ArrayList<>(x * y);
        this.pageSpecification = pageSpecification;
        this.x = x;
        this.y = y;

        initCoordinates();
    }

    public int entryCount() {
        return (x * y);
    }

    private void initCoordinates() {

        int offsetX = 0;
        int offsetY = 0;
        int width = 100;
        int height = 100;

        for (int y = 0; y < this.y; y++) {
            for (int x = 0; x < this.x; x++) {
                PageEntryCoordinates pec = new PageEntryCoordinates(offsetX, offsetY, width, height);
                this.coordinates.add(pec);
                offsetX += width + 10;
            }
            offsetY += height + 10;
        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<PageEntryCoordinates> getEntryCoordinates() {
        return coordinates;
    }

}
