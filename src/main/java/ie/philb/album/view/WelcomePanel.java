/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import java.awt.Color;
import javax.swing.JLabel;

/**
 *
 * @author philb
 */
public class WelcomePanel extends AppPanel {

    private JLabel lblMain;

    public WelcomePanel() {
        background(Color.cyan);
    }

    private void initComponents() {
        lblMain = new JLabel();
        lblMain.setText("Welcome!!");
    }

    private void layoutComponents() {

        GridBagCellConstraints gbc = new GridBagCellConstraints()
                .anchorNorth()
                .fillHorizontal()
                .weight(1);

        add(lblMain, gbc);
    }
}
