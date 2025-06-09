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

    Square_1(PageGeometry.square(1)),
    Square_2(PageGeometry.square(2)),
    Square_3(PageGeometry.square(3)),
    Rectangle_1_2(PageGeometry.rectangle(1, 2)),
    Rectangle_1_3(PageGeometry.rectangle(1, 3)),
    Rectangle_2_1(PageGeometry.rectangle(2, 1)),
    Rectangle_2_3(PageGeometry.rectangle(2, 3)),
    Rectangle_3_1(PageGeometry.rectangle(3, 1)),
    Rectangle_3_2(PageGeometry.rectangle(3, 2));

    private final PageGeometry geometry;

    PageGeometryOption(PageGeometry geometry) {
        this.geometry = geometry;
    }

    public PageGeometry geometry() {
        return geometry;
    }
}
