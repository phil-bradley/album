/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.Context;
import ie.philb.album.exporter.AlbumExporter;
import ie.philb.album.ui.action.CreatePdfAction;
import ie.philb.album.ui.action.callback.Callback;
import ie.philb.album.ui.common.Dialogs;
import ie.philb.album.ui.pdf.PdfViewDialog;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;

/**
 *
 * @author Philip.Bradley
 */
public class CreatePdfCommand extends AbstractCommand {

    private File file = null;
    private final AlbumExporter albumExporter;

    public CreatePdfCommand(Context context, AlbumExporter albumExporter) {
        this(context, albumExporter, null);
    }

    public CreatePdfCommand(Context context, AlbumExporter albumExporter, File file) {
        super(context);
        this.albumExporter = albumExporter;
        this.file = file;
    }

    @Override
    public void execute() {

        final JFileChooser chooser = new JFileChooser();

        String title = context.session().getAlbumModel().getTitle();

        if (title != null) {
            File proposedFile = new File(title + ".pdf");
            chooser.setSelectedFile(proposedFile);
        }

        int ret = chooser.showSaveDialog(context.ui());

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
            if (!Dialogs.confirm(context.ui(), msg)) {
                return;
            }
        }

        new CreatePdfAction(context.session(), albumExporter, file).execute(new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
                showPdf();
            }

            @Override
            public void onFailure(Exception ex) {
                Dialogs.showErrorMessage(context.ui(), "Failed to load PDF: " + ex.getMessage(), ex);
            }
        });
    }

    private void showPdf() {
        try {
            PdfViewDialog dlg = new PdfViewDialog(context);
            dlg.setFile(file);
            dlg.setVisible(true);
        } catch (IOException ex) {
            Dialogs.showErrorMessage(context.ui(), "Failed to load PDF: " + ex.getMessage(), ex);

        }
    }
}
