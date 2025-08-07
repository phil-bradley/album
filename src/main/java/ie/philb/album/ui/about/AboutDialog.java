/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.about;

import ie.philb.album.model.ApplicationInfo;
import ie.philb.album.ui.ApplicationUi;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

/**
 *
 * @author philb
 */
public class AboutDialog extends JDialog {

    private final AboutPanel aboutPanel = new AboutPanel();

    public AboutDialog() {
        super(ApplicationUi.getInstance(), "About");
        setModal(true);

        setLayout(new GridBagLayout());
        GridBagCellConstraints gbc = new GridBagCellConstraints(0, 0).weight(1).fillBoth();
        add(aboutPanel, gbc);

        setPreferredSize(new Dimension(350, 200));
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

    public class AboutPanel extends AppPanel {

        private final ApplicationInfo applicationInfo;
        private final JLabel lblApplicationName = new JLabel();
        private final JLabel lblGitInfo = new JLabel();
        private final JLabel lblBuildInfo = new JLabel();
        private final JButton btnOk = new JButton("OK");

        public AboutPanel() {
            background(Color.WHITE);
            this.applicationInfo = new ApplicationInfo();

            lblApplicationName.setText(applicationInfo.getProjectName() + " v" + applicationInfo.getProjectVersion());
            lblApplicationName.setFont(getFont().deriveFont(Font.BOLD).deriveFont(18f));
            lblGitInfo.setText("Branch: " + applicationInfo.getGitBranch() + ", " + applicationInfo.getGitCommit());
            lblBuildInfo.setText("Build time: " + applicationInfo.getBuildTime());

            btnOk.addActionListener((ActionEvent ae) -> {
                AboutDialog.this.dispose();
            });

            GridBagCellConstraints gbc = new GridBagCellConstraints(0, 0).weightx(1).fillHorizontal().anchorNorth();
            gbc.inset(2);

            add(lblApplicationName, gbc);

            gbc.incy();
            add(lblGitInfo, gbc);

            gbc.incy();
            add(lblBuildInfo, gbc);

            gbc.incy();
            add(filler(), gbc.weight(1).fillBoth());

            gbc.incy().fillNone().weighty(0).anchorSouthEast();
            add(btnOk, gbc);
        }
    }

}
