/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageEntryType;
import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageModel;
import ie.philb.album.model.PageSize;
import ie.philb.album.ui.common.Dialogs;
import java.time.LocalDateTime;

/**
 *
 * @author philb
 */
public class NewAlbumCommand extends AbstractCommand {

    @Override
    public void execute() {

        AlbumModel albumModel = AppContext.INSTANCE.getAlbumModel();
        
        if (albumModel != null && albumModel.hasUnSavedChanges()) {
        
            boolean ok = Dialogs.confirm("Are you sure?");
            
            if (!ok) {
                return;
            }
        }
        
        albumModel = new AlbumModel(PageSize.A4_Landscape);

        PageGeometry geometry = PageGeometry.square(PageEntryType.Text, 1);
        PageModel titlePage = new PageModel(geometry, PageSize.A4_Landscape);
        titlePage.getPageEntries().get(0).getTextControlModel().setText("The Title!");
        albumModel.addPage(titlePage);

        PageModel page1 = new PageModel(PageGeometry.square(2), albumModel.getPageSize()).withMargin(2);
        albumModel.addPage(page1);
        
        albumModel.setLastSaveDate(LocalDateTime.now());

//        PageModel page2 = new PageModel(PageGeometry.square(2), albumModel.getPageSize()).withMarginMillis(3);
//        albumModel.addPage(page2);
//
//        PageModel page3 = new PageModel(PageGeometry.withColumns(1, 2), albumModel.getPageSize()).withMarginMillis(3);
//        albumModel.addPage(page3);
//
//        PageModel page4 = new PageModel(PageGeometry.square(1), albumModel.getPageSize()).withMarginMillis(25);
//        albumModel.addPage(page4);
        AppContext.INSTANCE.setAlbumModel(albumModel);
    }
}
