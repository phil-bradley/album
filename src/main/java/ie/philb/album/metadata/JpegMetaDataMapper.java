/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.metadata;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.jpeg.JpegDirectory;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author philb
 */
public class JpegMetaDataMapper implements MetaDataMapper {

    @Override
    public ImageMetaData getMetaData(File file) {

        ImageMetaData imd = new ImageMetaData();

        Metadata md = null;
        try {
            md = ImageMetadataReader.readMetadata(file);
        } catch (ImageProcessingException | IOException ex) {
        }

        if (md == null) {
            return imd;
        }

        JpegDirectory dir = md.getFirstDirectoryOfType(JpegDirectory.class);

        if (dir == null) {
            return imd;
        }

        try {
            imd.setWidth(dir.getImageWidth());
            imd.setHeight(dir.getImageHeight());
        } catch (MetadataException ex) {

        }

        return imd;
    }

//    @Override
//    public Dimension getSize() {
//
//        int width = 0;
//        int height = 0;
//
//        try {
//            width = jpegDirectory.getImageWidth();
//            height = jpegDirectory.getImageHeight();
//        } catch (Exception ingored) {
//        }
//
//        return new Dimension(width, height);
//    }
}
