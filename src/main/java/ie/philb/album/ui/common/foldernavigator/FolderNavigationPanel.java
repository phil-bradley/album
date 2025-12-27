/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.foldernavigator;

import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.util.FileUtils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author philb
 */
public class FolderNavigationPanel extends AppPanel {

    private File currentLocation;
    private final List<FolderNavigationListener> listeners = new ArrayList<>();
    private final List<Path> pathToRoot = new ArrayList<>();
    private static final Color COLOR_SELECTED_FOREGROUND = UIManager.getColor("MenuItem.selectionForeground");
    private static final Color COLOR_SELECTED_BACKGROUND = UIManager.getColor("MenuItem.selectionBackground");
    private static final Color COLOR_STD_FOREGROUND = UIManager.getColor("MenuItem.foreground");

    private JScrollPane scrollPane = new JScrollPane(getNodesPanel());

    public FolderNavigationPanel(File file) {

        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        GridBagCellConstraints gbc = new GridBagCellConstraints().weight(1).fillBoth();
        add(scrollPane, gbc);

        setPath(file);

        scrollPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent evt) {
                JScrollBar hBar = scrollPane.getHorizontalScrollBar();
                hBar.setValue(hBar.getMaximum());
            }
        });
    }

    public void addListener(FolderNavigationListener l) {
        this.listeners.add(l);
    }

    public void removeListener(FolderNavigationListener l) {
        this.listeners.remove(l);
    }

    public final void setPath(File folder) {

        if (!folder.exists()) {
            throw new RuntimeException("Folder does not exist: " + folder.getAbsolutePath());
        }

        if (!folder.isDirectory()) {
            throw new RuntimeException("Not a folder: " + folder.getAbsolutePath());
        }

        if (isSameFile(currentLocation, folder)) {
            return;
        }

        this.currentLocation = folder;

        this.pathToRoot.clear();
        this.pathToRoot.addAll(FileUtils.getPathToRoot(folder.toPath()));

        addNodes();
        fireLocationUpdated(folder);
    }

    private boolean isSameFile(File file1, File file2) {

        if (file1 == null || file2 == null) {
            return false;
        }

        try {
            return Files.isSameFile(file1.toPath(), file2.toPath());
        } catch (IOException ex) {
        }

        return false;
    }

    private void addNodes() {
        AppPanel nodesPanel = getNodesPanel();
        scrollPane.setViewportView(nodesPanel);

        nodesPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent evt) {
                JScrollBar hBar = scrollPane.getHorizontalScrollBar();
                hBar.setValue(hBar.getMaximum());
            }
        });

        revalidate();
        repaint();

        JScrollBar hBar = scrollPane.getHorizontalScrollBar();
        hBar.setValue(hBar.getMaximum());
    }

    private AppPanel getNodesPanel() {

        AppPanel panel = new AppPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        // Leading space for visual appeal
        panel.add(Box.createRigidArea(new Dimension(5, 1)));

        for (Path path : pathToRoot) {
            FolderPanelLabel label = new FolderPanelLabel(path);
            label.addMouseListener(new NodeMouseListener());
            panel.add(label);

            if (FileUtils.isRoot(path) && File.listRoots().length > 1) {
                label.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mousePressed(MouseEvent e) {
                        maybeShowPopup(e);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        maybeShowPopup(e);
                    }

                    private void maybeShowPopup(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            FolderPanelLabel label = (FolderPanelLabel) e.getSource();
                            RootsMenu rootsMenu = new RootsMenu();
                            rootsMenu.show(label, e.getX(), e.getY());
                        }
                    }
                });
            }

            List<Path> children = getChildFolders(path);

            if (children.isEmpty()) {
                continue;
            }

            FolderPanelLabel controlLabel = new FolderPanelLabel(path, ">");
            panel.add(controlLabel);

            controlLabel.addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    maybeShowPopup(e);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    maybeShowPopup(e);
                }

                private void maybeShowPopup(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        FolderPanelLabel label = (FolderPanelLabel) e.getSource();
                        Path path = label.getPath();

                        FolderMenu folderMenu = new FolderMenu(path);
                        folderMenu.show(label, e.getX(), e.getY());
                    }
                }
            });
        }

        // Add some trailing empty space for visual appeal
        panel.add(Box.createRigidArea(new Dimension(20, 1)));
        return panel;
    }

    private void fireLocationUpdated(File folder) {
        listeners.forEach(l -> l.locationUpdated(folder));
    }

    private class FolderPanelLabel extends JLabel {

        private final Path path;
        private final String text;

        public FolderPanelLabel(Path path) {
            this(path, "");
        }

        public FolderPanelLabel(Path path, String text) {
            this.path = path;
            this.text = text;

            if (text.isBlank()) {
                setText(" " + getPathName());
            } else {
                setText(this.text);
            }

            addMouseListener(new NodeMouseListener());
        }

        public Path getPath() {
            return path;
        }

        private String getPathName() {

            String pathName = path.toFile().getName();
            if (pathName == null || pathName.isBlank()) {
                pathName = path.toFile().toString();
            }

            return pathName;
        }
    }

    private List<Path> getChildFolders(Path parent) {

        try (Stream<Path> paths = Files.list(parent)) {
            return paths
                    .filter(Files::isDirectory)
                    .sorted((Path t, Path t1) -> t.toFile().getName().toLowerCase().compareTo(t1.toFile().getName().toLowerCase()))
                    .toList();
        } catch (IOException e) {
        }

        return Collections.emptyList();
    }

    private class RootsMenu extends JPopupMenu {

        public RootsMenu() {
            File[] roots = File.listRoots();

            for (File root : roots) {
                JMenuItem menuItem = new JMenuItem(root.getPath());
                menuItem.addActionListener((ActionEvent ae) -> {
                    setPath(root);
                });
                add(menuItem);

            }
        }
    }

    private class FolderMenu extends JPopupMenu {

        public FolderMenu(Path path) {
            List<Path> children = getChildFolders(path);

            for (Path child : children) {
                JMenuItem menuItem = new JMenuItem(child.toFile().getName());
                menuItem.addActionListener((ActionEvent ae) -> {
                    setPath(child.toFile());
                });
                add(menuItem);
            }
        }
    }

    private class NodeMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            FolderPanelLabel label = (FolderPanelLabel) e.getSource();
            setPath(label.getPath().toFile());
        }

        @Override
        public void mouseEntered(MouseEvent e) {

            JLabel label = (JLabel) e.getSource();
            label.setBackground(COLOR_SELECTED_BACKGROUND);
            label.setForeground(COLOR_SELECTED_FOREGROUND);
            label.setOpaque(true);
        }

        @Override
        public void mouseExited(MouseEvent e) {

            JLabel label = (JLabel) e.getSource();
            label.setBackground(Color.WHITE);
            label.setForeground(COLOR_STD_FOREGROUND);
            label.setOpaque(false);
        }
    }
}
