/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import ie.philb.album.ui.common.font.ApplicationFont;
import ie.philb.album.ui.font.FontSelector;
import java.awt.Color;
import java.lang.reflect.Field;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author philb
 */
public class TextControlEditorToolBarTest {

    private TextControlModel model;
    private TextControlEditorToolBar toolbar;

    @BeforeEach
    void setUp() {
        model = new TextControlModel();
        model.setFontFamily(ApplicationFont.Almendra.name());
        model.setFontSize(12);
        model.setBold(false);
        model.setItalic(false);
        model.setUnderline(false);
        model.setFontColor(Color.CYAN);

        toolbar = new TextControlEditorToolBar(model);
    }

    @Test
    void boldToggleUpdatesModel() throws IllegalAccessException {
        JToggleButton btnBold = (JToggleButton) FieldUtils.readField(toolbar, "btnBold", true);
        btnBold.doClick();
        assertTrue(model.isBold());

        btnBold.doClick();
        assertFalse(model.isBold());
    }

    @Test
    void italicToggleUpdatesModel() throws IllegalAccessException {
        JToggleButton btnItalic = (JToggleButton) FieldUtils.readField(toolbar, "btnItalic", true);
        btnItalic.doClick();
        assertTrue(model.isItalic());

        btnItalic.doClick();
        assertFalse(model.isItalic());
    }

    @Test
    void underlineToggleUpdatesModel() throws IllegalAccessException {
        JToggleButton btnUnderline = (JToggleButton) FieldUtils.readField(toolbar, "btnUnderline", true);
        btnUnderline.doClick();
        assertTrue(model.isUnderline());

        btnUnderline.doClick();
        assertFalse(model.isUnderline());
    }

    @Test
    void fontSelectUpdatesModel() throws IllegalAccessException {
        FontSelector fontSelector = (FontSelector) FieldUtils.readField(toolbar, "fontSelector", true);
        fontSelector.setSelectedItem(ApplicationFont.Caveat);
        assertEquals(ApplicationFont.Caveat.name(), model.getFontFamily());
    }

    @Test
    void fontSSizeelectUpdatesModel() throws IllegalAccessException {
        JComboBox<Integer> fontSizeSelector = (JComboBox<Integer>) FieldUtils.readField(toolbar, "fontSizeSelector", true);
        fontSizeSelector.setSelectedItem(72);
        assertEquals(72, model.getFontSize());
    }
}
