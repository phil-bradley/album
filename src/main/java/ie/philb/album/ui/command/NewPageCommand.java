/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageGeometryOption;
import ie.philb.album.model.PageModel;
import ie.philb.album.view.PageView;
import java.util.List;

/**
 *
 * @author philb
 */
public class NewPageCommand extends AbstractCommand {

    @Override
    public void execute() {

        AlbumModel albumModel = AppContext.INSTANCE.getAlbumModel();
        PageView selectedPageView = AppContext.INSTANCE.getSelectedPageView();
        List<PageModel> pages = albumModel.getPages();

        int pageId = pages.size();

        if (selectedPageView != null) {
            pageId = selectedPageView.getPageModel().getPageId() + 1;
        }

        PageGeometry lastPageGeometry = PageGeometryOption.Columns_2_1.geometry();

        if (!pages.isEmpty()) {
            PageModel lastPage = pages.get(pages.size() - 1);
            lastPageGeometry = lastPage.getGeometry();
        }

        albumModel.addPage(pageId, lastPageGeometry);
        AppContext.INSTANCE.albumUpdated();
    }

}
