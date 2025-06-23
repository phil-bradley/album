/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.metadata;

import ie.philb.album.util.TestUtils;
import java.awt.Dimension;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class ImageMetaDataTest {

    @Test
    void validateReadImageMetaData() throws Exception {

        File file = TestUtils.getTestImageFile();
        ImageMetaDataReader imageMetaDataReader = new ImageMetaDataReader(file);
        ImageMetaData imageMetaData = imageMetaDataReader.getMetaData();

        assertNotNull(imageMetaData);

        assertEquals(275, imageMetaData.getWidth());
        assertEquals(183, imageMetaData.getHeight());
        assertEquals(new Dimension(275, 183), imageMetaData.getSize());

        assertEquals(300, imageMetaData.getXResolution());
        assertEquals(300, imageMetaData.getYResolution());
        assertEquals(new Dimension(300, 300), imageMetaData.getResolution());

    }
}
