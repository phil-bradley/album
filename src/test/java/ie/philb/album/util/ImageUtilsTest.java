/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
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
        File file = TestUtils.getTestImageFile();

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
        File file = TestUtils.getTestImageFile();

        BufferedImage image = ImageUtils.readBufferedImage(file);
        Dimension size = ImageUtils.getImageSize(image);
        assertEquals(275, size.width);
        assertEquals(183, size.height);
    }

    @Test
    void givenNullImage_expectGetSizeReturnsZero() throws Exception {
        Dimension size = ImageUtils.getImageSize(null);
        assertEquals(0, size.width);
        assertEquals(0, size.height);
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

    @Test
    void givenImageWidth_expectHeightScaledByAspectRatio() {
        Dimension imageSize = new Dimension(100, 100);
        Dimension boundingBox = ImageUtils.getBoundingBoxWithAspectRatio(imageSize, 2);
        assertEquals(200, boundingBox.width);
        assertEquals(100, boundingBox.height);
    }

    @Test
    void givenImageHeight_expectWidthScaledByAspectRatio() {
        Dimension imageSize = new Dimension(100, 100);
        Dimension boundingBox = ImageUtils.getBoundingBoxWithAspectRatio(imageSize, 0.5);
        assertEquals(100, boundingBox.width);
        assertEquals(200, boundingBox.height);
    }

    @Test
    void givenImageAspectLandscape_expectWidthScaledByAspectRatio() {
        Dimension imageSize = new Dimension(200, 100);
        Dimension boundingBox = ImageUtils.getBoundingBoxWithAspectRatio(imageSize, 3);
        assertEquals(300, boundingBox.width);
        assertEquals(100, boundingBox.height);
    }

    @Test
    void givenImageAspectPortrait_expectHeightScaledByAspectRatio() {
        Dimension imageSize = new Dimension(100, 200);
        Dimension boundingBox = ImageUtils.getBoundingBoxWithAspectRatio(imageSize, 3);
        assertEquals(600, boundingBox.width);
        assertEquals(200, boundingBox.height);
    }

    @Test

    void givenImageSizeEqualsCellSize_expectOffsetZero() {
        Dimension imageSize = new Dimension(100, 100);
        Dimension cellSize = new Dimension(100, 100);
        Point coords = ImageUtils.getCenteredCoordinates(imageSize, cellSize);
        assertEquals(0, coords.x);
        assertEquals(0, coords.y);
    }

    @Test
    void givenImageLandscapeSizeLessThanCellSize_expectOffset() {
        Dimension imageSize = new Dimension(100, 80);
        Dimension cellSize = new Dimension(150, 120);
        Point coords = ImageUtils.getCenteredCoordinates(imageSize, cellSize);
        assertEquals(25, coords.x);
        assertEquals(20, coords.y);
    }

    @Test
    void givenImagePortraitSizeLessThanCellSize_expectOffset() {
        Dimension imageSize = new Dimension(80, 100);
        Dimension cellSize = new Dimension(150, 120);
        Point coords = ImageUtils.getCenteredCoordinates(imageSize, cellSize);
        assertEquals(35, coords.x);
        assertEquals(10, coords.y);
    }

    @Test
    void givenLandscapeImageLargerThanCellSize_expectNegativeOffset() {
        Dimension imageSize = new Dimension(200, 100);
        Dimension cellSize = new Dimension(80, 50);
        Point coords = ImageUtils.getCenteredCoordinates(imageSize, cellSize);
        assertEquals(-60, coords.x);
        assertEquals(-25, coords.y);
    }

    @Test
    void givenPortraitImageLargerThanCellSize_expectNegativeOffset() {
        Dimension imageSize = new Dimension(100, 200);
        Dimension cellSize = new Dimension(80, 50);
        Point coords = ImageUtils.getCenteredCoordinates(imageSize, cellSize);
        assertEquals(-10, coords.x);
        assertEquals(-75, coords.y);
    }

    @Test
    void givenCropSizeLessThanImageSize_expectEqualsCropSize() throws Exception {
        File file = TestUtils.getTestImageFile();

        BufferedImage image = ImageUtils.readBufferedImage(file);
        Point offset = new Point(0, 0);
        Dimension cropSize = new Dimension(120, 80);
        BufferedImage subImage = ImageUtils.getSubimage(image, offset, cropSize);
        assertEquals(120, subImage.getWidth());
        assertEquals(80, subImage.getHeight());
    }

    @Test
    void givenCropSizeLessThanImageSize_andOffset_expectImageSizeReduced() throws Exception {
        File file = TestUtils.getTestImageFile();

        BufferedImage image = ImageUtils.readBufferedImage(file);
        Point offset = new Point(100, 0);
        Dimension cropSize = new Dimension(200, 80);
        BufferedImage subImage = ImageUtils.getSubimage(image, offset, cropSize);
        assertEquals(100, subImage.getWidth());
        assertEquals(80, subImage.getHeight());
    }
}
