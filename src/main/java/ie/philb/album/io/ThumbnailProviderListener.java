/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.io;

import java.awt.image.BufferedImage;

/**
 *
 * @author philb
 */
public interface ThumbnailProviderListener {

    void thumbnailLoaded(BufferedImage image);
}
