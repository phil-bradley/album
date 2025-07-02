/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.common.font.ApplicationFont;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author philb
 */
class TextControlEditView extends JPanel implements TextControlChangeListener {

    private double fontScalingFactor = 1;
    private final TextControlModel model;
    private PromptTextField field;

    public TextControlEditView(TextControlModel model) {
        this.model = model;

        setLayout(new GridBagLayout());
        initComponents();
        layoutComponents();
        setOpaque(false);

        this.model.addChangeListener(this);
    }

    private void initComponents() {

        field = new PromptTextField("Enter Text...");
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBorder(null);
        field.setOpaque(false);
        field.setMargin(new Insets(0, 0, 0, 0));
        field.setText(model.getText());

        formatUpdated(model);

        field.addActionListener((ActionEvent e) -> {
            model.setText(field.getText());
        });

        field.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    field.setText(model.getText());
                    model.cancelTextEdit();
                }
            }
        });

    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagCellConstraints().fillBoth().weight(1);
        add(field, gbc);
    }

    @Override
    public void requestFocus() {
        field.requestFocus();
    }

    @Override
    public void formatUpdated(TextControlModel model) {
        field.setFont(getDisplayFont());
        field.setForeground(model.getFontColor());
    }

    private Font getDisplayFont() {
        boolean bold = model.isBold();
        boolean italic = model.isItalic();
        boolean underline = model.isUnderline();
        int scaledSize = (int) (fontScalingFactor * model.getFontSize());

        Font font = ApplicationFont.byFamilyName(model.getFontFamily()).getDerivedFont(bold, italic, underline, scaledSize);
        return font;
    }

    void setFontScalingFactor(double fontScalingFactor) {
        this.fontScalingFactor = fontScalingFactor;
        field.setFont(getDisplayFont());
    }
}
