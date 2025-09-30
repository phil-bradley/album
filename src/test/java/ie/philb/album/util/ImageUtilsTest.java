/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.util;

import static ie.philb.album.util.ImageUtils.transparentToWhiteBackground;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.ImageIcon;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void givenImageIcon_expectBufferedImage() {

        BufferedImage source = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = source.createGraphics();
        g.setColor(Color.RED);
        g.fillRect(0, 0, 10, 10);
        g.dispose();

        ImageIcon icon = new ImageIcon(source);

        BufferedImage result = ImageUtils.getBufferedImage(icon);

        assertNotNull(result, "BufferedImage should not be null");
        assertEquals(10, result.getWidth());
        assertEquals(10, result.getHeight());

        // Verify at least one pixel is red (copy worked)
        int rgb = result.getRGB(5, 5);
        assertEquals(source.getRGB(5, 5), rgb);
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
        Point offset = new Point(25, 35);
        Dimension cropSize = new Dimension(200, 80);
        BufferedImage subImage = ImageUtils.getSubimage(image, offset, cropSize);
        assertEquals(cropSize.width - offset.x, subImage.getWidth());
        assertEquals(cropSize.height - offset.y, subImage.getHeight());
    }

    @Test
    void givenSizeLessThanBoundary_expectedNotSnappedMinimum() {
        Dimension size = new Dimension(100, 100);
        Dimension boundary = new Dimension(120, 120);
        assertFalse(ImageUtils.isSnappedFitMinimum(size, boundary));
    }

    @Test
    void givenSizeLessThanEqualBoundary_expectedSnappedMinimum() {
        Dimension size = new Dimension(100, 100);
        Dimension boundary = new Dimension(100, 120);
        assertTrue(ImageUtils.isSnappedFitMinimum(size, boundary));

        Dimension boundary2 = new Dimension(120, 100);
        assertTrue(ImageUtils.isSnappedFitMinimum(size, boundary2));
    }

    @Test
    void givenSizeGreaterhanBoundary_expectedNotSnappedMinimum() {
        Dimension size = new Dimension(100, 100);
        Dimension boundary = new Dimension(100, 80);
        assertFalse(ImageUtils.isSnappedFitMinimum(size, boundary));

        Dimension boundary2 = new Dimension(80, 100);
        assertFalse(ImageUtils.isSnappedFitMinimum(size, boundary2));
    }

    @Test
    void givenSizeLessThanBoundary_expectedNotSnappedMaximum() {
        Dimension size = new Dimension(100, 100);
        Dimension boundary = new Dimension(120, 120);
        assertFalse(ImageUtils.isSnappedFitMaximum(size, boundary));
    }

    @Test
    void givenSizeLessThanEqualBoundary_expectedNotSnappedMaximum() {
        Dimension size = new Dimension(100, 100);
        Dimension boundary = new Dimension(100, 120);
        assertFalse(ImageUtils.isSnappedFitMaximum(size, boundary));

        Dimension boundary2 = new Dimension(120, 100);
        assertFalse(ImageUtils.isSnappedFitMaximum(size, boundary2));
    }

    @Test
    void givenSizeGreaterThanEqualBoundary_expectedSnappedMaximum() {
        Dimension size = new Dimension(100, 120);
        Dimension boundary = new Dimension(100, 100);
        assertTrue(ImageUtils.isSnappedFitMaximum(size, boundary));

        Dimension size2 = new Dimension(120, 100);
        assertTrue(ImageUtils.isSnappedFitMaximum(size2, boundary));
    }

    @Test
    void givenSizeGreaterThanBoundary_expectedNotSnappedMaximum() {
        Dimension size = new Dimension(120, 120);
        Dimension boundary = new Dimension(100, 100);
        assertFalse(ImageUtils.isSnappedFitMaximum(size, boundary));
    }

    @Test
    void givenTransparentBackground_expectWhite() {

        int width = 20;
        int height = 25;

        BufferedImage transparent = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = transparent.createGraphics();
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(0, 0, width, height);
        g.dispose();

        BufferedImage white = transparentToWhiteBackground(transparent);
        assertNotNull(white, "BufferedImage should not be null");
        assertEquals(width, white.getWidth());
        assertEquals(height, white.getHeight());

        // The pixel should be white
        int argb = white.getRGB(2, 2);

        int red = (argb >> 16) & 0xFF;
        int green = (argb >> 8) & 0xFF;
        int blue = (argb) & 0xFF;

        assertEquals(255, red);
        assertEquals(255, green);
        assertEquals(255, blue);
    }

    @Test
    void givenNullImage_expectScaledIsNull() {
        BufferedImage source = null;
        BufferedImage scaled = ImageUtils.scaleImageToFit(source, new Dimension(50, 50));
        assertNull(scaled);
    }

    @Test
    void givenImage_andScaledToZero_expectNull() throws IOException {
        BufferedImage source = TestUtils.getTestImage();
        assertNull(ImageUtils.scaleImageToFit(source, new Dimension(0, 50)));
        assertNull(ImageUtils.scaleImageToFit(source, new Dimension(50, 0)));
    }

    @Test
    void givenImage_expectNoScaleUp() throws IOException {
        BufferedImage source = TestUtils.getTestImage();
        BufferedImage scaled = ImageUtils.scaleImageToFit(source, new Dimension(500, 500));

        assertEquals(source.getWidth(), scaled.getWidth());
        assertEquals(source.getHeight(), scaled.getHeight());
    }

    @Test
    void givenImage_expectScaleDown() throws IOException {
        BufferedImage source = TestUtils.getTestImage();
        BufferedImage scaled = ImageUtils.scaleImageToFit(source, new Dimension(100, 100));

        assertEquals(100, scaled.getWidth());
        assertTrue(scaled.getHeight() < 100);
    }

    @Test
    void givenNullImage_expectBestFitScaleIsOne() throws IOException {
        BufferedImage source = null;
        double scaleFactor = ImageUtils.getBestFitScaleFactor(source, new Dimension(100, 100));

        assertEquals(1, scaleFactor);
    }

    @Test
    void givenImage_andBoundaryIsZero_expectBestFitScaleIsZero() throws IOException {
        BufferedImage source = TestUtils.getTestImage();
        assertEquals(0, ImageUtils.getBestFitScaleFactor(source, new Dimension(0, 100)));
        assertEquals(0, ImageUtils.getBestFitScaleFactor(source, new Dimension(100, 0)));
    }

    @Test
    void givenImage_andBoundaryLarger_expectScaleUp() throws Exception {

        BufferedImage source = TestUtils.getTestImage();
        double scaleFactor = ImageUtils.getBestFitScaleFactor(source, new Dimension(500, 500));

        assertTrue(scaleFactor > 1);

        int scaledWidth = (int) (scaleFactor * source.getWidth());
        int scaledHeight = (int) (scaleFactor * source.getHeight());

        assertEquals(500, scaledWidth);
        assertTrue(scaledHeight < 500);
    }

    @Test
    void givenImage_andBoundarySmaller_expectScaleDown() throws Exception {

        BufferedImage source = TestUtils.getTestImage();
        double scaleFactor = ImageUtils.getBestFitScaleFactor(source, new Dimension(100, 100));

        assertTrue(scaleFactor < 1);

        int scaledWidth = (int) (scaleFactor * source.getWidth());
        int scaledHeight = (int) (scaleFactor * source.getHeight());

        assertEquals(100, scaledWidth);
        assertTrue(scaledHeight < 100);
    }
}
