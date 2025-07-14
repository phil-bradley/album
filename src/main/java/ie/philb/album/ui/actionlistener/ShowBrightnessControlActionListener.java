/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.actionlistener;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 *
 * @author philb
 */
public class ShowBrightnessControlActionListener extends AbstractCellActionListener {

    @Override
    protected void doAction() {
        JSlider slider = new JSlider(0, 100, 50);
        slider.setMajorTickSpacing(25);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.add(slider, BorderLayout.CENTER);
        sliderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    }

}
