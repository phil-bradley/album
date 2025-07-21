/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ie.philb.album.ui.imagelibrary;

import ie.philb.album.AppContext;
import ie.philb.album.io.ThumbnailProvider;
import ie.philb.album.io.ThumbnailProviderListener;
import ie.philb.album.metadata.ImageMetaData;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.Dialogs;
import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.common.Icons;
import ie.philb.album.ui.common.Resources;
import ie.philb.album.ui.common.foldernavigator.FolderNavigationListener;
import ie.philb.album.ui.common.foldernavigator.FolderNavigationPanel;
import ie.philb.album.ui.dnd.ImageLibraryTransferHandler;
import ie.philb.album.util.FileUtils;
import ie.philb.album.util.ImageUtils;
import ie.philb.album.util.StringUtils;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author Philip.Bradley
 */
public class ImageLibraryView extends AppPanel {

    private static final Dimension CELL_SIZE = new Dimension(120, 120);

    private final JList<ImageLibraryEntry> list = new JList<>();
    private final JToolBar toolBar = new JToolBar();
    private JButton btnHome;
    private JButton btnUp;
    private FolderNavigationPanel folderNavigationPanel;
    private final ThumbnailProvider thumbnailProvider;

    public ImageLibraryView() {

        thumbnailProvider = new ThumbnailProvider(CELL_SIZE);
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
        } catch (IOException ex) {
            String msg = "Failed to initialise library folder " + FileUtils.getHomeDirectory() + "\n" + ex.getMessage();
            JOptionPane.showMessageDialog(null, msg);
        }

        System.out.println("Thumbs: " + thumbnailProvider.imageMap.size());
    }

    @Override
    public void browseLocationUpdated(File file) {
        setBrowseLocation(file);
        btnUp.setEnabled(file.getParent() != null);
    }

    private void setBrowseLocation(File file) {
        try {
            ImageLibraryListModel model = new ImageLibraryListModel(file);
            list.setModel(model);
            folderNavigationPanel.setPath(file);
        } catch (IOException ex) {
            Dialogs.showErrorMessage("Failed to open folder " + file.getAbsolutePath(), ex);
        }
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
            ImageLibraryListModel lm = (ImageLibraryListModel) list.getModel();
            File currentLocation = lm.getFolder();

            if (currentLocation.getParentFile() != null) {
                setBrowseLocation(lm.getFolder().getParentFile());
            }
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

    class ImageLibraryViewCellRenderer extends AppPanel implements ThumbnailProviderListener, ListCellRenderer<ImageLibraryEntry> {

        private final ThumbnailView thumbnailView = new ThumbnailView(null);
        private final JLabel lblName = new JLabel();
        private int index = -1;

        public ImageLibraryViewCellRenderer() {

            background(Color.WHITE);
            GridBagCellConstraints gbc = new GridBagCellConstraints().weight(1).fillBoth().insetHorizontal(8).insetVertical(2);
            thumbnailView.setPreferredSize(CELL_SIZE);
            thumbnailView.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));
            add(thumbnailView, gbc);

            gbc.y(1).fillHorizontal().anchorSouth().weighty(0).inset(1);
            lblName.setHorizontalAlignment(SwingConstants.CENTER);
            add(lblName, gbc);

            System.out.println("Thumbs: " + thumbnailProvider.imageMap.size());
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends ImageLibraryEntry> list, ImageLibraryEntry value, int index, boolean isSelected, boolean cellHasFocus) {

            this.index = index;

            if (value.isDirectory()) {
                thumbnailView.setImage(ImageUtils.getBufferedImage(Icons.Regular.FOLDER));
            } else {
                thumbnailProvider.applyImage(value.getFile().getAbsolutePath(), this);
            }

            lblName.setText(StringUtils.truncate(value.getTitle(), 20));
            thumbnailView.setToolTipText(getToolTip(value));

            if (isSelected) {
                setBackground(Resources.COLOR_SELECTED);
                lblName.setForeground(Color.white);
            } else {
                setBackground(Color.WHITE);
                lblName.setForeground(Color.black);
            }

            setToolTipText(getToolTip(value));

            return this;
        }

        private String getToolTip(ImageLibraryEntry entry) {

            if (entry == null || entry.getFile() == null) {
                return "";
            }

            String toolTipText = entry.getFile().getName();

            ImageMetaData imageMetaData = entry.getImageMetaData();

            if (imageMetaData == null) {
                return toolTipText;
            }

            Dimension size = imageMetaData.getSize();
            Dimension resolution = imageMetaData.getResolution();

            String metaData;

            if (size.width == 0) {
                metaData = "Metadata not available";
            } else {
                metaData = "Size " + size.width + "x" + size.height;

                if (resolution.width != 0) {
                    metaData += "\nResolution " + resolution.width + "x" + resolution.height;
                }
            }

            return toolTipText + "\n\n" + metaData;
        }

        @Override
        public void thumbnailLoaded(BufferedImage image) {          
            thumbnailView.setImage(image);
            SwingUtilities.invokeLater(() -> list.repaint(list.getBounds()));
        }

    }

    class ThumbnailView extends AppPanel {

        private BufferedImage sourceImage;

        public ThumbnailView(BufferedImage image) {
            setImage(image);
        }

        public final void setImage(BufferedImage image) {
            if (!Objects.equals(image, sourceImage)) {
                this.sourceImage = image;
                revalidate();
                repaint();
            }
        }

        private int getAvailableWidth() {
            int availableWidth = getBounds().width;
            return availableWidth;
        }

        private int getAvailableHeight() {
            int availableHeight = getBounds().height;
            return availableHeight;
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            if (sourceImage == null) {
                return;
            }

            // Centre image if it's less tall or less wide than the available space
            int x = (getAvailableWidth() - sourceImage.getWidth()) / 2;
            int y = (getAvailableHeight() - sourceImage.getHeight()) / 2;

            g.drawImage(sourceImage, x, y, null);
        }
    }

}
