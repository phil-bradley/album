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

    public OpenAlbumCommand(AppContext appContext) {
        super(appContext);
    }

    @Override
    public void execute() {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Album Files", "album"));

        int ret = fileChooser.showOpenDialog(getAppContext().ui());

        if (ret != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File albumFile = fileChooser.getSelectedFile();

        new OpenAlbumAction(albumFile).execute(new Callback<AlbumModel>() {
            @Override
            public void onSuccess(AlbumModel result) {
                getAppContext().setAlbumModel(result);
            }

            @Override
            public void onFailure(Exception ex) {
                getAppContext().getDialogFactory().showErrorMessage("Failed to read album", ex);
            }
        });
    }

}
