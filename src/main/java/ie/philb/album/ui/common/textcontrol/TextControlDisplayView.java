/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.common.font.Fonts;
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
//        label.setFont(getDisplayFont());
        label.setBorder(null);
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagCellConstraints().fillBoth().weight(1);
        add(label, gbc);
    }

    @Override
    public void updated(TextContent content) {
        this.content = content;
        updateDisplayText();
    }

    private void updateDisplayText() {
        System.out.println("Content updated: " + content);
        this.label.setText(content.getContent());

        System.out.println("Looking for font " + content.getFontFamily());
        Fonts fonts = Fonts.byFamilyName(content.getFontFamily());

        System.out.println("Got fonts " + fonts + ", deriving font");
        Font font = fonts.getDerivedFont(content);

        System.out.println("Got derived " + font);
        label.setFont(font);
    }
}
