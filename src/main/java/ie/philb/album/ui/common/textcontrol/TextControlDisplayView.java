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
class TextControlDisplayView extends JPanel implements TextControlChangeListener {

    private double fontScalingFactor = 1;
    private final TextControlModel model;
    private final JLabel label = new JLabel();

    public TextControlDisplayView(TextControlModel model) {
        this.model = model;
        setLayout(new GridBagLayout());
        initComponents();
        layoutComponents();
        setOpaque(false);

        formatUpdated(model);
        textUpdated(model);

        model.addChangeListener(this);
    }

    private void initComponents() {
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
    public final void textUpdated(TextControlModel model) {
        this.label.setText(model.getText());
    }

    @Override
    public final void formatUpdated(TextControlModel model) {
        this.label.setFont(getDisplayFont());
        this.label.setForeground(model.getFontColor());
    }

    void setFontScalingFactor(double fontScalingFactor) {
        this.fontScalingFactor = fontScalingFactor;
        this.label.setFont(getDisplayFont());
    }

    private Font getDisplayFont() {
        boolean bold = model.isBold();
        boolean italic = model.isItalic();
        boolean underline = model.isUnderline();
        int scaledSize = (int) (fontScalingFactor * model.getFontSize());

        Font font = ApplicationFont.byFamilyName(model.getFontFamily()).getDerivedFont(bold, italic, underline, scaledSize);
        return font;
    }
}
