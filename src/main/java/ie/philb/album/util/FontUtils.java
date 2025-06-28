/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.util;

import java.awt.Font;
import java.awt.font.TextAttribute;
import static java.awt.font.TextAttribute.UNDERLINE_ON;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author philb
 */
public class FontUtils {

    public static Font bold(Font font) {
        return font.deriveFont(Font.BOLD);
    }

    public static Font italic(Font font) {
        return font.deriveFont(Font.ITALIC);
    }

    public static Font underline(Font font) {
        Map<TextAttribute, Integer> attrs = new HashMap<>();
        attrs.put(TextAttribute.UNDERLINE, UNDERLINE_ON);
        return font.deriveFont(attrs);
    }
}
