/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class AlbumModelTest {

    private static final int MARGIN=5;
    private static final int GUTTER=25;
    
    @Test
    void givenAlbumModel_expectPageSize() {
        AlbumModel albumModel = new AlbumModel(PageSize.US_Letter_Portrait, MARGIN, GUTTER);
        assertEquals(PageSize.US_Letter_Portrait, albumModel.getPageSize());
    }

    @Test
    void givenAlbum_whenPageAdded_expectPageSizeMatches() {
        AlbumModel albumModel = new AlbumModel(PageSize.A4_Landscape, MARGIN, GUTTER);
        assertEquals(0, albumModel.getPages().size());

        PageGeometry geometry = PageGeometry.square(2);
        albumModel.addPage(geometry);
        albumModel.addPage(geometry);
        assertEquals(2, albumModel.getPages().size());
    }
}
