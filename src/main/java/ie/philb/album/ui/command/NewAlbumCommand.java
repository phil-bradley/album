/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageEntryType;
import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageModel;
import ie.philb.album.ui.common.Dialogs;
import ie.philb.album.ui.dialog.NewAlbumDialog;
import ie.philb.album.ui.dialog.NewAlbumParams;
import java.time.LocalDateTime;

/**
 *
 * @author philb
 */
public class NewAlbumCommand extends AbstractCommand {

    private final PageGeometry defaultPageGeometry = PageGeometry.square(2);

    public NewAlbumCommand() {
    }

    @Override
    public void execute() {

        NewAlbumDialog dlg = new NewAlbumDialog();
        dlg.setVisible(true);
        
        if (!dlg.isOkPressed()) {
            return;
        }
        
        NewAlbumParams params = dlg.getResult();
        
        AlbumModel albumModel = AppContext.INSTANCE.getAlbumModel();

        if (albumModel != null && albumModel.hasUnSavedChanges()) {

            boolean ok = Dialogs.confirm("Are you sure?");

            if (!ok) {
                return;
            }
        }

        albumModel = new AlbumModel(params.pageSize(), params.margin(), params.gutter());

        // The title page - single text cell
        PageGeometry geometry = PageGeometry.square(PageEntryType.Text, 1);
        albumModel.addPage(geometry, 0, 0);

        PageModel titlePage = albumModel.getPages().get(0);
        PageEntryModel titleEntry = titlePage.getPageEntries().get(0);
        titleEntry.getTextControlModel().setText(params.title());

        for (int i = 0; i < params.pages(); i++) {
            albumModel.addPage(defaultPageGeometry);
            albumModel.setLastSaveDate(LocalDateTime.now());
        }

        AppContext.INSTANCE.setAlbumModel(albumModel);
    }
}
