/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageModel;
import ie.philb.album.model.PageSize;

/**
 *
 * @author philb
 */
public class NewPageCommand extends AbstractCommand {

    @Override
    public void execute() {

        AlbumModel albumModel = AppContext.INSTANCE.getAlbumModel();
        PageSize pageSize = albumModel.getPageSize();

        PageModel page = new PageModel(PageGeometry.rectangle(3, 2), pageSize);
        albumModel.addPage(page);

        AppContext.INSTANCE.albumUpdated();
    }

}
