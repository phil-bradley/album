/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.font.TextAttribute;
import static java.awt.font.TextAttribute.UNDERLINE_ON;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author philb
 */
public class FontProvider {

    private static final float DEFAULT_SIZE = 16;

    public static Font GREAT_VIBES;
    public static Font CAVEAT;
    public static Font ITALIANNO;
    public static Font KAPAKANA;
    public static Font NOTO_SERIF;
    public static Font OPEN_SANS;
    public static Font PARISIENNE;
    public static Font PINYON_SCRIPT;
    public static Font TANGERINE;

    public FontProvider() {
        try {
            GREAT_VIBES = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/ie/philb/album/fonts/GreatVibes.ttf")).deriveFont(DEFAULT_SIZE);
            CAVEAT = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/ie/philb/album/fonts/Caveat.ttf")).deriveFont(DEFAULT_SIZE);
            ITALIANNO = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/ie/philb/album/fonts/Italianno.ttf")).deriveFont(DEFAULT_SIZE);
            KAPAKANA = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/ie/philb/album/fonts/Kapakana.ttf")).deriveFont(DEFAULT_SIZE);
            NOTO_SERIF = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/ie/philb/album/fonts/NotoSerif.ttf")).deriveFont(DEFAULT_SIZE);
            OPEN_SANS = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/ie/philb/album/fonts/OpenSans.ttf")).deriveFont(DEFAULT_SIZE);
            PARISIENNE = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/ie/philb/album/fonts/Parisienne.ttf")).deriveFont(DEFAULT_SIZE);
            PINYON_SCRIPT = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/ie/philb/album/fonts/PinyonScript.ttf")).deriveFont(DEFAULT_SIZE);
            TANGERINE = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/ie/philb/album/fonts/Tangerine.ttf")).deriveFont(DEFAULT_SIZE);

        } catch (FontFormatException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<Font> getFonts() {

        return List.of(
                OPEN_SANS,
                NOTO_SERIF,
                GREAT_VIBES,
                ITALIANNO,
                CAVEAT,
                KAPAKANA,
                PARISIENNE,
                PINYON_SCRIPT,
                TANGERINE
        );
    }

    public Font getDerivedFont(Font font, boolean bold, boolean italic, boolean underline, int size) {

        int style = Font.PLAIN;

        if (bold) {
            style = style | Font.BOLD;
        }

        if (italic) {
            style = style | Font.ITALIC;
        }

        Font derived = font.deriveFont(style).deriveFont((float) size);

        if (underline) {
            Map<TextAttribute, Integer> attrs = new HashMap<>();
            attrs.put(TextAttribute.UNDERLINE, UNDERLINE_ON);
            return derived.deriveFont(attrs);
        } else {
            return derived;
        }

    }
}
