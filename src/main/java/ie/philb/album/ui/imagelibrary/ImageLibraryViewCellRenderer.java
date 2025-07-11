/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.imagelibrary;

import ie.philb.album.metadata.ImageMetaData;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.common.ImagePanel;
import ie.philb.album.ui.common.Resources;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

/**
 *
 * @author Philip.Bradley
 */
public class ImageLibraryViewCellRenderer extends AppPanel implements ListCellRenderer<ImageLibraryEntry> {

    private static final Dimension PREFERRED_SIZE = new Dimension(120, 120);
    private final ImagePanel imagePanel = new ImagePanel(null);
    private final JLabel lblName = new JLabel();

    public ImageLibraryViewCellRenderer() {

        background(Color.WHITE);
        GridBagCellConstraints gbc = new GridBagCellConstraints().weight(1).fillBoth().insetHorizontal(8).insetVertical(2);
        imagePanel.setPreferredSize(PREFERRED_SIZE);
        imagePanel.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));
        add(imagePanel, gbc);

        gbc.y(1).fillHorizontal().anchorSouth().weighty(0).inset(1);
        lblName.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblName, gbc);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ImageLibraryEntry> list, ImageLibraryEntry value, int index, boolean isSelected, boolean cellHasFocus) {
        imagePanel.setIcon(value.getIcon());
        lblName.setText(value.getTitle());
        imagePanel.setToolTipText(getToolTip(value));

        if (isSelected) {
            setBackground(Resources.COLOR_SELECTED);
            lblName.setForeground(Color.white);
        } else {
            setBackground(Color.WHITE);
            lblName.setForeground(Color.black);
        }

        setToolTipText(getToolTip(value));

        return this;
    }

    private String getToolTip(ImageLibraryEntry entry) {

        if (entry == null) {
            return "";
        }

        ImageMetaData imageMetaData = entry.getImageMetaData();

        if (imageMetaData == null) {
            return "";
        }

        Dimension size = imageMetaData.getSize();
        Dimension resolution = imageMetaData.getResolution();

        if (size.width == 0) {
            return "Not available";
        }

        String tip = "Size " + size.width + "x" + size.height;

        if (resolution.width != 0) {
            tip += ", Resolution " + resolution.width + "x" + resolution.height;
        }

        return tip;
    }

}
