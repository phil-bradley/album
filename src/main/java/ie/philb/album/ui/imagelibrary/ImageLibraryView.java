/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ie.philb.album.ui.imagelibrary;

import ie.philb.album.AppContext;
import ie.philb.album.ui.command.BrowseToParentCommand;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.common.Icons;
import ie.philb.album.ui.common.foldernavigator.FolderNavigationListener;
import ie.philb.album.ui.common.foldernavigator.FolderNavigationPanel;
import ie.philb.album.ui.dnd.ImageLibraryTransferHandler;
import ie.philb.album.util.FileUtils;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Philip.Bradley
 */
public class ImageLibraryView extends AppPanel {

    private final JList<ImageLibraryEntry> list = new JList<>();
    private final JToolBar toolBar = new JToolBar();
    private JButton btnHome;
    private JButton btnUp;
    private FolderNavigationPanel folderNavigationPanel;

    public ImageLibraryView() {

        list.setDragEnabled(true);
        list.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        list.setCellRenderer(new ImageLibraryViewCellRenderer());

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        initToolBar();

        GridBagCellConstraints gbc = new GridBagCellConstraints().fillHorizontal().weight(1, 0).anchorWest();
        add(toolBar, gbc);

        gbc.xy(0, 1).weight(1).width(1).fillBoth();
        add(scrollPane, gbc);

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                ImageLibraryEntry selected = list.getSelectedValue();

                if (selected == null) {
                    return;
                }

                if (selected.isDirectory()) {
                    setBrowseLocation(selected.getFile());
                }

                if (evt.getClickCount() == 2 && FileUtils.isImage(selected.getFile())) {
                    AppContext.INSTANCE.libraryImageSelected(selected);
                }
            }
        });

        list.setDragEnabled(true);
        list.setTransferHandler(new ImageLibraryTransferHandler());

        try {
            list.setModel(new ImageLibraryListModel(FileUtils.getHomeDirectory()));
        } catch (Exception ex) {
            String msg = "Failed to initialise library folder " + FileUtils.getHomeDirectory() + "\n" + ex.getMessage();
            JOptionPane.showMessageDialog(null, msg);
        }
    }

    @Override
    public void browseLocationUpdated(File file) {
        setBrowseLocation(file);
        btnUp.setEnabled(file.getParent() != null);
    }

    private void setBrowseLocation(File file) {
        ImageLibraryListModel model = new ImageLibraryListModel(file);
        list.setModel(model);
        folderNavigationPanel.setPath(file);
    }

    private void initToolBar() {

        btnHome = new JButton(Icons.Regular.FILE_HOME);
        btnHome.setToolTipText("Home");
        btnHome.addActionListener((ActionEvent ae) -> {
            setBrowseLocation(FileUtils.getHomeDirectory());
        });

        toolBar.add(btnHome);

        btnUp = new JButton(Icons.Small.FILE_PARENT);
        btnUp.setToolTipText("Up");
        btnUp.addActionListener((ActionEvent ae) -> {
            new BrowseToParentCommand().execute();
        });

        toolBar.add(btnUp);

        folderNavigationPanel = new FolderNavigationPanel(FileUtils.getHomeDirectory());
        folderNavigationPanel.addListener(new FolderNavigationListener() {
            @Override
            public void locationUpdated(File file) {
                setBrowseLocation(file);
            }
        });

        toolBar.add(folderNavigationPanel);
    }
}
