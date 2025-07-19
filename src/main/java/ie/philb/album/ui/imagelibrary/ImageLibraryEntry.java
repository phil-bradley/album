/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.imagelibrary;

import ie.philb.album.metadata.ImageMetaData;
import ie.philb.album.metadata.ImageMetaDataReader;
import ie.philb.album.ui.common.Icons;
import ie.philb.album.util.FileUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Philip.Bradley
 */
public class ImageLibraryEntry {

    private static final int MAX_NAME_LEN = 20;

    private final File file;
    private BufferedImage image;
    private final String title;
    private ImageMetaData imageMetadata = null;

    public ImageLibraryEntry(File file) throws IOException {
        this(file, file.getName());
    }

    public ImageLibraryEntry(File file, String title) throws IOException {
        this.file = file;
        this.title = title.trim();

        if (FileUtils.isImage(file)) {
            this.image = ImageIO.read(file);
            updateMetadata();
        }
    }

    private void updateMetadata() {

        if (file == null) {
            imageMetadata = null;
            return;
        }

        try {
            this.imageMetadata = new ImageMetaDataReader(file).getMetaData();
        } catch (Exception x) {
        }
    }

    public String getTitle() {

        if (title.length() > MAX_NAME_LEN) {
            return title.substring(MAX_NAME_LEN) + "...";
        }

        return title;
    }

    public ImageIcon getIcon() {

        if (isDirectory()) {
            return Icons.Regular.FOLDER;
        }

        return new ImageIcon(image);
    }

    public File getFile() {
        return file;
    }

    public boolean isDirectory() {
        return file.isDirectory();
    }

    public ImageMetaData getImageMetaData() {
        return imageMetadata;
    }
}
