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
import ie.philb.album.ui.common.PdfViewDialog;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Philip.Bradley
 */
public class CreatePdfCommand extends AbstractCommand {

    @Override
    public void execute() {
        AlbumModel albumModel = AppContext.INSTANCE.getAlbumModel();

        new CreatePdfAction(albumModel).execute(
                new Callback<File>() {

            @Override
            public void onSuccess(File result) {
                boolean ok = true;
                //boolean ok = Dialogs.confirm("Done!", "Preview the result?");
                if (ok) {
                    PdfViewDialog dlg = new PdfViewDialog();

                    try {
                        dlg.setFile(result);
                        dlg.setVisible(true);
                    } catch (IOException ex) {
                        Dialogs.showErrorMessage("Failed to load PDF: " + ex.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Exception ex) {
                Dialogs.showErrorMessage("Error", "Failed to save PDF: " + ex.getMessage());
            }
        }
        );
    }
}
