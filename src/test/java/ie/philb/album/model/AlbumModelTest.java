/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class AlbumModelTest {

    private static final int MARGIN = 5;
    private static final int GUTTER = 25;

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
        albumModel.addPage(0, geometry);
        albumModel.addPage(1, geometry);
        assertEquals(2, albumModel.getPages().size());
    }

    @Test
    void givenAlbum_whenPageDeleted_expectPageSizeMatches() {
        AlbumModel albumModel = new AlbumModel(PageSize.A4_Landscape, MARGIN, GUTTER);
        assertEquals(0, albumModel.getPages().size());

        PageGeometry geometry = PageGeometry.square(2);
        albumModel.addPage(0, geometry);
        albumModel.addPage(1, geometry);
        albumModel.addPage(2, geometry);

        assertEquals(3, albumModel.getPages().size());

        albumModel.deletePage(0);
        assertEquals(2, albumModel.getPages().size());
    }

    @Test
    void givenAlbum_whenPageDeleted_expectPagesRenumbered() {
        AlbumModel albumModel = new AlbumModel(PageSize.A4_Landscape, MARGIN, GUTTER);
        assertEquals(0, albumModel.getPages().size());

        PageGeometry geometry = PageGeometry.square(2);

        int numPages = 10;
        for (int i = 0; i < numPages; i++) {
            albumModel.addPage(i, geometry);
        }

        assertEquals(numPages, albumModel.getPages().size());

        // Delete some pages, check pageIds are contiguous
        albumModel.deletePage(2);
        albumModel.deletePage(7);

        assertEquals(8, albumModel.getPages().size());

        numPages = 8;
        List<PageModel> pages = albumModel.getPages();

        for (int i = 0; i < numPages; i++) {
            PageModel pageModel = pages.get(i);
            assertEquals(i, pageModel.getPageId());
        }
    }
}
