/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;

/**
 *
 * @author philb
 */
public class PageEntryModel {

    private final File imageFile;

    public PageEntryModel(File imageFile) {
        this.imageFile = imageFile;
    }

    public ImageIcon getImageIcon() {
        try {
            return new ImageIcon(imageFile.getCanonicalPath());
        } catch (IOException ex) {
            return null;
        }
    }
}
