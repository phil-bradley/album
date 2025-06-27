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
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author philb
 */
class TextControlEditView extends JPanel implements TextControlEventListener {

    private double fontScalingFactor = 1;
    private TextContent content = null;
    private PromptTextField field;

    public TextControlEditView() {
        setLayout(new GridBagLayout());
        initComponents();
        layoutComponents();
        setOpaque(false);

        TextControlEventBus.INSTANCE.addListener(this);
    }

    private void initComponents() {

        field = new PromptTextField("Enter Text...");
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBorder(null);
        field.setOpaque(false);
        field.setMargin(new Insets(0, 0, 0, 0));

        field.addActionListener((ActionEvent e) -> {
            content.setContent(field.getText());
            TextControlEventBus.INSTANCE.contentUpdated(content);
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
    public void contentUpdated(TextContent content) {
        String savedText = null;

        if (this.content != null) {
            savedText = this.content.getContent();
        }

        this.content = content;

        if (savedText != null) {
            this.content.setContent(savedText);
        }

        updateEditor();
    }

    @Override
    public void formatUpdated(TextContent content) {
        this.content = content;

        field.setText(content.getContent());
        field.setFont(getDisplayFont());
        field.setForeground(content.getFontColor());
    }

    private void updateEditor() {
        field.setText(content.getContent());
        field.setFont(getDisplayFont());
        field.setForeground(content.getFontColor());
    }

    private Font getDisplayFont() {
        boolean bold = content.isBold();
        boolean italic = content.isItalic();
        boolean underline = content.isUnderline();
        int scaledSize = (int) (fontScalingFactor * content.getFontSize());

        Font font = ApplicationFont.byFamilyName(content.getFontFamily()).getDerivedFont(bold, italic, underline, scaledSize);
        return font;
    }

    void setFontScalingFactor(double fontScalingFactor) {
        this.fontScalingFactor = fontScalingFactor;
        field.setFont(getDisplayFont());
    }
}
