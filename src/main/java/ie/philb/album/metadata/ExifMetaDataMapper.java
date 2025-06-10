/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.metadata;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.exif.ExifImageDirectory;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author philb
 */
public class ExifMetaDataMapper extends AbstractMetaDataMapper {

    public ExifMetaDataMapper(File file) throws ImageProcessingException, IOException {
        super(file);
    }

    @Override
    protected Class<? extends Directory> getDirectoryType() {
        return ExifImageDirectory.class;
    }

    @Override
    protected int getImageWidthTag() {
        return ExifImageDirectory.TAG_IMAGE_WIDTH;
    }

    @Override
    protected int getImageHeightTag() {
        return ExifImageDirectory.TAG_IMAGE_HEIGHT;
    }

    @Override
    protected int getXResolutionTag() {
        return ExifImageDirectory.TAG_X_RESOLUTION;
    }

    @Override
    protected int getYResolutionTag() {
        return ExifImageDirectory.TAG_Y_RESOLUTION;
    }

}
