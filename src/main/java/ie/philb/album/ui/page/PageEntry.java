/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.page;

import ie.philb.album.ui.common.ImagePanelFill;
import javax.swing.ImageIcon;

/**
 *
 * @author Philip.Bradley
 */
public class PageEntry {

    private int width;
    private int height;
    private int offsetX;
    private int offsetY;
    private ImageIcon icon;
    private ImagePanelFill fill = ImagePanelFill.CropToFit;

    public PageEntry(int width, int height, int offsetX, int offsetY) {
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public ImagePanelFill getFill() {
        return fill;
    }

    public void setFill(ImagePanelFill fill) {
        this.fill = fill;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    @Override
    public String toString() {
        return "PageEntry{" + "width=" + width + ", height=" + height + ", offsetX=" + offsetX + ", offsetY=" + offsetY + '}';
    }

}
