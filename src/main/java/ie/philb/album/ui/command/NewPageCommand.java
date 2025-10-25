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
import java.util.List;

/**
 *
 * @author philb
 */
public class NewPageCommand extends AbstractCommand {

    public NewPageCommand(AppContext appContext) {
        super(appContext);
    }

    @Override
    public void execute() {

        AlbumModel albumModel = getAppContext().getAlbumModel();

        PageGeometry lastPageGeometry = PageGeometryOption.Columns_2_1.geometry();
        List<PageModel> pages = albumModel.getPages();

        if (!pages.isEmpty()) {
            PageModel lastPage = pages.get(pages.size() - 1);
            lastPageGeometry = lastPage.getGeometry();
        }

        albumModel.addPage(lastPageGeometry);
        getAppContext().albumUpdated();
    }

}
