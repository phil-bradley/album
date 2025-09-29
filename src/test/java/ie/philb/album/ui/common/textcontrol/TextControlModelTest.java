/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import ie.philb.album.ui.common.font.ApplicationFont;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class TextControlModelTest {

    @Test
    void whenTextSet_expectedTextReturned() {
        TextControlModel tcm = new TextControlModel();
        assertEquals("", tcm.getText());

        tcm.setText("Hello");
        assertEquals("Hello", tcm.getText());
    }

    @Test
    void whenBoldSet_expectedIsBold() {
        TextControlModel tcm = new TextControlModel();
        assertFalse(tcm.isBold());

        tcm.setBold(true);
        assertTrue(tcm.isBold());

        tcm.setBold(false);
        assertFalse(tcm.isBold());
    }

    @Test
    void whenItalicSet_expectedIsItalic() {
        TextControlModel tcm = new TextControlModel();
        assertFalse(tcm.isItalic());

        tcm.setItalic(true);
        assertTrue(tcm.isItalic());

        tcm.setItalic(false);
        assertFalse(tcm.isItalic());
    }

    @Test
    void whenUnderlineSet_expectedIsUnderline() {
        TextControlModel tcm = new TextControlModel();
        assertFalse(tcm.isUnderline());

        tcm.setUnderline(true);
        assertTrue(tcm.isUnderline());

        tcm.setUnderline(false);
        assertFalse(tcm.isUnderline());
    }

    @Test
    void whenFontColorSet_expectedFontColorReturned() {
        TextControlModel tcm = new TextControlModel();
        assertEquals(TextControlModel.DEFAULT_FONT_COLOR, tcm.getFontColor());

        tcm.setFontColor(Color.MAGENTA);
        assertEquals(Color.MAGENTA, tcm.getFontColor());
    }

    @Test
    void whenFontSizeSet_expectedFontSizeReturned() {
        TextControlModel tcm = new TextControlModel();
        assertEquals(TextControlModel.DEFAULT_FONT_SIZE, tcm.getFontSize());

        tcm.setFontSize(123);
        assertEquals(123, tcm.getFontSize());
    }

    @Test
    void whenFontSet_expectedFontReturned() {
        TextControlModel tcm = new TextControlModel();
        assertEquals(TextControlModel.DEFAULT_FONT.name(), tcm.getFontFamily());

        tcm.setFontFamily(ApplicationFont.Almendra.name());
        assertEquals(ApplicationFont.Almendra.name(), tcm.getFontFamily());
    }

    @Test
    void whenTextSet_expectedTextUpdatedEventFired() {
        TestTextControlChangeListener listener = new TestTextControlChangeListener();
        TextControlModel tcm = new TextControlModel();
        tcm.addChangeListener(listener);

        assertFalse(listener.isTextUpdated);
        tcm.setText("Hello");
        assertTrue(listener.isTextUpdated);
    }

    @Test
    void whenBoldSet_expectedTextUpdatedEventFired() {
        TestTextControlChangeListener listener = new TestTextControlChangeListener();
        TextControlModel tcm = new TextControlModel();
        tcm.addChangeListener(listener);

        assertFalse(listener.isFormatUpdated);
        tcm.setBold(true);
        assertTrue(listener.isFormatUpdated);
    }

    @Test
    void whenUnderlineSet_expectedTextUpdatedEventFired() {
        TestTextControlChangeListener listener = new TestTextControlChangeListener();
        TextControlModel tcm = new TextControlModel();
        tcm.addChangeListener(listener);

        assertFalse(listener.isFormatUpdated);
        tcm.setUnderline(true);
        assertTrue(listener.isFormatUpdated);
    }

    @Test
    void whenItalicSet_expectedTextUpdatedEventFired() {
        TestTextControlChangeListener listener = new TestTextControlChangeListener();
        TextControlModel tcm = new TextControlModel();
        tcm.addChangeListener(listener);

        assertFalse(listener.isFormatUpdated);
        tcm.setItalic(true);
        assertTrue(listener.isFormatUpdated);
    }

    @Test
    void whenFontSizeSet_expectedTextUpdatedEventFired() {
        TestTextControlChangeListener listener = new TestTextControlChangeListener();
        TextControlModel tcm = new TextControlModel();
        tcm.addChangeListener(listener);

        assertFalse(listener.isFormatUpdated);
        tcm.setFontSize(123);
        assertTrue(listener.isFormatUpdated);
    }

    @Test
    void whenFontColorSet_expectedTextUpdatedEventFired() {
        TestTextControlChangeListener listener = new TestTextControlChangeListener();
        TextControlModel tcm = new TextControlModel();
        tcm.addChangeListener(listener);

        assertFalse(listener.isFormatUpdated);
        tcm.setFontColor(Color.CYAN);
        assertTrue(listener.isFormatUpdated);
    }

    @Test
    void whenFontFamilySet_expectedTextUpdatedEventFired() {
        TestTextControlChangeListener listener = new TestTextControlChangeListener();
        TextControlModel tcm = new TextControlModel();
        tcm.addChangeListener(listener);

        assertFalse(listener.isFormatUpdated);
        tcm.setFontFamily(ApplicationFont.CormorantUpright.name());
        assertTrue(listener.isFormatUpdated);
    }

    @Test
    void whenFormatSet_andNotChanged_expectedTextUpdatedEventNotFired() {

        TextControlModel tcm = new TextControlModel();
        tcm.setBold(true);
        tcm.setItalic(true);
        tcm.setUnderline(true);
        tcm.setFontSize(123);
        tcm.setFontFamily(ApplicationFont.Caveat.name());
        tcm.setFontColor(Color.MAGENTA);

        TestTextControlChangeListener listener = new TestTextControlChangeListener();
        tcm.addChangeListener(listener);

        assertFalse(listener.isFormatUpdated);
        tcm.setBold(true);
        tcm.setItalic(true);
        tcm.setUnderline(true);
        tcm.setFontSize(123);
        tcm.setFontFamily(ApplicationFont.Caveat.name());
        tcm.setFontColor(Color.MAGENTA);

        // Since there's no change, expected no format update
        assertFalse(listener.isFormatUpdated);
    }

    @Test
    void whentextEditSelected_expectedEventFired() {
        TestTextControlChangeListener listener = new TestTextControlChangeListener();
        TextControlModel tcm = new TextControlModel();
        tcm.addChangeListener(listener);

        assertFalse(listener.isTextEditSelected);
        tcm.textEditSelected();
        assertTrue(listener.isTextEditSelected);
    }

    @Test
    void whentextEditCancelled_expectedEventFired() {
        TestTextControlChangeListener listener = new TestTextControlChangeListener();
        TextControlModel tcm = new TextControlModel();
        tcm.addChangeListener(listener);

        assertFalse(listener.isTextEditCancelled);
        tcm.cancelTextEdit();
        assertTrue(listener.isTextEditCancelled);
    }

    class TestTextControlChangeListener implements TextControlChangeListener {

        boolean isTextEditSelected = false;
        boolean isTextEditCancelled = false;
        boolean isTextUpdated = false;
        boolean isFormatUpdated = false;

        @Override
        public void textEditSelected(TextControlModel model) {
            isTextEditSelected = true;
        }

        @Override
        public void textEditCancelled(TextControlModel model) {
            isTextEditCancelled = true;
        }

        @Override
        public void textUpdated(TextControlModel model) {
            isTextUpdated = true;
        }

        @Override
        public void formatUpdated(TextControlModel model) {
            isFormatUpdated = true;
        }

    };
}
