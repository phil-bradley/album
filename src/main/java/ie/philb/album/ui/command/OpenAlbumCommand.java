/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.Context;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.ui.action.OpenAlbumAction;
import ie.philb.album.ui.action.callback.Callback;
import ie.philb.album.ui.common.Dialogs;
import ie.philb.album.ui.resources.Icons;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author philb
 */
public class OpenAlbumCommand extends AbstractCommand {

    private Exception loadException = null;

    public OpenAlbumCommand(Context context) {
        super(context);
    }

    @Override
    public void execute() {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Album Files", "album"));

        int ret = fileChooser.showOpenDialog(context.ui());

        if (ret != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File albumFile = fileChooser.getSelectedFile();

        ProgressDialog dialog = new ProgressDialog(context.ui());
        dialog.setLocationRelativeTo(context.ui());

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {

                new OpenAlbumAction(context.session(), albumFile).execute(new Callback<AlbumModel>() {
                    @Override
                    public void onSuccess(AlbumModel result) {
                        context.session().setAlbumModel(result);
                    }

                    @Override
                    public void onFailure(Exception ex) {
                        loadException = ex;
                    }
                });

                return null;
            }

            @Override
            protected void done() {
                dialog.dispose();
            }
        }.execute();

        dialog.setVisible(true);

        if (loadException != null) {
            Dialogs.showErrorMessage(context.ui(), "Failed to read album", loadException);
        }
    }

    private class ProgressDialog extends JDialog {

        public ProgressDialog(Frame parent) {
            super(parent, "Exporting", true);
            setUndecorated(true);

            JPanel content = new JPanel(new BorderLayout());
            content.setBorder(BorderFactory.createRaisedBevelBorder());

            JLabel exportLabel = new JLabel("", SwingConstants.CENTER);
            exportLabel.setText("Loading album...");
            exportLabel.setIcon(Icons.Regular.IMPORT);

            content.add(exportLabel, BorderLayout.CENTER);
            setContentPane(content);

            setSize(300, 150);
            setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        }
    }
}
