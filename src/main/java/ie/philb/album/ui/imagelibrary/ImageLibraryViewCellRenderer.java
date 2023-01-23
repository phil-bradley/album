/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.imagelibrary;

import ie.philb.album.ui.common.Resouces;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.common.ImagePanel;
import ie.philb.album.ui.common.ImagePanelFill;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

/**
 *
 * @author Philip.Bradley
 */
public class ImageLibraryViewCellRenderer extends AppPanel implements ListCellRenderer<ImageLibraryEntry> {

    private static final Dimension PREFERRED_SIZE = new Dimension(150, 180);
    private ImagePanel imagePanel = new ImagePanel(null);
    private JLabel lblName = new JLabel();

    public ImageLibraryViewCellRenderer() {

        gridbag();
        opaque();

        GridBagCellConstraints gbc = new GridBagCellConstraints().weight(1).fillBoth().inset(8);
        add(imagePanel, gbc);

        gbc.y(1).fillHorizontal().anchorSouth().weighty(0);
        add(lblName, gbc);

        lblName.setHorizontalAlignment(SwingConstants.CENTER);
        setPreferredSize(PREFERRED_SIZE);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ImageLibraryEntry> list, ImageLibraryEntry value, int index, boolean isSelected, boolean cellHasFocus) {
        imagePanel.setIcon(value.getIcon());
        imagePanel.setFill(ImagePanelFill.Horizontal);
        lblName.setText(value.getTitle());

        if (isSelected) {
            setBackground(Resouces.COLOR_SELECTED);
            lblName.setForeground(Color.white);
        } else {
            setBackground(Color.WHITE);
            lblName.setForeground(Color.black);
        }

        return this;
    }

}
