/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageModel;
import ie.philb.album.model.PageSize;
import ie.philb.album.ui.action.NewAlbumAction;
import ie.philb.album.ui.dialog.NewAlbumParams;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
class NewPageCommandTest {

    @Test
    public void givenEmptyAlbum_whenInvokeNewPageCommand_expectAlbumHasOnePage() {

        PageSize pageSize = PageSize.A4_Portrait;
        int margin = 10;
        int gutter = 25;

        AlbumModel albumModel = new AlbumModel(pageSize, margin, gutter);
        AppContext.INSTANCE.setAlbumModel(albumModel);

        NewPageCommand cmd = new NewPageCommand();
        cmd.execute();

        assertEquals(1, albumModel.getPages().size());
    }

    @Test
    public void givenExistingAlbum_whenInvokeNewPageCommand_expectAlbumHasOneMorePage() {

        String title = "Test new album title";
        int margin = 12;
        int gutter = 27;
        int pageCount = 17;
        PageSize pageSize = PageSize.A4_Landscape;

        NewAlbumParams params = new NewAlbumParams(title, margin, gutter, pageCount, pageSize);
        new NewAlbumAction(params).execute();

        AlbumModel albumModel = AppContext.INSTANCE.getAlbumModel();

        // We expect pageCount +1 pages, 1 extra for the title page
        assertEquals(pageCount + 1, albumModel.getPages().size());

        NewPageCommand cmd = new NewPageCommand();
        cmd.execute();

        // We expect pageCount + 2 pages
        assertEquals(pageCount + 2, albumModel.getPages().size());
    }

    @Test
    public void givenExistingAlbumWithNoPageSelected_whenInvokeNewPageCommand_expectNewPageAtEndWithGeometryMatchingPenultimatePage() {

        String title = "Test new album title";
        int margin = 12;
        int gutter = 27;
        int pageCount = 17;
        PageSize pageSize = PageSize.A4_Landscape;

        NewAlbumParams params = new NewAlbumParams(title, margin, gutter, pageCount, pageSize);
        new NewAlbumAction(params).execute();

        AlbumModel albumModel = AppContext.INSTANCE.getAlbumModel();

        // We expect pageCount +1 pages, 1 extra for the title page
        assertEquals(pageCount + 1, albumModel.getPages().size());

        PageModel lastPagePriorToInsert = albumModel.getPages().get(albumModel.getPages().size() - 1);

        PageGeometry lastPagePriorToInsertGeometry = PageGeometry.rectangle(11, 9);
        lastPagePriorToInsert.setGeometry(lastPagePriorToInsertGeometry);

        NewPageCommand cmd = new NewPageCommand();
        cmd.execute();

        PageModel penultimatePage = albumModel.getPages().get(albumModel.getPages().size() - 2);
        PageModel lastPage = albumModel.getPages().get(albumModel.getPages().size() - 1);

        assertEquals(lastPagePriorToInsertGeometry, penultimatePage.getGeometry());
        assertEquals(lastPagePriorToInsertGeometry, lastPage.getGeometry());
    }

    @Test
    @Disabled
    public void givenExistingAlbumWithPageSelected_whenInvokeNewPageCommand_expectNewPageAfterSelectedPage() {
        // TODO
    }
}
