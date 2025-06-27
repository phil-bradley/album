/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import java.awt.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author philb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextContent {
    private String content;
    private boolean isBold;
    private boolean isItalic;
    private boolean isUnderline;
    private String fontFamily;
    private int fontSize;
    private Color fontColor;
}