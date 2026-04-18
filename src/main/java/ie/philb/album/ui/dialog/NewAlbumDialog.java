/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.dialog;

import ie.philb.album.model.PageSize;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.common.fields.IntField;
import ie.philb.album.ui.common.fields.MaxLengthTextField;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;

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
    protected NewAlbumParams getResult() {

        return new NewAlbumParams(
                view.txtTitle.getText(),
                view.txtMargin.getValue(),
                view.txtGutter.getValue(),
                view.txtPages.getValue(),
                (PageSize) view.cmbPageSize.getSelectedItem());
    }

    @Override
    protected JComponent getView() {
        if (this.view == null) {
            this.view = new NewAlbumDialogView();
        }

        return this.view;
    }

    @Override
    public DialogValidationState<NewAlbumParams> getValidationState() {

        if (view.txtTitle.getText().isBlank()) {
            return DialogValidationState.error("Please provide a title", view.txtTitle);
        }

        if (view.txtGutter.getText().isBlank()) {
            return DialogValidationState.error("Please provide a gutter value", view.txtGutter);
        }

        if (view.txtMargin.getText().isBlank()) {
            return DialogValidationState.error("Please provide a margin value", view.txtMargin);
        }

        if (view.txtPages.getText().isBlank()) {
            return DialogValidationState.error("Please specify the numner of pages", view.txtPages);
        }

        return DialogValidationState.ok(getResult());
    }

    private class NewAlbumDialogView extends AppPanel {

        private final JLabel[] labels = {
            new JLabel("TItle"),
            new JLabel("Page size"),
            new JLabel("Default Gutter (max 200)"),
            new JLabel("Default Margin (max 200)"),
            new JLabel("Pages (max 100)")
        };

        private final MaxLengthTextField txtTitle = new MaxLengthTextField(50);
        private final JComboBox<PageSize> cmbPageSize = new JComboBox();
        private final IntField txtGutter = new IntField(0, 200);
        private final IntField txtMargin = new IntField(0, 200);
        private final IntField txtPages = new IntField(1, 100);

        private final JComponent[] fields = {
            txtTitle,
            cmbPageSize,
            txtGutter,
            txtMargin,
            txtPages
        };

        public NewAlbumDialogView() {

            cmbPageSize.setModel(getPageSizeSelectionModel());

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

    private ComboBoxModel<PageSize> getPageSizeSelectionModel() {

        DefaultComboBoxModel<PageSize> model = new DefaultComboBoxModel<>();

        for (PageSize pageSize : PageSize.values()) {
            model.addElement(pageSize);
        }

        return model;
    }

    public static void main(String[] args) {
        NewAlbumDialog dlg = new NewAlbumDialog();
        dlg.setLocation(500, 500);
        dlg.setVisible(true);
    }
}
