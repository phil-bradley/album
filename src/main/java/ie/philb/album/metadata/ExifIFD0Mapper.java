/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.metadata;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.exif.ExifIFD0Directory;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author philb
 */
public class ExifIFD0Mapper extends AbstractMetaDataMapper {

    public ExifIFD0Mapper(File file) throws ImageProcessingException, IOException {
        super(file);
    }

    @Override
    protected Class<? extends Directory> getDirectoryType() {
        return ExifIFD0Directory.class;
    }

    @Override
    protected int getImageWidthTag() {
        return ExifIFD0Directory.TAG_IMAGE_WIDTH;
    }

    @Override
    protected int getImageHeightTag() {
        return ExifIFD0Directory.TAG_IMAGE_HEIGHT;
    }

    @Override
    protected int getXResolutionTag() {
        return ExifIFD0Directory.TAG_X_RESOLUTION;
    }

    @Override
    protected int getYResolutionTag() {
        return ExifIFD0Directory.TAG_Y_RESOLUTION;
    }
}
