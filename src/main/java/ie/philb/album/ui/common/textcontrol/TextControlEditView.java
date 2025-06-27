/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import ie.philb.album.ui.common.GridBagCellConstraints;
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

    private TextContent content = new TextContent();
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
//        field.setFont(getDisplayFont());
        field.setMargin(new Insets(0, 0, 0, 0));

        field.addActionListener((ActionEvent e) -> {
            content.setContent(field.getText());
            TextControlEventBus.INSTANCE.updated(content);
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
    public void updated(TextContent content) {
        this.content = content;
        updateEditor();
    }

    private void updateEditor() {
        this.field.setText(content.getContent());
    }
}
