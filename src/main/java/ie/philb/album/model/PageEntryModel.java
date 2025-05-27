/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import javax.swing.ImageIcon;

/**
 *
 * @author philb
 */
public class PageEntryModel {

    private final PageCell cell;
    private ImageIcon imageIcon;

    public PageEntryModel(PageCell cell) {
        this.cell = cell;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    public PageCell getCell() {
        return cell;
    }

}
