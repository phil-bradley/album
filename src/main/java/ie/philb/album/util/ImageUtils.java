/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 *
 * @author Philip.Bradley
 */
public class ImageUtils {

    public static BufferedImage getBufferedImage(ImageIcon icon) {

        if (icon == null) {
            return null;
        }

        BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        g.drawImage(icon.getImage(), 0, 0, icon.getIconWidth(), icon.getIconHeight(), null);
        return bi;
    }

    public static BufferedImage createImage(Dimension size, Color color) {
        BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < size.width; x++) {
            for (int y = 0; y < size.height; y++) {
                image.setRGB(x, y, color.getRGB());
            }
        }

        return image;
    }
}
