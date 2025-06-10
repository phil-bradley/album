/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.metadata;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.gif.GifHeaderDirectory;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author philb
 */
public class GifMetaDataMapper extends AbstractMetaDataMapper {

    public GifMetaDataMapper(File file) throws ImageProcessingException, IOException {
        super(file);
    }

    @Override
    protected Class<? extends Directory> getDirectoryType() {
        return GifHeaderDirectory.class;
    }

    @Override
    protected int getImageWidthTag() {
        return GifHeaderDirectory.TAG_IMAGE_WIDTH;
    }

    @Override
    protected int getImageHeightTag() {
        return GifHeaderDirectory.TAG_IMAGE_HEIGHT;
    }

    @Override
    protected int getXResolutionTag() {
        return -1; // No such tag
    }

    @Override
    protected int getYResolutionTag() {
        return -1; // No such tag
    }

}
