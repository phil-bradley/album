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

    private ImageIcon imageIcon;

    public PageEntryModel(File imageFile) {
        try {
            this.imageIcon = new ImageIcon(imageFile.getCanonicalPath());
        } catch (IOException ex) {
            throw new RuntimeException("Cannot load icon", ex);
        }
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }
}
