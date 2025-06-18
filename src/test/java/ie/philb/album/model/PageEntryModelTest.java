/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

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
}
