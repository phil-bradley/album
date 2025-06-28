/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.font.TextAttribute;
import static java.awt.font.TextAttribute.UNDERLINE_ON;
import java.io.File;
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
    private static final String FONT_PATH = "/ie/philb/album/fonts";
    private static final Map<String, ApplicationFont> map = new HashMap<>();
    private static final Map<String, Font> fontCache = new HashMap<>();

    public Font getFont() {
        return fontCache.get(name());
    }

    public String getFontPath() {
        return FONT_PATH + "/" + name() + ".ttf";
    }

    public File getFontFile() {
        String path = getFontPath();
        String a = getClass().getClassLoader().getResource(path).getFile();
        return new File(a);
    }

    private static Font loadFont(ApplicationFont font) {

        String fontPath = font.getFontPath();

        try {
            return Font.createFont(Font.TRUETYPE_FONT, ApplicationFont.class.getResourceAsStream(fontPath)).deriveFont(DEFAULT_SIZE);
        } catch (FontFormatException | IOException ex) {
            throw new RuntimeException("Failed to load font " + font + ", with path " + fontPath, ex);
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

    static {
        for (ApplicationFont fonts : values()) {
            map.put(fonts.name(), fonts);
        }
    }

    static {
        for (ApplicationFont applicationFont : values()) {
            fontCache.put(applicationFont.name(), loadFont(applicationFont));
        }
    }

    public static ApplicationFont byFamilyName(String familyName) {
        return map.get(familyName);
    }
}
