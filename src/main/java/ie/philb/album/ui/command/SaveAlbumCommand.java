/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.Context;
import ie.philb.album.ui.action.SaveAlbumAction;
import ie.philb.album.ui.action.callback.Callback;
import ie.philb.album.ui.common.Dialogs;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author philb
 */
public class SaveAlbumCommand extends AbstractCommand {

    private boolean forceFileSelection = false;
    private File saveFile = null;

    public SaveAlbumCommand(Context context) {
        this(context, false);
    }

    public SaveAlbumCommand(Context context, boolean forceFileSelection) {
        super(context);
        this.forceFileSelection = forceFileSelection;
    }

    @Override
    public void execute() {

        saveFile = context.session().getAlbumModel().getFile();

        if (saveFile == null || forceFileSelection) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Album Files", "album"));

            if (saveFile != null) {
                fileChooser.setSelectedFile(saveFile);
            }

            int ret = fileChooser.showSaveDialog(context.ui());

            if (ret == JFileChooser.APPROVE_OPTION) {
                saveFile = fileChooser.getSelectedFile();

                String saveFileName = saveFile.getAbsolutePath();

                if (!saveFileName.endsWith("album")) {
                    saveFile = new File(saveFileName + ".album");
                }

                if (saveFile.exists()) {
                    String msg = "Overwrite " + saveFile.getName() + "?";
                    if (!Dialogs.confirm(context.ui(), msg)) {
                        return;
                    }
                }
            }

            if (saveFile == null) {
                return;
            }
        }

        new SaveAlbumAction(context.session(), saveFile).execute(
                new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
                context.session().getAlbumModel().setFile(saveFile);
            }

            @Override
            public void onFailure(Exception ex) {
                Dialogs.showErrorMessage(context.ui(), "Could not save album", ex);
            }
        }
        );
    }
}
