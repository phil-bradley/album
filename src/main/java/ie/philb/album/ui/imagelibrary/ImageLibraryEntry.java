/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.imagelibrary;

import ie.philb.album.ui.common.Icons;
import ie.philb.album.util.FileUtils;
import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author Philip.Bradley
 */
public class ImageLibraryEntry {

    private static final int MAX_NAME_LEN = 20;

    private final File file;
    private ImageIcon icon;
    private final String title;

    public ImageLibraryEntry(File file) {
        this(file, file.getName());
    }

    public ImageLibraryEntry(File file, String title) {
        this.file = file;
        this.title = title;

        if (file.isDirectory()) {
            this.icon = Icons.FOLDER;
        } else {
            if (FileUtils.isImage(file)) {
                this.icon = new ImageIcon(file.getAbsolutePath());
            }
        }
    }

    public String getTitle() {

        if (title.length() > MAX_NAME_LEN) {
            return title.substring(MAX_NAME_LEN) + "...";
        }

        return title;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public File getFile() {
        return file;
    }

    public boolean isDirectory() {
        return file.isDirectory();
    }
}
