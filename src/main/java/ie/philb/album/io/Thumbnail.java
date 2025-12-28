/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.io;

import ie.philb.album.metadata.ImageMetaData;
import java.awt.image.BufferedImage;

/**
 *
 * @author philb
 */
public class Thumbnail {
    
    private BufferedImage bufferedImage;
    private ImageMetaData imageMetaData;
    private String name;
    
    public Thumbnail(String name, BufferedImage bufferedImage, ImageMetaData imageMetaData) {
        this.name = name;
        this.bufferedImage = bufferedImage;
        this.imageMetaData = imageMetaData;
    }
    
    public Thumbnail(String name, BufferedImage bufferedImage) {
        this(name, bufferedImage, new ImageMetaData());
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public ImageMetaData getImageMetaData() {
        return imageMetaData;
    }

    public String getName() {
        return name;
    }   
}