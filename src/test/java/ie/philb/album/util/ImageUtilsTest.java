/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.ImageIcon;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
class ImageUtilsTest {

    @Test
    void givenEmptyImage_expectIsNotNull_and1x1() {
        BufferedImage emptyImage = ImageUtils.getEmptyImage();
        assertNotNull(emptyImage);
        assertEquals(1, emptyImage.getWidth());
        assertEquals(1, emptyImage.getHeight());
    }

    @Test
    void givenImageFile_expectReadToBufferedImage() throws Exception {
        String resourcePath = getClass().getResource("/test_275x183.jpg").getPath();
        File file = new File(resourcePath);

        BufferedImage image = ImageUtils.readBufferedImage(file);
        assertNotNull(image);
        assertEquals(275, image.getWidth());
        assertEquals(183, image.getHeight());
    }

    @Test
    void givenImageFileNotExists_expectFileNotFoundException() throws Exception {
        File file = new File("notexists.jpg");

        Exception ex = assertThrows(FileNotFoundException.class, () -> {
            ImageUtils.readBufferedImage(file);
        });
    }

    @Test
    void givenNullIcon_expectedNullBufferedImage() {
        ImageIcon icon = null;
        BufferedImage image = ImageUtils.getBufferedImage(icon);
        assertNull(image);
    }

    @Test
    void givenCreatedImage_expectSizeMatches() {
        Dimension size = new Dimension(30, 20);
        Color color = Color.yellow;

        BufferedImage image = ImageUtils.createImage(size, color);
        assertNotNull(image);
        assertEquals(size.width, image.getWidth());
        assertEquals(size.height, image.getHeight());
    }

    @Test
    void givenImage_expectGetSizeReturnsSize() throws Exception {
        String resourcePath = getClass().getResource("/test_275x183.jpg").getPath();
        File file = new File(resourcePath);

        BufferedImage image = ImageUtils.readBufferedImage(file);
        Dimension size = ImageUtils.getImageSize(image);
        assertEquals(275, size.width);
        assertEquals(183, size.height);
    }

    @Test
    void givenPlaceholderImage_expectNotNull() {
        BufferedImage image = ImageUtils.getPlaceholderImage();
        assertNotNull(image);
    }

    @Test
    void giveWidthAndAspectRatio_expectHeight() {
        int aspectRatio = 5;
        int width = 100;
        int height = ImageUtils.getHeightFromWidth(width, aspectRatio);
        assertEquals(20, height);
    }

    @Test
    void giveHeightAndAspectRatio_expectWidth() {
        int aspectRatio = 5;
        int height = 100;
        int width = ImageUtils.getWidthFromHeight(height, aspectRatio);
        assertEquals(500, width);
    }

    @Test
    void givenSize_expectAspectRatio() {
        Dimension size = new Dimension(100, 20);
        double aspectRatio = ImageUtils.getAspectRatio(size);
        assertEquals(5, aspectRatio);
    }
}
