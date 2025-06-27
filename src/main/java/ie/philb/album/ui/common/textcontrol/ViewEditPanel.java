/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author philb
 */
class ViewEditPanel extends JPanel {

    private final CardLayout cardLayout = new CardLayout();

    private final TextControlDisplayView displayView = new TextControlDisplayView();
    private final TextControlEditView editView = new TextControlEditView();

    public ViewEditPanel() {

        setOpaque(false);
        setBorder(BorderFactory.createDashedBorder(Color.LIGHT_GRAY));
        setLayout(cardLayout);
        add(editView, "edit");
        add(displayView, "display");

        cardLayout.show(this, "display");
    }

    void setEditor(boolean b) {
        cardLayout.show(this, "edit");
        editView.requestFocus();
    }

    void setFontScalingFactor(double fontScalingFactor) {
        displayView.setFontScalingFactor(fontScalingFactor);
        editView.setFontScalingFactor(fontScalingFactor);
    }
}
