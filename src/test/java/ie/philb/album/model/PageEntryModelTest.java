/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import ie.philb.album.util.ImageUtils;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class PageEntryModelTest {

    private static final String TEST_IMAGE_FILE_NAME = "test_275x183.jpg";
    private static File TEST_IMAGE_FILE = null;

    @BeforeAll
    private static void initTestImage() {
        String resourcePath = PageEntryModelTest.class.getResource("/" + TEST_IMAGE_FILE_NAME).getPath();
        TEST_IMAGE_FILE = new File(resourcePath);
    }

    @Test
    void givenPageEntryModel_expectInitialised() {
        PageCell cell = new PageCell(new Dimension(1, 2), new Point(3, 4));
        PageEntryModel pem = new PageEntryModel(cell);

        assertEquals(cell, pem.getCell());
        assertNull(pem.getImage());
        assertNull(pem.getImageFile());
        assertFalse(pem.isCentered());
        assertEquals(new Point(0, 0), pem.getImageViewOffset());
        assertEquals(1, pem.getZoomFactor());
    }

    @Test
    void givenPageEntryModel_whenSetImageFile_expectImageCreated() {
        PageCell cell = new PageCell(new Dimension(1, 2), new Point(3, 4));
        PageEntryModel pem = new PageEntryModel(cell);
        pem.setImageFile(TEST_IMAGE_FILE);

        assertNotNull(pem.getImage());
        assertNotNull(pem.getImageFile());

        File pemFile = pem.getImageFile();
        String pemFileName = pemFile.getName();
        assertTrue(pemFileName.endsWith(TEST_IMAGE_FILE_NAME));

        BufferedImage pemImage = pem.getImage();
        assertEquals(275, pemImage.getWidth());
        assertEquals(183, pemImage.getHeight());
    }

    @Test
    void givenPageEntryModel_whenZoomIn_expectZoomFactorIncreased() {
        PageCell cell = new PageCell(new Dimension(1, 2), new Point(3, 4));
        PageEntryModel pem = new PageEntryModel(cell);
        pem.setImageFile(TEST_IMAGE_FILE);

        pem.setZoomFactor(1);
        assertEquals(1, pem.getZoomFactor());

        pem.zoomIn();
        assertEquals(1.1, pem.getZoomFactor());

        pem.zoomIn();
        assertEquals(1.1 * 1.1, pem.getZoomFactor());
    }

    @Test
    void givenPageEntryModel_whenZoomOut_expectZoomFactorDecreased() {
        PageCell cell = new PageCell(new Dimension(1, 2), new Point(3, 4));
        PageEntryModel pem = new PageEntryModel(cell);
        pem.setImageFile(TEST_IMAGE_FILE);

        double zoom1 = 10;
        double zoom2 = zoom1 / (double) 1.1;
        double zoom3 = zoom2 / (double) 1.1;

        pem.setZoomFactor(zoom1);
        assertEquals(zoom1, pem.getZoomFactor());

        pem.zoomOut();
        assertEquals(zoom2, pem.getZoomFactor());

        pem.zoomOut();
        assertEquals(zoom3, pem.getZoomFactor());
    }

    @Test
    void givenPageEntryModel_whenZoomReset_expectZoomFactorIsOne() {
        PageCell cell = new PageCell(new Dimension(1, 2), new Point(3, 4));
        PageEntryModel pem = new PageEntryModel(cell);
        pem.setImageFile(TEST_IMAGE_FILE);

        pem.setZoomFactor(10);
        assertEquals(10, pem.getZoomFactor());

        pem.resetZoom();
        assertEquals(1, pem.getZoomFactor());
    }

    @Test
    void givenPageEntryModel_whenOffsetReset_expectOffsetIsZero() {
        PageCell cell = new PageCell(new Dimension(1, 2), new Point(3, 4));
        PageEntryModel pem = new PageEntryModel(cell);
        pem.setImageFile(TEST_IMAGE_FILE);

        Point viewOffset = new Point(10, 20);
        pem.setImageViewOffset(viewOffset);
        assertEquals(viewOffset, pem.getImageViewOffset());

        pem.resetOffset();
        assertEquals(new Point(0, 0), pem.getImageViewOffset());
    }

    @Test
    void givenPageEntryModel_andImageSet_andZoomIsOne_expectViewImageNotExceedsCellBound() {
        PageCell cell = new PageCell(new Dimension(1, 1), new Point(0, 0));
        PageEntryModel pem = new PageEntryModel(cell);
        pem.setImageFile(TEST_IMAGE_FILE);
        pem.setZoomFactor(1);

        PageGeometry geometry = PageGeometry.square(1);
        PageModel pageModel = new PageModel(geometry, PageSize.A4_Landscape).withMargin(0);

        Dimension viewSize = PageSize.A4_Landscape.size(); // 1-1 size mapping
        PageGeometryMapper geometryMapper = new PageGeometryMapper(pageModel, viewSize);

        BufferedImage viewImage = pem.getViewImage(viewSize, geometryMapper);

        Dimension imageSize = ImageUtils.getImageSize(viewImage);
        assertEquals(viewSize.width, imageSize.width);

        int imageHeight = ImageUtils.getHeightFromWidth(viewImage.getWidth(), ImageUtils.getAspectRatio(imageSize));
        assertEquals(imageHeight, imageSize.height);
    }

    @Test
    void givenPageEntryModel_andImageSet_andZoomIsTen_expectViewImageNotExceedsCellBound() {
        PageCell cell = new PageCell(new Dimension(1, 1), new Point(0, 0));
        PageEntryModel pem = new PageEntryModel(cell);
        pem.setImageFile(TEST_IMAGE_FILE);
        pem.setZoomFactor(10);

        PageGeometry geometry = PageGeometry.square(1);
        PageModel pageModel = new PageModel(geometry, PageSize.A4_Landscape).withMargin(0);

        Dimension viewSize = PageSize.A4_Landscape.size(); // 1-1 size mapping
        PageGeometryMapper geometryMapper = new PageGeometryMapper(pageModel, viewSize);

        BufferedImage viewImage = pem.getViewImage(viewSize, geometryMapper);

        Dimension imageSize = ImageUtils.getImageSize(viewImage);
        assertEquals(viewSize.width, imageSize.width);

        int imageHeight = ImageUtils.getHeightFromWidth(viewImage.getWidth(), ImageUtils.getAspectRatio(imageSize));
        assertEquals(imageHeight, imageSize.height);
    }

    @Test
    void givenPageEntryModel_andImageSet_andOffsetPositive_expectViewReduced() {
        PageCell cell = new PageCell(new Dimension(1, 1), new Point(0, 0));
        PageEntryModel pem = new PageEntryModel(cell);
        pem.setImageFile(TEST_IMAGE_FILE);
        pem.setZoomFactor(1);

        Point offset = new Point(100, 0);
        pem.setImageViewOffset(offset);

        PageGeometry geometry = PageGeometry.square(1);
        PageModel pageModel = new PageModel(geometry, PageSize.A4_Landscape).withMargin(0);

        Dimension viewSize = PageSize.A4_Landscape.size(); // 1-1 size mapping
        PageGeometryMapper geometryMapper = new PageGeometryMapper(pageModel, viewSize);

        BufferedImage viewImage = pem.getViewImage(viewSize, geometryMapper);

        Dimension imageSize = ImageUtils.getImageSize(viewImage);
        assertEquals(viewSize.width - offset.x, imageSize.width);
    }

    @Test
    void givenPageEntryModel_andImageSet_andOffsetNegative_expectViewReduced() {
        PageCell cell = new PageCell(new Dimension(1, 1), new Point(0, 0));
        PageEntryModel pem = new PageEntryModel(cell);
        pem.setImageFile(TEST_IMAGE_FILE);
        pem.setZoomFactor(1);

        Point offset = new Point(-100, 0);
        pem.setImageViewOffset(offset);

        PageGeometry geometry = PageGeometry.square(1);
        PageModel pageModel = new PageModel(geometry, PageSize.A4_Landscape).withMargin(0);

        Dimension viewSize = PageSize.A4_Landscape.size(); // 1-1 size mapping
        PageGeometryMapper geometryMapper = new PageGeometryMapper(pageModel, viewSize);

        BufferedImage viewImage = pem.getViewImage(viewSize, geometryMapper);

        Dimension imageSize = ImageUtils.getImageSize(viewImage);
        assertEquals(viewSize.width - Math.abs(offset.x), imageSize.width);
    }

    @Test
    void givenPageEntryModel_andImageSet_andZoomIsOne_expectScaledImageNotExceedsCellBound() {
        PageCell cell = new PageCell(new Dimension(1, 1), new Point(0, 0));
        PageEntryModel pem = new PageEntryModel(cell);
        pem.setImageFile(TEST_IMAGE_FILE);
        pem.setZoomFactor(1);

        PageGeometry geometry = PageGeometry.square(1);
        PageModel pageModel = new PageModel(geometry, PageSize.A4_Landscape).withMargin(0);

        Dimension viewSize = PageSize.A4_Landscape.size(); // 1-1 size mapping
        PageGeometryMapper geometryMapper = new PageGeometryMapper(pageModel, viewSize);

        BufferedImage scaled = pem.getScaledImage(viewSize, geometryMapper);

        Dimension imageSize = ImageUtils.getImageSize(scaled);
        assertEquals(viewSize.width, imageSize.width);

        int imageHeight = ImageUtils.getHeightFromWidth(scaled.getWidth(), ImageUtils.getAspectRatio(imageSize));
        assertEquals(imageHeight, imageSize.height);
    }

    @Test
    void givenPageEntryModel_andImageSet_andZoomIsTen_expectScaledImageExceedsCellBound() {
        PageCell cell = new PageCell(new Dimension(1, 1), new Point(0, 0));
        PageEntryModel pem = new PageEntryModel(cell);
        pem.setImageFile(TEST_IMAGE_FILE);
        pem.setZoomFactor(10);

        PageGeometry geometry = PageGeometry.square(1);
        PageModel pageModel = new PageModel(geometry, PageSize.A4_Landscape).withMargin(0);

        Dimension viewSize = PageSize.A4_Landscape.size(); // 1-1 size mapping
        PageGeometryMapper geometryMapper = new PageGeometryMapper(pageModel, viewSize);

        BufferedImage scaled = pem.getScaledImage(viewSize, geometryMapper);

        Dimension imageSize = ImageUtils.getImageSize(scaled);
        assertEquals(viewSize.width * 10, imageSize.width);

        int imageHeight = ImageUtils.getHeightFromWidth(scaled.getWidth(), ImageUtils.getAspectRatio(imageSize));
        assertEquals(imageHeight, imageSize.height);
    }

    @Test
    void givenPageEntryModel_andImageSet_andZoomToCover_expectScaledImagesExceedsCellBound() {
        PageCell cell = new PageCell(new Dimension(1, 1), new Point(0, 0));
        PageEntryModel pem = new PageEntryModel(cell);
        pem.setImageFile(TEST_IMAGE_FILE);
        pem.setZoomFactor(1);

        PageGeometry geometry = PageGeometry.square(1);
        PageModel pageModel = new PageModel(geometry, PageSize.A4_Landscape).withMargin(0);

        Dimension viewSize = PageSize.A4_Landscape.size(); // 1-1 size mapping
        PageGeometryMapper geometryMapper = new PageGeometryMapper(pageModel, viewSize);
        pem.zoomToCoverFit(viewSize);

        BufferedImage scaled = pem.getScaledImage(viewSize, geometryMapper);

        Dimension imageSize = ImageUtils.getImageSize(scaled);
        assertEquals(viewSize.height, imageSize.height);

        assertTrue(imageSize.width > viewSize.width);
    }

    @Test
    void givenPageEntryModel_andImageSet_andZoomToCover_expectViewImagesMatchesCellBound() {
        PageCell cell = new PageCell(new Dimension(1, 1), new Point(0, 0));
        PageEntryModel pem = new PageEntryModel(cell);
        pem.setImageFile(TEST_IMAGE_FILE);
        pem.setZoomFactor(1);

        PageGeometry geometry = PageGeometry.square(1);
        PageModel pageModel = new PageModel(geometry, PageSize.A4_Landscape).withMargin(0);

        Dimension viewSize = PageSize.A4_Landscape.size(); // 1-1 size mapping
        PageGeometryMapper geometryMapper = new PageGeometryMapper(pageModel, viewSize);
        pem.zoomToCoverFit(viewSize);

        BufferedImage viewImage = pem.getViewImage(viewSize, geometryMapper);

        Dimension imageSize = ImageUtils.getImageSize(viewImage);
        assertEquals(viewSize, imageSize);
    }

    @Test
    void givenPageEntryModel_andImageNotSet_expectPlaceholderImage() {
        PageCell cell = new PageCell(new Dimension(1, 1), new Point(0, 0));
        PageEntryModel pem = new PageEntryModel(cell);

        PageGeometry geometry = PageGeometry.square(1);
        PageModel pageModel = new PageModel(geometry, PageSize.A4_Landscape).withMargin(0);

        Dimension viewSize = PageSize.A4_Landscape.size(); // 1-1 size mapping
        PageGeometryMapper geometryMapper = new PageGeometryMapper(pageModel, viewSize);

        BufferedImage viewImage = pem.getViewImage(viewSize, geometryMapper);
        Dimension viewImageSize = ImageUtils.getImageSize(viewImage);

        Dimension placeHolderImageSize = ImageUtils.getImageSize(ImageUtils.getPlaceholderImage());
        assertEquals(placeHolderImageSize, viewImageSize);
    }

    @Test
    void givenPageEntryModel_andImageNotSet_andCellIsSmall_expectPlaceholderImage() {
        PageCell cell = new PageCell(new Dimension(1, 1), new Point(0, 0));
        PageEntryModel pem = new PageEntryModel(cell);

        PageGeometry geometry = PageGeometry.square(1);
        PageModel pageModel = new PageModel(geometry, PageSize.A4_Landscape).withMargin(0);

        Dimension viewSize = new Dimension(5, 5); // Too small for placeholder
        PageGeometryMapper geometryMapper = new PageGeometryMapper(pageModel, viewSize);

        BufferedImage viewImage = pem.getViewImage(viewSize, geometryMapper);
        Dimension viewImageSize = ImageUtils.getImageSize(viewImage);

        Dimension placeHolderImageSize = new Dimension(0, 0);
        assertEquals(placeHolderImageSize, viewImageSize);
    }
}
