/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.font.fields;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author philb
 */
public class IntField extends JTextField {

    public IntField() {
        this(Integer.MAX_VALUE);
    }
    
    public IntField(int maxValue) {
        this(0, maxValue);
    }

    public IntField(int minValue, int maxValue) {
        ((AbstractDocument) getDocument()).setDocumentFilter(new IntFieldFilter(this, minValue, maxValue));
    }

    @Override
    public void setText(String text) {
        int value = Integer.parseInt(text);
        setValue(value);
    }

    public int getValue() {
        if (super.getText() == null || super.getText().isBlank()) {
            return 0;
        }
        return Integer.parseInt(super.getText());
    }

    public void setValue(int value) {
        super.setText(value + "");
    }

    class IntFieldFilter extends DocumentFilter {

        private final JTextField textField;
        private final int maxValue;
        private final int minValue;

        public IntFieldFilter(JTextField textField, int minValue, int maxValue) {
            this.textField = textField;
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        private boolean isValid(String newText) {
            try {
                int value = Integer.parseInt(newText);

                if (value < minValue) {
                    return false;
                }

                if (value > maxValue) {
                    return false;
                }
            } catch (NumberFormatException ex) {
                return false;
            }

            return true;
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

            textField.setBorder(BorderFactory.createLineBorder(Color.RED, 1));

            // Revert to original after 200ms
            Timer timer = new Timer(200, e -> textField.setBorder(null));
            timer.setRepeats(false);
            timer.start();
        }
    }
}
