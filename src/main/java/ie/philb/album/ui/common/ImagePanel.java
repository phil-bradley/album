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
    private ImagePanelFill fill = ImagePanelFill.Vertical;

    public ImagePanel(ImageIcon icon) {
        this.imageIcon = icon;
    }

    public void setIcon(ImageIcon icon) {
        this.imageIcon = icon;
    }

    public ImagePanelFill getFill() {
        return fill;
    }

    public void setFill(ImagePanelFill fill) {
        this.fill = fill;
    }

    private int getAvailableWidth() {
        Insets insets = getInsets();
        int availableWidth = getBounds().width - insets.left - insets.right;
        return availableWidth;
    }

    private int getAvailableHeight() {
        Insets insets = getInsets();
        int availableHeight = getBounds().height - insets.top - insets.bottom;
        return availableHeight;
    }

    private double getScale() {

        int iconWidth = imageIcon.getIconWidth();
        int iconHeight = imageIcon.getIconHeight();

        if (fill == ImagePanelFill.Vertical) {
            return Math.min(iconWidth / (double) getAvailableWidth(), iconHeight / (double) getAvailableHeight());

        }

        if (fill == ImagePanelFill.Horizontal) {
            return Math.max(iconWidth / (double) getAvailableWidth(), iconHeight / (double) getAvailableHeight());
        }

        return 1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imageIcon == null) {
            return;
        }

        int iconWidth = imageIcon.getIconWidth();
        int iconHeight = imageIcon.getIconHeight();

        double scale = getScale();

        int scaledWidth = (int) (iconWidth / scale);
        int scaledHeight = (int) (iconHeight / scale);

        int x = (getAvailableWidth() - scaledWidth) / 2 + getInsets().left;
        int y = 0;

        // We draw the image with dimensions scaledWidth x scaledHeight rather than bounds.width x bounds.height
        // since we want to preserve the aspect ratio.
        g.drawImage(imageIcon.getImage(), x, y, scaledWidth, scaledHeight, null);
    }
}
