/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.io;

import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageEntryType;
import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageModel;
import ie.philb.album.model.PageSize;
import ie.philb.album.ui.common.textcontrol.TextControlModel;
import ie.philb.album.util.TestUtils;
import java.awt.Color;
import java.awt.Point;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class AlbumMapperTest {

    private static final int DEFAULT_MARGIN = 20;
    private static final int DEFAULT_GUTTER = 34;

    @Test
    void mapPageSizeAndMargins() {

        AlbumModel albumModel = new AlbumModel(PageSize.US_Letter_Landscape, DEFAULT_MARGIN, DEFAULT_GUTTER);
        AlbumData albumData = new AlbumDataMapper().map(albumModel);
        assertNotNull(albumData);
        assertEquals(PageSize.US_Letter_Landscape, albumData.getPageSize());
    }

    @Test
    void mapGeometry() {

        AlbumModel albumModel = new AlbumModel(PageSize.US_Letter_Landscape, DEFAULT_MARGIN, DEFAULT_GUTTER);
        albumModel.addPage(PageGeometry.rectangle(3, 2));
        albumModel.addPage(PageGeometry.square(4));

        AlbumData albumData = new AlbumDataMapper().map(albumModel);
        assertEquals(2, albumData.getPages().size());

        PageData page0 = albumData.getPages().get(0);
        assertEquals(6, page0.cells().size());
        assertEquals(PageGeometry.rectangle(3, 2), page0.pageGeometry());

        PageData page1 = albumData.getPages().get(1);
        assertEquals(16, page1.cells().size());
        assertEquals(PageGeometry.square(4), page1.pageGeometry());

    }

    @Test
    void mapTextProperties() {

        AlbumModel albumModel = new AlbumModel(PageSize.US_Letter_Landscape, DEFAULT_MARGIN, DEFAULT_GUTTER);
        albumModel.addPage(PageGeometry.rectangle(3, 2));

        PageModel page = albumModel.getPages().get(0);
        PageEntryModel textPem = page.getPageEntries().get(1);
        textPem.setPageEntryType(PageEntryType.Text);

        TextControlModel txt = textPem.getTextControlModel();
        txt.setText("This is text");
        txt.setBold(true);
        txt.setItalic(true);
        txt.setUnderline(true);
        txt.setFontSize(36);
        txt.setFontFamily("Arial");
        txt.setFontColor(Color.MAGENTA);

        AlbumData albumData = new AlbumDataMapper().map(albumModel);
        PageData pageData = albumData.getPages().get(0);

        CellData cellData = pageData.cells().get(1);
        assertEquals(PageEntryType.Text, cellData.pageEntryType());
        assertEquals("This is text", cellData.text());
        assertEquals(36, cellData.fontSize());
        assertEquals("Arial", cellData.fontFamily());
        assertEquals("#ff00ff", cellData.fontColor());
        assertTrue(cellData.bold());
        assertTrue(cellData.italic());
        assertTrue(cellData.underline());
    }

    @Test
    void mapImageProperties() {

        AlbumModel albumModel = new AlbumModel(PageSize.US_Letter_Landscape, DEFAULT_MARGIN, DEFAULT_GUTTER);
        albumModel.addPage(PageGeometry.rectangle(3, 2));

        PageModel page = albumModel.getPages().get(0);
        PageEntryModel pem = page.getPageEntries().get(1);
        pem.setPageEntryType(PageEntryType.Image);

        pem.setImageFile(TestUtils.getTestImageFile());
        pem.setBrightnessAdjustment(123);
        pem.setGrayScale(true);
        pem.setImageViewOffset(new Point(12, 13));
        pem.setCentered(true);
        pem.setZoomFactor(1.234);

        AlbumData albumData = new AlbumDataMapper().map(albumModel);
        PageData pageData = albumData.getPages().get(0);

        CellData cellData = pageData.cells().get(1);
        assertEquals(PageEntryType.Image, cellData.pageEntryType());
        assertEquals(TestUtils.getTestImageFile().getAbsolutePath(), cellData.fileName());
        assertEquals(123, cellData.brightness());
        assertEquals(1.234, cellData.zoom());
        assertTrue(cellData.isCentered());
        assertTrue(cellData.isGreyScale());
        assertEquals(12, cellData.offsetX());
        assertEquals(13, cellData.offsetY());
    }
}
