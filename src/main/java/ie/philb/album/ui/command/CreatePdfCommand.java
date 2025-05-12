/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.ui.action.Callback;
import ie.philb.album.ui.action.CreatePdfAction;
import ie.philb.album.ui.common.Dialogs;

/**
 *
 * @author Philip.Bradley
 */
public class CreatePdfCommand extends AbstractCommand {

    @Override
    public void execute() {
        AlbumModel albumModel = AppContext.INSTANCE.getAlbumModel();

        new CreatePdfAction(albumModel).execute(
                new Callback<Void>() {

            @Override
            public void onSuccess(Void result) {
                Dialogs.showInfoMessage("Done!");
            }

            @Override
            public void onFailure(Exception ex) {
                Dialogs.showErrorMessage("Error", "Failed to save PDF: " + ex.getMessage());
            }
        }
        );
    }
}
