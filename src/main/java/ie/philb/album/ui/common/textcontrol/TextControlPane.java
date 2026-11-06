/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import static javax.swing.JComponent.WHEN_FOCUSED;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import static javax.swing.KeyStroke.getKeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.View;

/**
 *
 * @author philb
 */
public class TextControlPane extends JPanel implements TextControlChangeListener {

    private final List<TextControlPaneListener> listeners = new ArrayList<>();
//    private boolean isBold;
//    private boolean isItalic;
//    private boolean isUnderline;
    private final Border activeBorder = BorderFactory.createDashedBorder(Color.LIGHT_GRAY, 1, 3, 1, true);
    private final Border inActiveBorder = BorderFactory.createLineBorder(Color.WHITE, 1);
    private double fontScalingFactor = 1;
    private final TextControlModel model;
    private final JTextPane textPane;
    private String originalText = "";

    public TextControlPane(TextControlModel model) {
        this.model = model;

        textPane = new JTextPane() {
            @Override
            public Dimension getPreferredSize() {
                // Always measure the text as if there were unlimited width,
                // so it never wraps and the height doesn't depend on current size.
                View root = getUI().getRootView(this);
                Document doc = getDocument();
                float w, h;
                if (doc instanceof AbstractDocument) {
                    ((AbstractDocument) doc).readLock();
                }
                try {
                    root.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
                    w = root.getPreferredSpan(View.X_AXIS);
                    h = root.getPreferredSpan(View.Y_AXIS);
                } finally {
                    if (doc instanceof AbstractDocument) {
                        ((AbstractDocument) doc).readUnlock();
                    }
                }
                Insets in = getInsets();
                return new Dimension((int) Math.ceil(w) + in.left + in.right,
                        (int) Math.ceil(h) + in.top + in.bottom);
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize(); // stops GridBagLayout shrinking it
            }
        };

        setOpaque(true);
        setBackground(Color.white);
        setLayout(new BorderLayout());

        setText(model.getText());
        textPane.setBorder(null);
        textPane.setBackground(Color.white);

        CenteringPanel holder = new CenteringPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // border fills width when there's room
        gbc.weightx = 1.0;                        // weighty stays 0 -> centred vertically
        gbc.anchor = GridBagConstraints.CENTER;
        holder.add(textPane, gbc);

        JScrollPane sp = new JScrollPane(holder);
        sp.setBorder(null);
        sp.getViewport().setBackground(Color.white);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(sp, CENTER);

        textPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fireMouseClicked(e);
            }

        });

        textPane.getInputMap(WHEN_FOCUSED).put(getKeyStroke(VK_ESCAPE, 0), "cancelEditing");

        textPane.getActionMap().put("cancelEditing", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelEditing();
                fireEditingCancelled();
            }
        });

        textPane.getInputMap(WHEN_FOCUSED).put(getKeyStroke(KeyEvent.VK_ENTER, CTRL_DOWN_MASK), "finishEditing");

        textPane.getActionMap().put("finishEditing", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finishEditing();
                fireEditingCompleted();
            }
        });

        model.addChangeListener(this);
        finishEditing();
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

    private void fireEditingCancelled() {
        for (TextControlPaneListener l : listeners) {
            l.editingCancelled();
        }
    }

    private void fireMouseClicked(MouseEvent e) {
        for (TextControlPaneListener l : listeners) {
            l.mouseClicked(e);
        }
    }

//    public void setDisplayFont(Font font, Color color, boolean isBold, boolean isItalic, boolean isUnderline) {
//
//        if (textPane == null) {
//            return;
//        }
//
    ////        this.isBold = isBold;
////        this.isItalic = isItalic;
////        this.isUnderline = isUnderline;
//
//        textPane.setFont(font);
//
//        super.setForeground(color);
//        textPane.setForeground(color);
//
//        applyCharacterAttributes();
//        applyCenterAlignment();
//        repaint();
//    }

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

    public String getText() {
        return textPane.getText();
    }

    private void applyCenterAlignment() {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        repaint();
    }

    private void startEditing() {

        originalText = textPane.getText();

        System.out.println("Starting to edit");
        textPane.setEditable(true);
        textPane.setFocusable(true);
        textPane.requestFocusInWindow();
        textPane.setCaretPosition(textPane.getDocument().getLength());
        textPane.getCaret().setVisible(true);
    }

    private void cancelEditing() {
        setText(originalText);
        textPane.setEditable(false);
        textPane.setFocusable(false);
        textPane.select(0, 0);  // clear selection
        textPane.getCaret().setVisible(false);
    }

    private void finishEditing() {
        System.out.println("Finished editing");
        textPane.setEditable(false);
        textPane.setFocusable(false);
        textPane.select(0, 0);  // clear selection
        textPane.getCaret().setVisible(false);
        model.setText(getText());

    }

    private void applyCharacterAttributes() {

        Font font = model.getDisplayFont(fontScalingFactor);

        StyledDocument doc = textPane.getStyledDocument();

        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attrs, font.getFamily());
        StyleConstants.setFontSize(attrs, font.getSize());
        StyleConstants.setBold(attrs, model.isBold());
        StyleConstants.setItalic(attrs, model.isItalic());
        StyleConstants.setUnderline(attrs, model.isUnderline());
        StyleConstants.setForeground(attrs, model.getFontColor());

        // Make the default style carry the font, so new/empty paragraphs inherit it
        Style defaultStyle = doc.getStyle(StyleContext.DEFAULT_STYLE);
        defaultStyle.addAttributes(attrs);

        // Style the existing text...
        doc.setCharacterAttributes(0, doc.getLength(), attrs, false);
        // ...and make new/edited text inherit it too.
        textPane.setCharacterAttributes(attrs, false);

        repaint();
    }

    void setEditorEnabled(boolean enabled) {

        if (enabled) {
            textPane.setBorder(activeBorder);
            startEditing();
        } else {
            textPane.setBorder(inActiveBorder);
            finishEditing();
        }
    }

    void setFontScalingFactor(double fontScalingFactor, TextControlModel model) {
        this.fontScalingFactor = fontScalingFactor;
        applyCharacterAttributes();
        applyCenterAlignment();
    }

    @Override
    public void formatUpdated(TextControlModel model) {
        System.out.println("Setting font: " + model.getDisplayFont(1));
        setForeground(model.getFontColor());
        applyCharacterAttributes();
        applyCenterAlignment();
        repaint();
    }

    @Override
    public void textUpdated(TextControlModel model) {
        setText(model.getText());
    }

    private static class CenteringPanel extends JPanel implements Scrollable {

        CenteringPanel() {
            super(new GridBagLayout());
            setOpaque(true);
            setBackground(Color.white);
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle r, int orientation, int direction) {
            return 16;
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle r, int orientation, int direction) {
            return orientation == SwingConstants.HORIZONTAL ? r.width : r.height;
        }

        // Fill the viewport when the content fits (this gives the centring);
        // otherwise keep the natural size and let the scroll pane clip.
        @Override
        public boolean getScrollableTracksViewportWidth() {
            return getParent() instanceof JViewport
                    && getPreferredSize().width <= getParent().getWidth();
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return getParent() instanceof JViewport
                    && getPreferredSize().height <= getParent().getHeight();
        }
    }
}
