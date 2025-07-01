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
class TextControlViewEditPanel extends JPanel implements TextControlChangeListener {

    private final CardLayout cardLayout = new CardLayout();
    private final TextControlModel model;
    private final TextControlDisplayView displayView;
    private final TextControlEditView editView;

    public TextControlViewEditPanel(TextControlModel model) {

        this.model = model;

        displayView = new TextControlDisplayView(model);
        editView = new TextControlEditView(model);

        setOpaque(false);
        setBorder(BorderFactory.createDashedBorder(Color.LIGHT_GRAY));
        setLayout(cardLayout);
        add(editView, "edit");
        add(displayView, "display");

        cardLayout.show(this, "display");
        this.model.addChangeListener(this);
    }

    void setEditor(boolean isEditor) {
        if (isEditor) {
            cardLayout.show(this, "edit");
            editView.requestFocus();
        } else {
            cardLayout.show(this, "display");
        }
    }

    void setFontScalingFactor(double fontScalingFactor) {
        displayView.setFontScalingFactor(fontScalingFactor);
        editView.setFontScalingFactor(fontScalingFactor);
    }

    @Override
    public void requestFocus() {
        editView.requestFocus();
    }
}
