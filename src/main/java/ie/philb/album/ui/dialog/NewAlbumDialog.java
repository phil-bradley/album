/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.dialog;

import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author philb
 */
public class NewAlbumDialog extends AbstractOkCancelDialog<NewAlbumParams> {

    private NewAlbumDialogView view;

    public NewAlbumDialog() {
        super();
        setPreferredSize(new Dimension(500, 300));
        setSize(getPreferredSize());
    }

    @Override
    public NewAlbumParams getResult() {
        return null;
    }

    @Override
    protected JComponent getView() {
        if (this.view == null) {
            this.view = new NewAlbumDialogView();
        }
        
        return this.view;
    }

    @Override
    public DialogValidationState getValidationState() {
        return new DialogValidationState(false, "It didn't work", view.txtGutter);
    }

    private class NewAlbumDialogView extends AppPanel {

        private final JLabel[] labels = {
            new JLabel("TItle"),
            new JLabel("Page size"),
            new JLabel("Default Gutter"),
            new JLabel("Default Margin"),
            new JLabel("Pages")
        };

        private final JTextField txtTitle = new JTextField();
        private final JComboBox cmbPageSize = new JComboBox();
        private final JTextField txtGutter = new JTextField();
        private final JTextField txtMargin = new JTextField();
        private final JTextField txtPages = new JTextField();

        private final JComponent[] fields = {
            txtTitle,
            cmbPageSize,
            txtGutter,
            txtMargin,
            txtPages
        };

        public NewAlbumDialogView() {

            GridBagCellConstraints gbc = new GridBagCellConstraints()
                    .fillHorizontal()
                    .inset(5)
                    .anchor(GridBagConstraints.LINE_END)
                    .weight(0);
            
            for (JLabel label : labels) {
                add(label, gbc);
                gbc.incy();
            }

            gbc.xy(1, 0)
                    .anchor(GridBagConstraints.LINE_START)
                    .weightx(1);

            for (JComponent field : fields) {
                add(field, gbc);
                gbc.incy();
            }                   
        }
    }

    public static void main(String[] args) {
        NewAlbumDialog dlg = new NewAlbumDialog();
        dlg.setLocation(500, 500);
        dlg.setVisible(true);
    }
}
