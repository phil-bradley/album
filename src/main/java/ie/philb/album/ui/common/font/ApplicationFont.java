/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.font.TextAttribute;
import static java.awt.font.TextAttribute.UNDERLINE_ON;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author philb
 */
public enum ApplicationFont {

    GreatVibes(),
    Caveat(),
    Italianno(),
    Kapakana(),
    NotoSerif(),
    OpenSans(),
    Parisienne(),
    PinyonScript(),
    Tangerine();

    private static final float DEFAULT_SIZE = 16;

    public Font getFont() {
        try {
            String path = "/ie/philb/album/fonts/" + name() + ".ttf";
            return Font.createFont(Font.TRUETYPE_FONT, ApplicationFont.class.getResourceAsStream(path)).deriveFont(DEFAULT_SIZE);
        } catch (FontFormatException | IOException ex) {
            throw new RuntimeException("Font creation failed", ex);
        }
    }

    public Font getDerivedFont(boolean bold, boolean italic, boolean underline, int size) {

        int style = Font.PLAIN;

        if (bold) {
            style = style | Font.BOLD;
        }

        if (italic) {
            style = style | Font.ITALIC;
        }

        Font derived = getFont().deriveFont(style).deriveFont((float) size);

        if (underline) {
            Map<TextAttribute, Integer> attrs = new HashMap<>();
            attrs.put(TextAttribute.UNDERLINE, UNDERLINE_ON);
            return derived.deriveFont(attrs);
        } else {
            return derived;
        }
    }

    static final Map<String, ApplicationFont> map = new HashMap<>();

    static {
        for (ApplicationFont fonts : values()) {
            map.put(fonts.name(), fonts);
        }
    }

    public static ApplicationFont byFamilyName(String familyName) {
        return map.get(familyName);
    }
}
