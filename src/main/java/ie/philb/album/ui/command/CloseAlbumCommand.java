/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.Context;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.ui.common.Dialogs;

/**
 *
 * @author philb
 */
public class CloseAlbumCommand extends AbstractCommand {

    public CloseAlbumCommand(Context context) {
        super(context);
    }

    @Override
    public void execute() {

        AlbumModel albumModel = context.session().getAlbumModel();

        if (albumModel == null) {
            return;
        }

        String msg = "Close album?";

        if (albumModel.hasUnSavedChanges()) {
            msg = "You have unsaved changes, close anyway?";
        }

        if (Dialogs.confirm(context.ui(), msg)) {
            context.session().setAlbumModel(null);
        }

    }
}
