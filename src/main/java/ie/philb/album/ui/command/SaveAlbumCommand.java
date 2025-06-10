/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.ui.ApplicationUi;
import ie.philb.album.ui.action.Callback;
import ie.philb.album.ui.action.SaveAlbumAction;
import ie.philb.album.ui.common.Dialogs;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author philb
 */
public class SaveAlbumCommand extends AbstractCommand {

    private AlbumModel albumModel;
    private File saveFile;

    @Override
    public void execute() {

        this.albumModel = AppContext.INSTANCE.getAlbumModel();
        this.saveFile = albumModel.getFile();

        if (saveFile == null) {
            JFileChooser fileChooser = new JFileChooser();

            int ret = fileChooser.showSaveDialog(ApplicationUi.getInstance());

            if (ret == JFileChooser.APPROVE_OPTION) {
                saveFile = fileChooser.getSelectedFile();
            }

            if (saveFile == null) {
                return;
            }
        }

        if (albumModel.getFile() == null && saveFile.exists()) {
            String msg = "Overwrite " + saveFile.getName() + "?";
            if (!Dialogs.confirm(msg)) {
                return;
            }
        }

        new SaveAlbumAction(saveFile, albumModel).execute(
                new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
                albumModel.setFile(saveFile);
            }

            @Override
            public void onFailure(Exception ex) {
                String msg = "Could not save album:" + ex.getMessage();
                Dialogs.showErrorMessage(msg);
            }
        }
        );
    }
}
