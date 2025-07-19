/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.imagelibrary;

import ie.philb.album.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philip.Bradley
 */
public class ImageLibraryListModel implements ListModel<ImageLibraryEntry> {

    private static final Logger logger = LoggerFactory.getLogger(ImageLibraryListModel.class);

    private final List<ImageLibraryEntry> entries = new ArrayList<>();
    private final List<ListDataListener> listeners = new ArrayList<>();
    private final File folder;

    public ImageLibraryListModel(File folder) throws IOException {

        if (folder == null) {
            logger.info("Base folder cannot be null");
            throw new RuntimeException("Base folder cannot be null");
        }

        if (!folder.exists() || !folder.isDirectory()) {
            logger.info("Base folder {} not found", folder.getAbsoluteFile());
            throw new RuntimeException("Base folder not found: " + folder.getAbsolutePath());
        }

        this.folder = folder;

        File[] children = folder.listFiles();

        if (children == null) {
            return;
        }

        for (File file : children) {
            if (file.isDirectory() && !FileUtils.isHidden(file)) {
                entries.add(new ImageLibraryEntry(file));
            }
        }

        for (File file : children) {
            if (FileUtils.isImage(file)) {
                entries.add(new ImageLibraryEntry(file));
            }
        }
    }

    public File getFolder() {
        return folder;
    }

    @Override
    public int getSize() {
        return entries.size();
    }

    @Override
    public ImageLibraryEntry getElementAt(int index) {
        return entries.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

}
