/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ie.philb.album.ui.action;

import ie.philb.album.AppContext;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageEntryType;
import ie.philb.album.model.PageModel;
import ie.philb.album.model.PageSize;
import ie.philb.album.ui.dialog.NewAlbumParams;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class NewAlbumActionTest {

    @Test
    public void givenNewAlbumParams_whenNewAlbumInvoked_expectPageParamsApplied() throws Exception {

        AppContext.INSTANCE.setAlbumModel(null);

        String title = "";
        int margin = 12;
        int gutter = 27;
        int pageCount = 7;
        PageSize pageSize = PageSize.A4_Landscape;

        NewAlbumParams params = new NewAlbumParams(title, margin, gutter, pageCount, pageSize);
        NewAlbumAction action = new NewAlbumAction(params);
        action.doAction();

        AlbumModel albumModel = AppContext.INSTANCE.getAlbumModel();

        assertNotNull(albumModel);
        assertEquals(pageCount + 1, albumModel.getPages().size(), "Expect specified pages + title page");
        assertEquals(margin, albumModel.getDefaultMargin());
        assertEquals(gutter, albumModel.getDefaultGutter());
        assertEquals(pageSize, albumModel.getPageSize());
    }

    @Test
    public void givenNewAlbumParamsWithTitle_whenNewAlbumInvoked_expectTitlePageShowsTitle() throws Exception {

        String title = "Test new album title";
        int margin = 12;
        int gutter = 27;
        int pageCount = 7;
        PageSize pageSize = PageSize.A4_Landscape;

        NewAlbumParams params = new NewAlbumParams(title, margin, gutter, pageCount, pageSize);
        NewAlbumAction action = new NewAlbumAction(params);
        action.doAction();

        AlbumModel albumModel = AppContext.INSTANCE.getAlbumModel();

        assertNotNull(albumModel);

        PageModel titlePage = albumModel.getPages().get(0);

        // Expect title page to consist of single page entry, type TEXT
        assertEquals(1, titlePage.getPageEntries().size());

        PageEntryModel pageEntryModel = titlePage.getPageEntries().get(0);
        assertEquals(PageEntryType.Text, pageEntryModel.getPageEntryType());
        assertEquals(title, pageEntryModel.getTextControlModel().getText());
    }

}
