/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import java.awt.Color;

/**
 *
 * @author philb
 */
public record TextContent(
        String content,
        boolean isBold,
        boolean isItalic,
        boolean isUnderline,
        String fontName,
        int fontSize,
        Color fontColor) {

}
