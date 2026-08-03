/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.Context;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.ui.action.CreatePdfAction;
import ie.philb.album.ui.action.PrintPdfAction;
import ie.philb.album.ui.action.callback.Callback;
import ie.philb.album.ui.common.Dialogs;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author philb
 */
public class PrintAlbumCommand extends AbstractCommand {

    private boolean exportComplete;

    public PrintAlbumCommand(Context context) {
        this(context, false);
    }

    public PrintAlbumCommand(Context context, boolean exportComplete) {
        super(context);
        this.exportComplete = exportComplete;
    }

    @Override
    public void execute() {

        AlbumModel album = context.session().getAlbumModel();
        File tempFile = null;

        try {
            tempFile = File.createTempFile("album-", ".pdf");
        } catch (IOException ex) {
            Dialogs.showErrorMessage(context.ui(), "Cannot create PDF export", ex);
            return;
        }

        new CreatePdfAction(context.session(), tempFile).execute(new Callback<File>() {
            @Override
            public void onSuccess(File result) {
                exportComplete = true;
            }

            @Override
            public void onFailure(Exception ex) {
                Dialogs.showErrorMessage(context.ui(), "Failed to create PDF", ex);
            }
        });

        if (exportComplete) {
            new PrintPdfAction(context.session(), tempFile).execute(new Callback<Void>() {
                @Override
                public void onSuccess(Void result) {
                }

                @Override
                public void onFailure(Exception ex) {
                    Dialogs.showErrorMessage(context.ui(), "Failed to print", ex);
                }
            });
        }

    }

}
