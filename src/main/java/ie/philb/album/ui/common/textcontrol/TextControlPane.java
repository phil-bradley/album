/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import static javax.swing.JComponent.WHEN_FOCUSED;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import static javax.swing.KeyStroke.getKeyStroke;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author philb
 */
public class TextControlPane extends JPanel {

    private final List<TextControlPaneListener> listeners = new ArrayList<>();
    private boolean isBold;
    private boolean isItalic;
    private boolean isUnderline;

    private final JTextPane textPane = new JTextPane();

    public TextControlPane() {
        setOpaque(true);
        setBackground(Color.white);
        setLayout(new GridBagLayout());   // centers a non-filling child both ways

        textPane.setBorder(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // stretch width, keep natural height
        gbc.weightx = 1.0;
        add(textPane, gbc);

        textPane.getInputMap(WHEN_FOCUSED).put(getKeyStroke(VK_ESCAPE, 0), "stopEditing");

        textPane.getActionMap().put("stopEditing", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopEditing();
                fireEditingCompleted();
            }
        });

        stopEditing();
    }

    public void addListener(TextControlPaneListener l) {
        listeners.add(l);
    }

    public void removeListener(TextControlPaneListener l) {
        listeners.remove(l);
    }

    private void fireEditingCompleted() {
        for (TextControlPaneListener l : listeners) {
            l.editingCompleted();
        }
    }

    public void setDisplayFont(Font font, Color color, boolean isBold, boolean isItalic, boolean isUnderline) {
        
        if (textPane == null) {
            return;
        }

        this.isBold = isBold;
        this.isItalic = isItalic;
        this.isUnderline = isUnderline;
        
        textPane.setFont(font);
        
        super.setForeground(color);
        textPane.setForeground(color);
        
        applyCharacterAttributes();
        applyCenterAlignment();
        repaint();
    }

    @Override
    public Font getFont() {
        
        if (textPane == null) {
            return null;
        }
        return textPane.getFont();
    }

    public final void setText(String text) {
        textPane.setText(text);
        applyCharacterAttributes();
        applyCenterAlignment();
    }

    private void applyCenterAlignment() {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    private void startEditing() {
        System.out.println("Starting to edit");
        textPane.setEditable(true);
        textPane.setFocusable(true);
        textPane.requestFocusInWindow();
        textPane.setCaretPosition(textPane.getDocument().getLength());
        textPane.getCaret().setVisible(true);
    }

    private void stopEditing() {
        System.out.println("Stopped editing");
        textPane.setEditable(false);
        textPane.setFocusable(false);
        textPane.select(0, 0);  // clear selection
        textPane.getCaret().setVisible(false);

    }

    private void applyCharacterAttributes() {
        Font font = textPane.getFont();
        Color color = textPane.getForeground();
        StyledDocument doc = textPane.getStyledDocument();

        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attrs, font.getFamily());
        StyleConstants.setFontSize(attrs, font.getSize());
        StyleConstants.setBold(attrs, isBold);
        StyleConstants.setItalic(attrs, isItalic);
        StyleConstants.setUnderline(attrs, isUnderline);
        StyleConstants.setForeground(attrs, color);

        // Style the existing text...
        doc.setCharacterAttributes(0, doc.getLength(), attrs, false);
        // ...and make new/edited text inherit it too.
        textPane.setCharacterAttributes(attrs, false);
    }

    void setEditorEnabled(boolean enabled) {
        if (enabled && !textPane.isEditable()) {
            startEditing();
        }

        if (!enabled && textPane.isEditable()) {
            stopEditing();
        }
    }

}
