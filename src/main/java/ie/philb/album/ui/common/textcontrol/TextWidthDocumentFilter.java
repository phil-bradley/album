/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author philb
 */
public class TextWidthDocumentFilter extends DocumentFilter {

    private final JTextField textField;

    public TextWidthDocumentFilter(JTextField textField) {
        this.textField = textField;
    }

    private boolean isValid(String newText) {
        FontMetrics metrics = textField.getFontMetrics(textField.getFont());
        int width = metrics.stringWidth(newText);
        return width <= textField.getWidth();
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        StringBuilder currentText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
        currentText.insert(offset, string);

        if (isValid(currentText.toString())) {
            super.insertString(fb, offset, string, attr);
        } else {
            indicateValidationFailure();
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

        StringBuilder currentText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
        currentText.replace(offset, offset + length, text);

        if (isValid(currentText.toString())) {
            super.replace(fb, offset, length, text, attrs);
        } else {
            indicateValidationFailure();
        }
    }

    private void indicateValidationFailure() {

        Toolkit.getDefaultToolkit().beep();

        textField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        // Revert to original after 200ms
        Timer timer = new Timer(200, e -> textField.setBorder(null));
        timer.setRepeats(false);
        timer.start();
    }
}
