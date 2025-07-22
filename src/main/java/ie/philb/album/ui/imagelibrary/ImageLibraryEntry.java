/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.imagelibrary;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Philip.Bradley
 */
public class ImageLibraryEntry {

    private static final int MAX_NAME_LEN = 20;

    private final File file;
    private final String title;


    public ImageLibraryEntry(File file) throws IOException {
        this(file, file.getName());
    }

    public ImageLibraryEntry(File file, String title) throws IOException {
        this.file = file;
        this.title = title.trim();
    }

    public String getTitle() {

        if (title.length() > MAX_NAME_LEN) {
            return title.substring(MAX_NAME_LEN) + "...";
        }

        return title;
    }

    public File getFile() {
        return file;
    }

    public boolean isDirectory() {
        return file.isDirectory();
    }
}
