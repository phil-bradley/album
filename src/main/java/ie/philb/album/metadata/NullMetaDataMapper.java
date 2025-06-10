/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.metadata;

import java.io.File;

/**
 *
 * @author philb
 */
public class NullMetaDataMapper implements MetaDataMapper {

    @Override
    public ImageMetaData getMetaData(File file) {
        return new ImageMetaData();
    }

}
