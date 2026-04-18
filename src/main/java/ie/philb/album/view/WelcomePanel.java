/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.ui.command.NewAlbumCommand;
import ie.philb.album.ui.command.OpenAlbumCommand;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.resources.Icons;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author philb
 */
public class WelcomePanel extends AppPanel {

    private static final float WELCOME_FONT_SIZE = 36f;
    private static final Color WELCOME_FONT_COLOR = new Color(10, 202, 245);

    private JLabel lblWelcome;
    private JButton btnNewAlbum;
    private JButton btnOpenAlbum;
    private AppPanel buttonsPanel = new AppPanel();

    public WelcomePanel() {
        background(Color.white);
        initComponents();
    }

    private void initComponents() {

        lblWelcome = new JLabel();
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setFont(getFont().deriveFont(WELCOME_FONT_SIZE));
        lblWelcome.setForeground(WELCOME_FONT_COLOR);
        lblWelcome.setText("Welcome to album");

        btnNewAlbum = new JButton( "New Album", Icons.Regular.NEW);
        btnOpenAlbum = new JButton("Open Album", Icons.Regular.OPEN);

        btnNewAlbum.addActionListener((ActionEvent e) -> {
            new NewAlbumCommand().execute();
        });

        btnOpenAlbum.addActionListener((ActionEvent e) -> {
            new OpenAlbumCommand().execute();
        });

        setBorder(
            new CompoundBorder(
                new EmptyBorder(10, 10, 10, 10),
                new LineBorder(Color.lightGray, 1, true)
            )
        );
        layoutComponents();
    }

    private void layoutComponents() {

        GridBagCellConstraints gbc = new GridBagCellConstraints()
                .anchorNorth()
                .fillHorizontal()
                .weight(1)
                .insetTop(30);

        add(lblWelcome, gbc);

        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(btnNewAlbum);
        buttonsPanel.add(Box.createRigidArea(new Dimension(10, 0))); // spacing
        buttonsPanel.add(btnOpenAlbum);
        buttonsPanel.add(Box.createHorizontalGlue());

        gbc.xy(0, 1);
        add(buttonsPanel, gbc);
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
