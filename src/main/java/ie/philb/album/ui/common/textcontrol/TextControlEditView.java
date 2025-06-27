/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import ie.philb.album.ui.common.GridBagCellConstraints;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author philb
 */
class TextControlEditView extends JPanel {

    private PromptTextField field;

    public TextControlEditView() {
        setLayout(new GridBagLayout());
        initComponents();
        layoutComponents();
        setOpaque(false);
    }

    private void initComponents() {

        field = new PromptTextField("Enter Text...");
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBorder(null);
        field.setOpaque(false);
        field.setFont(getDisplayFont());
        field.setMargin(new Insets(0, 0, 0, 0));
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagCellConstraints().fillBoth().weight(1);
        add(field, gbc);
    }

    public void addActionListener(ActionListener l) {
        this.field.addActionListener(l);

    }

    protected void setText(String text) {
        field.setText(text);
    }

    protected String getText() {
        return field.getText();
    }

    @Override
    public void requestFocus() {
        field.requestFocus();
    }
}
