/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageCell;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageEntryType;
import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageModel;
import ie.philb.album.model.PageSize;
import ie.philb.album.ui.common.textcontrol.TextControlModel;
import ie.philb.album.util.TestUtils;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class AlbumDataMapperTest {

    private static AlbumModel albumModel;

    @BeforeAll
    static void readAlbum() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());

        File testAlbumFile = new File(AlbumDataMapperTest.class.getResource("/test-album.json").getPath());
        AlbumData albumData = mapper.readValue(testAlbumFile, AlbumData.class);
        albumModel = new AlbumDataMapper().map(albumData);
    }

    @Test
    void albumReadFromJson() throws Exception {
        assertNotNull(albumModel);

        assertEquals(PageSize.A4_Landscape, albumModel.getPageSize());
        assertEquals(10, albumModel.getDefaultMargin());
        assertEquals(25, albumModel.getDefaultGutter());

        assertEquals(21, albumModel.getPages().size());
    }

    @Test
    void pageGeometryReadFromJson() throws Exception {

        PageModel page0 = albumModel.getPages().get(0);
        PageGeometry geometry0 = PageGeometry.square(1);
        geometry0.getCells().get(0).setPageEntryType(PageEntryType.Text);
        assertEquals(geometry0, page0.getGeometry());

        PageModel page1 = albumModel.getPages().get(1);
        PageGeometry geometry1 = PageGeometry.square(2);
        geometry1.getCells().get(1).setPageEntryType(PageEntryType.Text);
        assertEquals(geometry1, page1.getGeometry());
    }

    @Test
    void imagePropertiesReadFromJson() throws Exception {

        PageModel page1 = albumModel.getPages().get(1);
        PageEntryModel pem0 = page1.getPageEntries().get(0);

        assertEquals(PageEntryType.Image, pem0.getPageEntryType());
        assertEquals("/home/philb/Pictures/5999564640.jpg", pem0.getImageFile().getAbsolutePath());
        assertEquals(142, pem0.getBrightnessAdjustment());
        assertEquals(1.741, pem0.getZoomFactor());
        assertFalse(pem0.isCentered());
        assertFalse(pem0.isGrayScale());
        assertEquals(38, pem0.getImageViewOffset().x);
        assertEquals(151, pem0.getImageViewOffset().y);

        PageEntryModel pem3 = page1.getPageEntries().get(3);

        assertEquals(PageEntryType.Image, pem3.getPageEntryType());
        assertEquals("/home/philb/Pictures/6739254902.jpg", pem3.getImageFile().getAbsolutePath());
        assertEquals(324, pem3.getBrightnessAdjustment());
        assertEquals(1, pem3.getZoomFactor());
        assertTrue(pem3.isCentered());
        assertTrue(pem3.isGrayScale());
        assertEquals(89, pem3.getImageViewOffset().x);
        assertEquals(0, pem3.getImageViewOffset().y);
    }

    @Test
    void textPropertiesReadFromJson() throws Exception {

        PageModel page1 = albumModel.getPages().get(1);
        PageEntryModel pem1 = page1.getPageEntries().get(1);

        assertEquals(PageEntryType.Text, pem1.getPageEntryType());
        TextControlModel tcm = pem1.getTextControlModel();

        assertEquals("Text Cell", tcm.getText());
        assertEquals("NotoSerif", tcm.getFontFamily());
        assertEquals(24, tcm.getFontSize());
        assertEquals(Color.decode("#404040"), tcm.getFontColor());
        assertTrue(tcm.isBold());
        assertTrue(tcm.isItalic());
        assertTrue(tcm.isUnderline());
    }
}
