/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.ui.action.Callback;
import ie.philb.album.ui.action.CreatePdfAction;
import ie.philb.album.ui.action.PrintPdfAction;
import ie.philb.album.ui.common.Dialogs;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author philb
 */
public class PrintAlbumCommand extends AbstractCommand {

    private boolean exportComplete;

    @Override
    public void execute() {

        AlbumModel album = AppContext.INSTANCE.getAlbumModel();
        File tempFile = null;

        try {
            tempFile = File.createTempFile("album-", ".pdf");
        } catch (IOException ex) {
            Dialogs.showErrorMessage("Cannot create PDF export: " + ex.getMessage());
            return;
        }

        new CreatePdfAction(tempFile, album).execute(new Callback<File>() {
            @Override
            public void onSuccess(File result) {
                exportComplete = true;
            }

            @Override
            public void onFailure(Exception ex) {
                Dialogs.showErrorMessage("Failed to create PDF: " + ex.getMessage());
            }
        });

        if (exportComplete) {
            new PrintPdfAction(tempFile).execute(new Callback<Void>() {
                @Override
                public void onSuccess(Void result) {
                }

                @Override
                public void onFailure(Exception ex) {
                    Dialogs.showErrorMessage("Failed to print: " + ex.getMessage());
                }
            });
        }

    }

}
