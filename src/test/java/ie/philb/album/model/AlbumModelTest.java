/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class AlbumModelTest {

    @Test
    void givenAlbumModel_expectPageSize() {
        AlbumModel albumModel = new AlbumModel(PageSize.US_Letter_Portrait);
        assertEquals(PageSize.US_Letter_Portrait, albumModel.getPageSize());
    }

    @Test
    void givenAlbum_whenPageAdded_expectPageSizeMatches() {
        AlbumModel albumModel = new AlbumModel(PageSize.A4_Landscape);
        assertEquals(0, albumModel.getPages().size());

        PageGeometry geometry = PageGeometry.square(2);
        albumModel.addPage(new PageModel(geometry, PageSize.A4_Landscape));
        albumModel.addPage(new PageModel(geometry, PageSize.A4_Landscape));
        assertEquals(2, albumModel.getPages().size());
    }

    @Test
    void givenAlbum_whenPageAdded_andPageSizeNotMatched_expectException() {

        AlbumModel albumModel = new AlbumModel(PageSize.A4_Landscape);

        PageGeometry geometry = PageGeometry.square(2);

        Exception thrown = assertThrows(
                RuntimeException.class,
                () -> {
                    albumModel.addPage(new PageModel(geometry, PageSize.A4_Portrait));
                }
        );

        assertTrue(thrown.getMessage().contains("Cannot create page of size"));
    }
}
