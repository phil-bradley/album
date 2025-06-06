/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ie.philb.album.ui.imagelibrary;

import ie.philb.album.ui.dnd.ImageLibraryTransferHandler;
import ie.philb.album.AppContext;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Philip.Bradley
 */
public class ImageLibraryView extends AppPanel {

    private final JList<ImageLibraryEntry> list = new JList<>();

    public ImageLibraryView() {

        list.setDragEnabled(true);
        list.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        list.setCellRenderer(new ImageLibraryViewCellRenderer());

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        gridbag();
        add(scrollPane, new GridBagCellConstraints().fillBoth().weight(1));

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {

                if (evt.getClickCount() == 2) {
                    ImageLibraryEntry selected = list.getSelectedValue();

                    if (selected != null) {
                        AppContext.INSTANCE.libraryImageSelected(selected);
                    }
                }
            }
        });

        list.setDragEnabled(true);
        list.setTransferHandler(new ImageLibraryTransferHandler());

        File baseFolder = new File("/home/philb/Pictures");

        try {
            list.setModel(new ImageLibraryListModel(baseFolder));
        } catch (Exception ex) {
            String msg = "Failed to initialise library folder " + baseFolder + "\n" + ex.getMessage();
            JOptionPane.showMessageDialog(null, msg);
        }
    }

    public void setBaseFolder(File file) {

    }

}
