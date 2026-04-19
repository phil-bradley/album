/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.fields;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author philb
 */
public class MaxLengthTextField extends PromptField {

    private final int maxLength;
    
    public MaxLengthTextField(int maxLength) {
        this.maxLength = maxLength;
        ((AbstractDocument) getDocument()).setDocumentFilter(new MaxLengthFilter(this, maxLength));
    }

    @Override
    protected String getDefaultPrompt() {
        return "Max length " + maxLength;
    }

    class MaxLengthFilter extends DocumentFilter {

        private final JTextField textField;
        private final int maxLength;

        public MaxLengthFilter(JTextField textField, int maxLength) {
            this.textField = textField;
            this.maxLength = maxLength;
        }

        private boolean isValid(String newText) {
            return newText.length() <= maxLength;
        }

        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            StringBuilder currentText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
            currentText.insert(offset, string);

            if (isValid(currentText.toString())) {
                super.insertString(fb, offset, string, attr);
            } else {
                indicateValidationFailure();
            }
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

            StringBuilder currentText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
            currentText.replace(offset, offset + length, text);

            if (isValid(currentText.toString())) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                indicateValidationFailure();
            }
        }

        private void indicateValidationFailure() {

            Border originalBorder = textField.getBorder();

            Toolkit.getDefaultToolkit().beep();
            textField.setBorder(BorderFactory.createLineBorder(Color.RED, 1));

            // Revert to original after 200ms
            Timer timer = new Timer(200, e -> textField.setBorder(originalBorder));
            timer.setRepeats(false);
            timer.start();
        }
    }
}
