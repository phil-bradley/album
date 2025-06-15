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
import ie.philb.album.model.PageSize;
import java.util.List;

/**
 *
 * @author philb
 */
public class NewPageCommand extends AbstractCommand {

    @Override
    public void execute() {

        AlbumModel albumModel = AppContext.INSTANCE.getAlbumModel();
        PageSize pageSize = albumModel.getPageSize();

        PageGeometry lastPageGeometry = PageGeometryOption.Columns_2_1.geometry();
        List<PageModel> pages = albumModel.getPages();

        if (!pages.isEmpty()) {
            PageModel lastPage = pages.get(pages.size() - 1);
            lastPageGeometry = lastPage.getGeometry();
        }

        PageModel page = new PageModel(lastPageGeometry, pageSize).withMargin(3);
        albumModel.addPage(page);

        AppContext.INSTANCE.albumUpdated();
    }

}
