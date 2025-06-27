/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import ie.philb.album.ui.common.AppPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;

/**
 *
 * @author philb
 */
class ViewEditPanel extends AppPanel {

    private final CardLayout cardLayout = new CardLayout();

    private TextControlDisplayView displayView;
    private TextControlEditView editView;

    public ViewEditPanel() {

        displayView = new TextControlDisplayView();
        editView = new TextControlEditView();

        setOpaque(false);
        setBorder(BorderFactory.createDashedBorder(Color.LIGHT_GRAY));
        setLayout(cardLayout);
        add(editView, "edit");
        add(displayView, "display");

        editView.addActionListener((ActionEvent e) -> {
            displayView.setText(editView.getText());
            cardLayout.show(this, "display");
        });

        cardLayout.show(this, "display");
    }

    public void addActionListener(ActionListener l) {
        editView.addActionListener(l);
    }

    private String getText() {
        return displayView.getText();
    }

    private void setText(String text) {
        displayView.setText(text);
        editView.setText(text);
    }

    void setEditor(boolean b) {
        cardLayout.show(this, "edit");
        editView.requestFocus();
    }

}
