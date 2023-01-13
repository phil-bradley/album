/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.imagelibrary;

import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author Philip.Bradley
 */
public class ImageLibraryEntry {

    private static final int MAX_NAME_LEN = 20;

    private final File file;
    private ImageIcon icon = null;
    private String title = "";

    public ImageLibraryEntry(File file) {
        this.file = file;
        this.title = file.getName().trim();

        if (title.length() > MAX_NAME_LEN) {
            this.title = title.substring(MAX_NAME_LEN) + "...";
        }
    }

    public String getTitle() {
        return title;
    }

    public ImageIcon getIcon() {

        if (icon != null) {
            return icon;
        }

        if (!file.exists()) {
            return null;
        }

        this.icon = new ImageIcon(file.getAbsolutePath());

        return icon;
    }
}
