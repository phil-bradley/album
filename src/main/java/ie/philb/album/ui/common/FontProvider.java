/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common;

import com.lowagie.text.pdf.BaseFont;
import ie.philb.album.ui.common.font.ApplicationFont;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author philb
 */
public class FontProvider {

    private final Map<FontKey, BaseFont> fontMap = new HashMap<>();

    record FontKey(ApplicationFont font, boolean isBold, boolean isItalic) {

    }

    public BaseFont getFont(ApplicationFont font, boolean isBold, boolean isItalic) throws IOException {

        FontKey key = new FontKey(font, isBold, isItalic);

        if (fontMap.containsKey(key)) {
            return fontMap.get(key);
        }
        
        BaseFont baseFont = loadFont(font, isBold, isItalic);
        fontMap.put(key, baseFont);
        
        return baseFont;
    }

    private BaseFont loadFont(ApplicationFont font, boolean isBold, boolean isItalic) throws IOException {

        File tempFontFile = File.createTempFile("tempfont", ".ttf");
        tempFontFile.deleteOnExit();

        try (InputStream is = getClass().getResourceAsStream(font.getFontPath(isBold, isItalic))) {
            Files.copy(is, tempFontFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        
        BaseFont baseFont = BaseFont.createFont(tempFontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);       
        return baseFont;
    }
}
