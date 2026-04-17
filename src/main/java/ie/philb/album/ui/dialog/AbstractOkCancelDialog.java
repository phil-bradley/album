/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.dialog;

import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 *
 * @author philb
 */
public abstract class AbstractOkCancelDialog<T> extends AbstractDialog {

    protected boolean okPressed = false;
    private final JButton btnOk = new JButton("Ok");
    private final JButton btnCancel = new JButton("Cancel");
    private final JLabel lblFeedback = new JLabel(" ");

    public AbstractOkCancelDialog() {
        super();
        initComponents();
    }

    public abstract T getResult();

    public abstract DialogValidationState getValidationState();

    protected void ok() {

        DialogValidationState validation = getValidationState();

        if (validation.isValid()) {
            this.okPressed = true;
            dispose();
        } else {
            showValidationMessage(validation.message());

            JComponent invalidField = validation.invalidField();

            if (invalidField != null) {
                invalidField.requestFocus();
            }

            if (invalidField instanceof JTextField) {
                ((JTextField) invalidField).selectAll();
            }
        }

    }

    protected void cancel() {
        this.okPressed = false;
        dispose();
    }
    
    public boolean isOkPressed() {
        return okPressed;
    }

    private void initComponents() {

        btnOk.addActionListener((ActionEvent e) -> {
            ok();
        });

        btnCancel.addActionListener((ActionEvent e) -> {
            cancel();
        });

        bindEscapeAndEnterKeys();
        layoutComponents();
    }

    private void layoutComponents() {

        GridBagCellConstraints gbc = new GridBagCellConstraints().weight(1, 0);
        gbc.fillHorizontal();

        add(getView(), gbc);

        gbc.incy().fillBoth().weight(1);
        add(new AppPanel(), gbc);

        gbc.incy()
                .fillHorizontal()
                .anchorSouthEast()
                .inset(2)
                .weight(1, 0);

        add(lblFeedback, gbc);

        gbc.incy();
        JPanel buttonsPanel = getButtonsPanel();
        add(buttonsPanel, gbc);
    }

    private void showValidationMessage(String message) {
        lblFeedback.setBackground(Color.WHITE);
        lblFeedback.setOpaque(true);
        lblFeedback.setText(message);

        new Timer(2000, (ActionEvent e) -> {
            hideValidationMessage();
        }).start();
    }

    private void hideValidationMessage() {
        lblFeedback.setOpaque(false);
        lblFeedback.setText(" ");
    }

    private JPanel getButtonsPanel() {
        AppPanel buttonsPanel = new AppPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(btnCancel);
        buttonsPanel.add(Box.createRigidArea(new Dimension(2, 0))); // spacing
        buttonsPanel.add(btnOk);

        return buttonsPanel;
    }

    private void bindEscapeAndEnterKeys() {

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel");

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ok");

        getRootPane().getActionMap()
                .put("cancel", new CancelAction());

        getRootPane().getActionMap()
                .put("ok", new OkAction());
    }

    private class OkAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            ok();
        }
    }

    private class CancelAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            cancel();
        }
    }
}
