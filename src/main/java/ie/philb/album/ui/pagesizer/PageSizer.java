/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.pagesizer;

/**
 *
 * @author Philip.Bradley
 */
public interface PageSizer {
    
    int getWidthFromHeight(int height);

    int getHeightFromWidth(int width);
}
