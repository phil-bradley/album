/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.ui.common.Dialogs;

/**
 *
 * @author Philip.Bradley
 */
public class ExitCommand extends AbstractCommand {

    @Override
    public void execute() {

        AlbumModel albumModel = AppContext.INSTANCE.getAlbumModel();

        String msg = "Quit album application?";

        if (albumModel != null && albumModel.hasUnSavedChanges()) {
            msg = "You have unsaved changes, quit anyway?";
        }

        if (Dialogs.confirm(msg)) {
            System.exit(0);
        }
    }
}
