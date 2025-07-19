/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author philb
 */
public enum ApplicationFont {
    NotoSerif(),
    OpenSans(),
    Orbitron(),
    Genos(),
    Caveat(),
    EduQLD(),
    DancingScript(),
    Tangerine(),
    CormorantUpright(),
    Almendra(),
    GrenzeGotisch(),;

    private static final float DEFAULT_SIZE = 16;
    private static final String FONT_PATH = "/ie/philb/album/fonts";
    private static final Map<String, ApplicationFont> map = new HashMap<>();
    private static final Map<String, Font> regularFonts = new HashMap<>();
    private static final Map<String, Font> boldFonts = new HashMap<>();
    private static final Map<String, Font> italicFonts = new HashMap<>();
    private static final Map<String, Font> boldItalicFonts = new HashMap<>();

    public Font getFont(boolean bold, boolean italic) {

        if (!bold && !italic) {
            return regularFonts.get(name());
        }

        if (bold && !italic) {
            return boldFonts.get(name());
        }

        if (!bold && italic) {
            return italicFonts.get(name());
        }

        return boldItalicFonts.get(name());
    }

    public boolean hasBold() {
        return boldFonts.get(name()) != null;
    }

    public boolean hasItalic() {
        return italicFonts.get(name()) != null;
    }

    public boolean hasBoldItalic() {
        return boldItalicFonts.get(name()) != null;
    }

    public String getFontPath(boolean bold, boolean italic) {
        String suffix = "";

        if (bold && italic) {
            suffix = "-BoldItalic";
        } else if (bold) {
            suffix = "-Bold";
        } else if (italic) {
            suffix = "-Italic";
        }

        return FONT_PATH + "/" + name() + suffix + ".ttf";
    }

    public File getFontFile(boolean bold, boolean italic) {
        String path = getFontPath(bold, italic);
        String a = getClass().getClassLoader().getResource(path).getFile();
        return new File(a);
    }

    private static Font loadFont(ApplicationFont font, boolean bold, boolean italic) {

        String fontPath = font.getFontPath(bold, italic);

        try {
            return Font.createFont(Font.TRUETYPE_FONT, ApplicationFont.class.getResourceAsStream(fontPath)).deriveFont(DEFAULT_SIZE);
        } catch (FontFormatException | IOException ex) {
            return null;
        }
    }

    static {
        for (ApplicationFont fonts : values()) {
            map.put(fonts.name(), fonts);
        }
    }

    static {
        for (ApplicationFont applicationFont : values()) {
            regularFonts.put(applicationFont.name(), loadFont(applicationFont, false, false));
            boldFonts.put(applicationFont.name(), loadFont(applicationFont, true, false));
            italicFonts.put(applicationFont.name(), loadFont(applicationFont, false, true));
            boldItalicFonts.put(applicationFont.name(), loadFont(applicationFont, true, true));
        }
    }

    public static ApplicationFont byFamilyName(String familyName) {
        return map.get(familyName);
    }
}
