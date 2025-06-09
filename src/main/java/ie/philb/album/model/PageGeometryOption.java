/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

/**
 *
 * @author philb
 */
public enum PageGeometryOption {

    Square_1("Single", PageGeometry.square(1)),
    Square_2("2x2", PageGeometry.square(2)),
    Square_3("3x3", PageGeometry.square(3)),
    Rectangle_1_2("1x2", PageGeometry.rectangle(1, 2)),
    Rectangle_1_3("1x3", PageGeometry.rectangle(1, 3)),
    Rectangle_2_1("2x1", PageGeometry.rectangle(2, 1)),
    Rectangle_2_3("2x3", PageGeometry.rectangle(2, 3)),
    Rectangle_3_1("3x1", PageGeometry.rectangle(3, 1)),
    Rectangle_3_2("3x2", PageGeometry.rectangle(3, 2));

    private final String description;
    private final PageGeometry geometry;

    PageGeometryOption(String description, PageGeometry geometry) {
        this.description = description;
        this.geometry = geometry;
    }

    public PageGeometry geometry() {
        return geometry;
    }

    public String description() {
        return description;
    }
}
