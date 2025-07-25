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
import java.io.File;
import java.io.FileNotFoundException;
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
    private static BufferedImage PLACEHOLDER_IMAGE_SMALL = null;
    private static BufferedImage EMPTY_IMAGE = null;

    public static BufferedImage getEmptyImage() {

        if (EMPTY_IMAGE == null) {
            EMPTY_IMAGE = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        }

        return EMPTY_IMAGE;
    }

    public static BufferedImage readBufferedImage(File file) throws IOException {

        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        }

        return ImageIO.read(file);
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
        BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < size.width; x++) {
            for (int y = 0; y < size.height; y++) {
                image.setRGB(x, y, color.getRGB());
            }
        }

        return image;
    }

    public static Dimension getImageSize(BufferedImage image) {
        if (image == null) {
            return new Dimension(0, 0);
        }

        return new Dimension(image.getWidth(), image.getHeight());
    }

    public static Point getCenteredCoordinates(Dimension imageSize, Dimension cellSize) {

        // Centre image if it's less tall or less wide than the available space
        int imageHeight = imageSize.height;
        int imageWidth = imageSize.width;

        int x = (cellSize.width - imageWidth) / 2;
        int y = (cellSize.height - imageHeight) / 2;

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

    public static BufferedImage getPlaceholderSmallImage() {

        if (PLACEHOLDER_IMAGE_SMALL == null) {
            try {
                PLACEHOLDER_IMAGE_SMALL = ImageIO.read(ImageUtils.class.getResourceAsStream("/ie/philb/album/placeholder_sm.png"));
            } catch (IOException ex) {
            }
        }

        return PLACEHOLDER_IMAGE_SMALL;
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

    public static Dimension getBoundingBoxWithAspectRatio(Dimension imageSize, double aspectRatio) {

        int imageWidth = imageSize.width;
        int imageHeight = imageSize.height;

        int boxWidth = Math.max(imageWidth, getWidthFromHeight(imageHeight, aspectRatio));
        int boxHeight = Math.max(imageHeight, getHeightFromWidth(imageWidth, aspectRatio));

        return new Dimension(boxWidth, boxHeight);
    }

    public static BufferedImage getSubimage(BufferedImage image, Point offset, Dimension cropSize) {

        int availableWidth = cropSize.width;
        int availableHeight = cropSize.height;
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int x = 0;
        int y = 0;

        if (offset.x > 0) {
            availableWidth -= offset.x;
        } else {
            x = -offset.x;
            imageWidth -= x;
        }

        if (offset.y > 0) {
            availableHeight -= offset.y;
        } else {
            y = -offset.y;
            imageHeight -= y;
        }

        int width = Math.min(availableWidth, imageWidth);
        int height = Math.min(availableHeight, imageHeight);

        if (width <= 0 || height <= 0) {
            return getEmptyImage();
        }

        LOG.info("Getting subimage at {}x{} with size {}x{}, image size: {}x{}", x, y, width, height, image.getWidth(), image.getHeight());
        return image.getSubimage(x, y, width, height);
    }

    // One edge matches boundary, the other edge does not exceed boundary
    public static boolean isSnappedFitMinimum(Dimension size, Dimension boundary) {

        if (size.width == boundary.width && size.height <= boundary.height) {
            return true;
        }

        return size.height == boundary.height && size.width <= boundary.width;
    }

    // One edge matches boundary, the other edge >= boundary
    public static boolean isSnappedFitMaximum(Dimension size, Dimension boundary) {

        if (size.width == boundary.width && size.height >= boundary.height) {
            return true;
        }

        if (size.height == boundary.height && size.width >= boundary.width) {
            return true;
        }

        return false;
    }

    public static BufferedImage transparentToWhiteBackground(BufferedImage transparent) {
        return transparentToOpaque(transparent, Color.WHITE);
    }

    public static BufferedImage transparentToOpaque(BufferedImage source, Color backgroundColor) {

        int width = source.getWidth();
        int height = source.getHeight();

        BufferedImage opaque = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_RGB // No alpha channel, ensures opaque white background
        );

        Graphics2D g = opaque.createGraphics();
        g.setColor(backgroundColor);
        g.fillRect(0, 0, width, height);
        g.drawImage(source, 0, 0, width, height, null);
        g.dispose();

        return opaque;
    }

    public static BufferedImage scaleImageToFit(BufferedImage image, Dimension size) {

        if (image == null) {
            return null;
        }

        if (size.width == 0 || size.height == 0) {
            return null;
        }

        // Don't scale up
        if (image.getWidth() <= size.width && image.getHeight() <= size.height) {
            return image;
        }

        double scaleFactor = getBestFitScaleFactor(image, size);

        int scaledWidth = (int) (image.getWidth() * scaleFactor);
        int scaledHeight = (int) (image.getHeight() * scaleFactor);

        BufferedImage scaled = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaled.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, scaledWidth, scaledHeight);
        g.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();

        return scaled;
    }

    public static double getBestFitScaleFactor(BufferedImage image, Dimension size) {

        if (image == null) {
            return 1;
        }

        if (size.width == 0 || size.height == 0) {
            return 0;
        }

        double boundaryWidth = size.width;
        double boundaryHeight = size.height;
        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();

        double widthScale = boundaryWidth / imageWidth;
        double heightScale = boundaryHeight / imageHeight;

        double bestFitZoom = Math.min(widthScale, heightScale);
        return bestFitZoom;

    }
}
