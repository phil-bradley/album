/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common;

import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.ImageIcon;

/**
 *
 * @author Philip.Bradley
 */
public class ImagePanel extends AppPanel {

    private ImageIcon imageIcon;

    public ImagePanel(ImageIcon icon) {
        this.imageIcon = icon;
    }

    public void setIcon(ImageIcon icon) {
        this.imageIcon = icon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imageIcon == null) {
            return;
        }

        Insets insets = getInsets();

        int availableHeight = getBounds().height - insets.top - insets.bottom;
        int availableWidth = getBounds().width - insets.left - insets.right;

        int iconWidth = imageIcon.getIconWidth();
        int iconHeight = imageIcon.getIconHeight();

        double scale = Math.max(iconWidth / (double) availableWidth, iconHeight / (double) availableHeight);

        int scaledWidth = (int) (iconWidth / scale);
        int scaledHeight = (int) (iconHeight / scale);

        int x = (availableWidth - scaledWidth) / 2 + insets.left;
        int y = 0;

        g.drawImage(imageIcon.getImage(), x, y, scaledWidth, scaledHeight, null);
    }
}
