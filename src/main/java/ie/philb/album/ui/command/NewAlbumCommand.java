/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.ui.action.NewAlbumAction;
import ie.philb.album.ui.common.Dialogs;
import ie.philb.album.ui.dialog.NewAlbumDialog;
import ie.philb.album.ui.dialog.NewAlbumParams;

/**
 *
 * @author philb
 */
public class NewAlbumCommand extends AbstractCommand {

    public NewAlbumCommand() {
    }

    @Override
    public void execute() {

        NewAlbumDialog dlg = new NewAlbumDialog();
        dlg.setVisible(true);

        if (!dlg.isOkPressed()) {
            return;
        }

        NewAlbumParams params = dlg.getValidationState().result();

        AlbumModel albumModel = AppContext.INSTANCE.getAlbumModel();

        if (albumModel != null && albumModel.hasUnSavedChanges()) {

            boolean ok = Dialogs.confirm("Are you sure?");

            if (!ok) {
                return;
            }
        }

        new NewAlbumAction(params).execute();
    }
}
