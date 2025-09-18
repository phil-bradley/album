/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.io;

import ie.philb.album.model.PageEntryType;

/**
 *
 * @author philb
 */
public record CellData(
        int width,
        int height,
        int positionX,
        int positionY,
        PageEntryType pageEntryType,
        String fileName,
        double zoom,
        boolean isGreyScale,
        boolean isCentered,
        int offsetX,
        int offsetY,
        int brightness,
        String text,
        String fontFamily,
        int fontSize,
        String fontColor,
        boolean italic,
        boolean bold,
        boolean underline
        ) {

}
