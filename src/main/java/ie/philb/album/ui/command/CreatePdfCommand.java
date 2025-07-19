/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.exporter.AlbumExporter;
import ie.philb.album.exporter.OpenPdfExporter;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.ui.ApplicationUi;
import ie.philb.album.ui.common.Dialogs;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Philip.Bradley
 */
public class CreatePdfCommand extends AbstractCommand {

    private File file = null;

    public CreatePdfCommand() {
    }

    public CreatePdfCommand(File file) {
        this.file = file;
    }

    @Override
    public void execute() {
        AlbumModel albumModel = AppContext.INSTANCE.getAlbumModel();

        final JFileChooser chooser = new JFileChooser();
        int ret = chooser.showSaveDialog(ApplicationUi.getInstance());

        if (file == null) {
            if (ret == JFileChooser.APPROVE_OPTION) {
                file = chooser.getSelectedFile();
            }

            if (file == null) {
                return;
            }
        }

        if (file.exists()) {
            String msg = "Overwrite file " + file.getName() + "?";
            if (!Dialogs.confirm(msg)) {
                return;
            }
        }

        AlbumExporter exporter = new OpenPdfExporter(albumModel);

        try {
            exporter.export(file);
        } catch (Exception ex) {
            Dialogs.showErrorMessage("Failed to load PDF", ex);
        }

        /*
        new CreatePdfAction(file, albumModel).execute(
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
                        Dialogs.showErrorMessage("Failed to load PDF", ex);
                    }
                }
            }

            @Override
            public void onFailure(Exception ex) {
                Dialogs.showErrorMessage("Failed to save PDF", ex);
            }
        }
        );
         */
    }
}
