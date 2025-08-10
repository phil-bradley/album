/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.about;

import ie.philb.album.model.appinfo.LicenseInfo;
import ie.philb.album.model.appinfo.LicenseInfoHtmlRenderer;
import ie.philb.album.model.appinfo.LicenseInfoReader;
import ie.philb.album.ui.ApplicationUi;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

/**
 *
 * @author philb
 */
public class LicenseDialog extends JDialog {

    private LicensePanel licensePanel = new LicensePanel();

    public LicenseDialog() {

        super(ApplicationUi.getInstance(), "License");
        setModal(true);

        setLayout(new GridBagLayout());
        GridBagCellConstraints gbc = new GridBagCellConstraints(0, 0).weight(1).fillBoth();
        add(licensePanel, gbc);

        setPreferredSize(new Dimension(500, 400));
        setSize(getPreferredSize());

        bindEscapeKey(this);
    }

    private static void bindEscapeKey(JDialog dialog) {
        JRootPane rootPane = dialog.getRootPane();

        String escapeActionKey = "escapeAction";
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap actionMap = rootPane.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), escapeActionKey);
        actionMap.put(escapeActionKey, new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dialog.dispose();
            }
        });
    }

    class LicensePanel extends AppPanel {

        private LicenseInfo licenseInfo;
        private final JButton btnOk = new JButton("OK");
        private JEditorPane editorPane = new JEditorPane();

        public LicensePanel() {
            background(Color.WHITE);

            editorPane.setEditable(false);
            editorPane.setContentType("text/html");

            try {
                licenseInfo = new LicenseInfoReader().read();
                String html = new LicenseInfoHtmlRenderer(licenseInfo).getHtml();
                editorPane.setText(html);
            } catch (Exception ex) {
                editorPane.setText("<html><body><br/><center>License Info Not Available<br/></body></html>");
            }

            GridBagCellConstraints gbc = new GridBagCellConstraints(0, 0).weight(1).fillBoth();
            add(editorPane, gbc);

            gbc.incy().fillNone().weighty(0).anchorSouthEast();
            add(btnOk, gbc);

            //this.licenseInfo = LicenseInfoReader.read();
        }
    }
}
