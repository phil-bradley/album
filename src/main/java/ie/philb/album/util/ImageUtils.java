/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philip.Bradley
 */
public class ImageUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ImageUtils.class);

    private static BufferedImage PLACEHOLDER_IMAGE = null;
    private static BufferedImage EMPTY_IMAGE = null;

    public static BufferedImage getEmptyImage() {

        if (EMPTY_IMAGE == null) {
            EMPTY_IMAGE = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        }

        return EMPTY_IMAGE;
    }

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

    public static Point getCenteredCoordinates(BufferedImage image, Dimension cellSize) {

        // Centre image if it's less tall or less wide than the available space
        if (image == null) {
            return new Point(0, 0);
        }

        int imageHeight = image.getHeight();
        int imageWidth = image.getWidth();

        int boxWidth = 0;
        int boxHeight = 0;
        double cellAspectRatio = getAspectRatio(cellSize);
        double imageAspectRatio = getAspectRatio(new Dimension(imageWidth, imageHeight));

        if (imageWidth < cellSize.width && imageHeight < cellSize.height) {
            boxWidth = imageWidth;
            boxHeight = imageHeight;
        } else {

            if (imageAspectRatio > 1) {
                boxWidth = cellSize.width;
                boxHeight = getHeightFromWidth(boxWidth, imageAspectRatio);
            } else {
                boxHeight = cellSize.height;
                boxWidth = getWidthFromHeight(boxHeight, imageAspectRatio);
            }
        }

        int x = (cellSize.width - boxWidth) / 2;
        int y = (cellSize.height - boxHeight) / 2;

        return new Point(x, y);
    }

    public static BufferedImage getPlaceholderImage() {

        if (PLACEHOLDER_IMAGE == null) {
            try {
                PLACEHOLDER_IMAGE = ImageIO.read(ImageUtils.class.getResourceAsStream("/ie/philb/album/placeholder.png"));
            } catch (IOException ex) {
            }
        }

        return PLACEHOLDER_IMAGE;
    }

    /*  width/height = aspect
     *  width = aspect * height
     */
    public static int getWidthFromHeight(int height, double aspectRatio) {
        return (int) (height * aspectRatio);
    }

    /*  width/height = aspect
     *  height = width / aspect
     */
    public static int getHeightFromWidth(int width, double aspectRatio) {
        return (int) (width / aspectRatio);
    }

    public static double getAspectRatio(Dimension d) {
        double width = d.getWidth();
        double height = d.getHeight();
        return width / height;
    }

    public static Dimension getBoundingBoxWithAspectRatio(ImageIcon image, double aspectRatio) {

        if (image == null) {
            return new Dimension(0, 0);
        }

        int imageWidth = image.getIconWidth();
        int imageHeight = image.getIconHeight();

        int boxWidth = 0;
        int boxHeight = 0;

        if (imageWidth > imageHeight) {
            boxWidth = imageWidth;
            boxHeight = getHeightFromWidth(boxWidth, aspectRatio);
        } else {
            boxHeight = imageHeight;
            boxWidth = getWidthFromHeight(boxHeight, aspectRatio);
        }

        return new Dimension(boxWidth, boxHeight);
    }

    public static BufferedImage getSubimage(BufferedImage image, Point offset, Dimension cropSize) {

        Point subImageOffset = new Point(-offset.x, -offset.y);

        int x = Math.max(0, subImageOffset.x);
        int y = Math.max(0, subImageOffset.y);

        int width = cropSize.width - offset.x;

        if (width > image.getWidth() - x) {
            width = image.getWidth() - x;
        }

        int height = cropSize.height - offset.y;

        if (height > image.getHeight() - y) {
            height = image.getHeight() - y;
        }

        if (width <= 0 || height <= 0) {
            return getEmptyImage();
        }

        LOG.info("Getting subimage at {}x{} with size {}x{}", x, y, width, height);
        return image.getSubimage(x, y, width, height);
    }
}
