/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author philb
 */
public class WelcomePanel extends AppPanel {

    private static final float WELCOME_FONT_SIZE = 36f;
    private static final Color WELCOME_FONT_COLOR = new Color(10, 202, 245);
    
    private JLabel lblMain;

    public WelcomePanel() {
        background(Color.white);
        initComponents();
    }

    private void initComponents() {
        
        lblMain = new JLabel();
        lblMain.setHorizontalAlignment(SwingConstants.CENTER);
        lblMain.setFont(getFont().deriveFont(WELCOME_FONT_SIZE));
        lblMain.setForeground(WELCOME_FONT_COLOR);
        lblMain.setText("Welcome!!");
        
        layoutComponents();
    }

    private void layoutComponents() {

        GridBagCellConstraints gbc = new GridBagCellConstraints()
                .anchorNorth()
                .fillHorizontal()
                .weight(1)
                .insetTop(30);

        add(lblMain, gbc);
    }

    public static void main(String[] args) {
        
        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(Color.yellow);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setPreferredSize(new Dimension(400, 250));
        frame.setSize(frame.getPreferredSize());
        
        GridBagCellConstraints gbc = new GridBagCellConstraints().fillBoth().weight(1);
        
        WelcomePanel welcomePanel = new WelcomePanel();
        frame.add(welcomePanel, gbc);
        frame.setVisible(true);
    }
}
