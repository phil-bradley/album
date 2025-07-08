/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.foldernavigator;

import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.util.FileUtils;
import java.awt.Color;
import java.awt.event.ActionEvent;
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
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

/**
 *
 * @author philb
 */
public class FolderNavigationPanel extends AppPanel {

    private File currentLocation;
    private final List<FolderNavigationListener> listeners = new ArrayList<>();
    private final List<Path> pathToRoot = new ArrayList<>();
    private static final Color HIGHLIGHT = new Color(240, 240, 240);

    public FolderNavigationPanel(File file) {
        background(Color.WHITE);
        setPath(file);
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
        this.removeAll();

        GridBagCellConstraints gbc = new GridBagCellConstraints().anchorWest().fillNone().insetLeft(2).insetRight(5);

        for (Path path : pathToRoot) {
            FolderPanelLabel label = new FolderPanelLabel(path);
            label.addMouseListener(new NodeMouseListener());
            add(label, gbc);
            gbc.incx();

            FolderPanelLabel controlLabel = new FolderPanelLabel(path, ">");
            add(controlLabel, gbc);
            gbc.incx();

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

        gbc.fillHorizontal().weightx(1).incx();
        add(filler(), gbc);

        revalidate();
        repaint();
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
                setText(getPathName());
            } else {
                setText(this.text);
            }

            setBackground(Color.WHITE);
            setOpaque(true);

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

        private List<Path> getChildFolders(Path parent) {

            try (Stream<Path> paths = Files.list(parent)) {
                return paths
                        .filter(Files::isDirectory)
                        .toList();
            } catch (IOException e) {
            }

            return Collections.emptyList();
        }
    }

    private class NodeMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            FolderPanelLabel label = (FolderPanelLabel) e.getSource();
            System.out.println("Clicked on: " + label.getPath());

            setPath(label.getPath().toFile());
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            label.setBackground(HIGHLIGHT);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            label.setBackground(Color.WHITE);
        }
    }
}
