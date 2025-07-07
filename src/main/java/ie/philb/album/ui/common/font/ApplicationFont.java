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
    private static final Map<FontSpec, Font> fontCache = new HashMap<>();

    public Font getFont(boolean bold, boolean italic) {
        return fontCache.get(new FontSpec(name(), bold, italic));
    }

    public Font getRegularFont() {
        return getFont(false, false);
    }

    public String getFontPath(boolean bold, boolean italic) {
        String baseName = name();

        if (bold) {
            baseName += "-Bold";
        }

        if (italic) {
            baseName += "-Italic";
        }

        return FONT_PATH + "/" + baseName + ".ttf";
    }

    public File getFontFile(boolean bold, boolean italic) {
        String path = getFontPath(bold, italic);
        String a = getClass().getClassLoader().getResource(path).getFile();
        return new File(a);
    }

    private static Font loadFont(ApplicationFont font, boolean bold, boolean italic) {

        String fontPath = font.getFontPath(bold, italic);

        try {
            Font created = Font.createFont(Font.TRUETYPE_FONT, ApplicationFont.class.getResourceAsStream(fontPath)).deriveFont(DEFAULT_SIZE);
            return created;
        } catch (FontFormatException | IOException ex) {
            throw new RuntimeException("Failed to load font " + font + ", with path " + fontPath, ex);
        }
    }

    public Font getDerivedFont(boolean bold, boolean italic, boolean underline, int size) {

        Font baseFont = getFont(bold, italic);
        Font derived = baseFont.deriveFont((float) size);

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
            fontCache.put(new FontSpec(applicationFont.name(), false, false), loadFont(applicationFont, false, false));
            fontCache.put(new FontSpec(applicationFont.name(), true, false), loadFont(applicationFont, true, false));
            fontCache.put(new FontSpec(applicationFont.name(), false, true), loadFont(applicationFont, false, true));
            fontCache.put(new FontSpec(applicationFont.name(), true, true), loadFont(applicationFont, true, true));

        }
    }

    public static ApplicationFont byFamilyName(String familyName) {
        return map.get(familyName);
    }

    record FontSpec(String familyName, boolean bold, boolean italic) {

    }
}
