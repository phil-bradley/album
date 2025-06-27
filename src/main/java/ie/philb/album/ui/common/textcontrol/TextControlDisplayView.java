/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import ie.philb.album.ui.common.GridBagCellConstraints;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author philb
 */
class TextControlDisplayView extends JPanel {

    private JLabel label;

    public TextControlDisplayView() {
        setLayout(new GridBagLayout());
        initComponents();
        layoutComponents();
        setOpaque(false);

    }

    private void initComponents() {
        label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setOpaque(false);
        label.setFont(getDisplayFont());
        label.setBorder(null);
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagCellConstraints().fillBoth().weight(1);
        add(label, gbc);
    }

    protected void setText(String text) {
        label.setText(text);
    }

    protected String getText() {
        return label.getText();
    }

}
