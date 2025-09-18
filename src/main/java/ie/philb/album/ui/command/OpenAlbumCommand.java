/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.ui.ApplicationUi;
import ie.philb.album.ui.action.OpenAlbumAction;
import ie.philb.album.ui.action.callback.Callback;
import ie.philb.album.ui.common.Dialogs;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author philb
 */
public class OpenAlbumCommand extends AbstractCommand {

    @Override
    public void execute() {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Album Files", "album"));

        int ret = fileChooser.showOpenDialog(ApplicationUi.getInstance());

        if (ret != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File albumFile = fileChooser.getSelectedFile();

        new OpenAlbumAction(albumFile).execute(new Callback<AlbumModel>() {
            @Override
            public void onSuccess(AlbumModel result) {
                AppContext.INSTANCE.setAlbumModel(result);
            }

            @Override
            public void onFailure(Exception ex) {
                Dialogs.showErrorMessage("Failed to read album", ex);
            }
        });
    }

}
