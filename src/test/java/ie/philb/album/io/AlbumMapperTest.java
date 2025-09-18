/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.io;

import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageEntryType;
import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class AlbumMapperTest {

    @Test
    void albumModelIsMappedToAlbumData() {

        int defaultMargin = 20;
        int defaultGutter = 34;

        AlbumModel albumModel = new AlbumModel(PageSize.US_Letter_Landscape, defaultMargin, defaultGutter);
        albumModel.addPage(PageGeometry.rectangle(3, 2));
        albumModel.addPage(PageGeometry.square(4));

        albumModel.getPages().get(0).getPageEntries().get(0).setPageEntryType(PageEntryType.Text);
        albumModel.getPages().get(0).getPageEntries().get(0).getTextControlModel().setText("This is text");
        albumModel.getPages().get(0).getPageEntries().get(0).getTextControlModel().setBold(true);
        albumModel.getPages().get(0).getPageEntries().get(0).getTextControlModel().setFontSize(36);

        AlbumData albumData = new AlbumDataMapper().map(albumModel);
        assertNotNull(albumData);
        assertEquals(PageSize.US_Letter_Landscape, albumData.getPageSize());
        assertEquals(2, albumData.getPages().size());
        
        PageData page0 = albumData.getPages().get(0);
        
        
        PageData page1 = albumData.getPages().get(1);
        
    }
}
