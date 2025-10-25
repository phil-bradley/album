/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.ui.action.callback.Callback;
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

    public PrintAlbumCommand(AppContext appContext) {
        super(appContext);
    }

    @Override
    public void execute() {

        AlbumModel album = getAppContext().getAlbumModel();
        File tempFile = null;

        try {
            tempFile = File.createTempFile("album-", ".pdf");
        } catch (IOException ex) {
            getAppContext().getDialogFactory().showErrorMessage("Cannot create PDF export", ex);
            return;
        }

        new CreatePdfAction(tempFile, album).execute(new Callback<File>() {
            @Override
            public void onSuccess(File result) {
                exportComplete = true;
            }

            @Override
            public void onFailure(Exception ex) {
                getAppContext().getDialogFactory().showErrorMessage("Failed to create PDF", ex);
            }
        });

        if (exportComplete) {
            new PrintPdfAction(tempFile).execute(new Callback<Void>() {
                @Override
                public void onSuccess(Void result) {
                }

                @Override
                public void onFailure(Exception ex) {
                    getAppContext().getDialogFactory().showErrorMessage("Failed to print", ex);
                }
            });
        }

    }

}
