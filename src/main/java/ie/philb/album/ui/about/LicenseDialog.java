/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.about;

import ie.philb.album.AppContext;
import ie.philb.album.model.appinfo.LicenseInfoHtmlRenderer;
import ie.philb.album.model.appinfo.LicenseReader;
import ie.philb.album.model.appinfo.LicenseSummary;
import ie.philb.album.ui.ApplicationUi;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author philb
 */
public class LicenseDialog extends JDialog {

    private final LicensePanel licensePanel = new LicensePanel();

    public LicenseDialog(AppContext appContext) {

        super(appContext.ui(), "License");
        setModal(true);
        setBackground(Color.white);

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

        private LicenseSummary licenseSummary;
        private final JButton btnOk = new JButton("OK");
        private JEditorPane editorPane = new JEditorPane();

        public LicensePanel() {
            background(Color.WHITE);

            editorPane.setEditable(false);
            editorPane.setContentType("text/html");
            editorPane.setBackground(Color.white);

            try {
                licenseSummary = new LicenseReader().read();
                String html = new LicenseInfoHtmlRenderer(licenseSummary).getHtml();
                editorPane.setText(html);
            } catch (Exception ex) {
                editorPane.setText("<html><body><br/><center>License Info Not Available<br/>" + ex.getMessage() + "</body></html>");
            }

            GridBagCellConstraints gbc = new GridBagCellConstraints(0, 0).weight(1).fillBoth();
            JScrollPane scrollPane = new JScrollPane(editorPane);
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

            add(scrollPane, gbc);

            gbc.incy().fillNone().weighty(0).anchorSouthEast();
            add(btnOk, gbc);

            btnOk.addActionListener((ActionEvent ae) -> {
                LicenseDialog.this.dispose();
            });

            scrollPane.getVerticalScrollBar().setValue(0);
        }
    }
}
