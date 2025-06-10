/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.metadata;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author philb
 */
public class ExifSubMetaDataMapper extends AbstractMetaDataMapper {

    public ExifSubMetaDataMapper(File file) throws ImageProcessingException, IOException {
        super(file);
    }

    @Override
    protected Class<? extends Directory> getDirectoryType() {
        return ExifSubIFDDirectory.class;
    }

    @Override
    protected int getImageWidthTag() {
        return ExifSubIFDDirectory.TAG_IMAGE_WIDTH;
    }

    @Override
    protected int getImageHeightTag() {
        return ExifSubIFDDirectory.TAG_IMAGE_HEIGHT;
    }

    @Override
    protected int getXResolutionTag() {
        return ExifSubIFDDirectory.TAG_X_RESOLUTION;
    }

    @Override
    protected int getYResolutionTag() {
        return ExifSubIFDDirectory.TAG_Y_RESOLUTION;
    }
}
