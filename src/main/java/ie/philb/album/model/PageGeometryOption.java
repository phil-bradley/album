/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import ie.philb.album.ui.resources.Icons;
import static ie.philb.album.ui.resources.Icons.Pages.*;
import javax.swing.ImageIcon;

/**
 *
 * @author philb
 */
public enum PageGeometryOption {

    Title("Title Page", PageGeometry.square(PageEntryType.Text, 1), TITLE),
    Square_1("Single Image", PageGeometry.square(1), RECT_1x1),
    Square_2("Rectangle 2x2", PageGeometry.square(2), RECT_2x2),
    Square_3("Rectangle 3x3", PageGeometry.square(3), RECT_3x3),
    Rectangle_1_2("Rectangle 1x2", PageGeometry.rectangle(1, 2), RECT_1x2),
    Rectangle_1_3("Rectangle 1x3", PageGeometry.rectangle(1, 3), RECT_1x3),
    Rectangle_2_1("Rectangle 2x1", PageGeometry.rectangle(2, 1), RECT_2x1),
    Rectangle_2_3("Rectangle 2x3", PageGeometry.rectangle(2, 3), RECT_2x3),
    Rectangle_3_1("Rectangle 3x1", PageGeometry.rectangle(3, 1), RECT_3x1),
    Rectangle_3_2("Rectangle 3x2", PageGeometry.rectangle(3, 2), RECT_3x2),
    Columns_1_2("Columns 1+2", PageGeometry.withColumns(1, 2), COLS_1_2),
    Columns_2_1("Columns 2+1", PageGeometry.withColumns(2, 1), COLS_2_1),
    Rows_1_2("Rows 1+2", PageGeometry.withRows(1, 2), ROWS_1_2),
    Rows_1_3("Rows 1+3", PageGeometry.withRows(1, 3), ROWS_1_3),
    Rows_2_3("Rows 2+3", PageGeometry.withRows(2, 3), ROWS_2_3),
    Rows_2_1("Rows 2+1", PageGeometry.withRows(2, 1), ROWS_2_1),
    Rows_3_1("Rows 3+1", PageGeometry.withRows(3, 1), ROWS_3_1),
    Rows_3_2("Rows 3+2", PageGeometry.withRows(3, 2), ROWS_3_2);

    private final String description;
    private final PageGeometry geometry;
    private final ImageIcon icon;

    PageGeometryOption(String description, PageGeometry geometry, ImageIcon icon) {
        this.description = description;
        this.geometry = geometry;
        this.icon = icon;
    }

    public PageGeometry geometry() {
        return geometry;
    }

    public String description() {
        return description;
    }

    public ImageIcon icon() {
        return icon;
    }
}
