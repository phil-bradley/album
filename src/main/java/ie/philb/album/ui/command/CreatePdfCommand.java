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
import ie.philb.album.ui.resources.Icons;
import ie.philb.album.util.StringUtils;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Philip.Bradley
 */
public class CreatePdfCommand extends AbstractCommand {

    private File file = null;
    private final AlbumExporter albumExporter;
    private Exception exportException;

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

        String exportBaseName = "";

        File albumFile = context.session().getAlbumModel().getFile();

        if (albumFile != null) {
            exportBaseName = FilenameUtils.removeExtension(albumFile.getName());
        }

        if (StringUtils.hasValue(exportBaseName)) {
            File proposedFile = new File(exportBaseName + ".pdf");
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

        ExportProgressDialog dialog = new ExportProgressDialog(context.ui());
        dialog.setLocationRelativeTo(context.ui());

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {

                new CreatePdfAction(context.session(), albumExporter, file).execute(new Callback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                    }

                    @Override
                    public void onFailure(Exception ex) {
                        exportException = ex;
                    }
                });

                return null;
            }

            @Override
            protected void done() {
                dialog.dispose();   // or dialog.setVisible(false)
            }
        }.execute();

        dialog.setVisible(true);

        if (exportException == null) {
            showPdf();
        } else {
            Dialogs.showErrorMessage(context.ui(), "Failed to load PDF: " + exportException.getMessage(), exportException);
        }
    }

    private void showPdf() {
        try {
            PdfViewDialog dlg = new PdfViewDialog(context);
            dlg.setFile(file);
            dlg.setVisible(true);
        } catch (IOException ex) {
            Dialogs.showErrorMessage(context.ui(), "Failed to display PDF: " + ex.getMessage(), ex);

        }
    }

    private class ExportProgressDialog extends JDialog {

        public ExportProgressDialog(Frame parent) {
            super(parent, "Exporting", true);
            setUndecorated(true);

            JPanel content = new JPanel(new BorderLayout());
            content.setBorder(BorderFactory.createRaisedBevelBorder());

            JLabel exportLabel = new JLabel("", SwingConstants.CENTER);
            exportLabel.setText("Creating PDF, Please wait...");
            exportLabel.setIcon(Icons.Regular.EXPORT);

            content.add(exportLabel, BorderLayout.CENTER);
            setContentPane(content);

            setSize(300, 150);
            setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        }
    }
}
