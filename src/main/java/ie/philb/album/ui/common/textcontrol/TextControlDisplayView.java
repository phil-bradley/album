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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author philb
 */
class TextControlDisplayView extends JPanel implements TextControlEventListener {

    private double fontScalingFactor = 1;
    private TextContent content = new TextContent();
    private JLabel label;

    public TextControlDisplayView() {
        setLayout(new GridBagLayout());
        initComponents();
        layoutComponents();
        setOpaque(false);

        TextControlEventBus.INSTANCE.addListener(this);
    }

    private void initComponents() {
        label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setOpaque(false);
        label.setBorder(null);
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagCellConstraints().fillBoth().weight(1);
        add(label, gbc);
    }

    @Override
    public void contentUpdated(TextContent content) {
        this.content = content;
        this.label.setText(content.getContent());
    }

    @Override
    public void formatUpdated(TextContent content) {
        this.content = content;
        this.label.setFont(getDisplayFont());
        this.label.setForeground(content.getFontColor());
    }

    void setFontScalingFactor(double fontScalingFactor) {
        this.fontScalingFactor = fontScalingFactor;
        this.label.setFont(getDisplayFont());
    }

    private Font getDisplayFont() {
        boolean bold = content.isBold();
        boolean italic = content.isItalic();
        boolean underline = content.isUnderline();
        int scaledSize = (int) (fontScalingFactor * content.getFontSize());

        Font font = ApplicationFont.byFamilyName(content.getFontFamily()).getDerivedFont(bold, italic, underline, scaledSize);
        return font;
    }
}
