/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.metadata;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author philb
 */
public abstract class AbstractMetaDataMapper {

    private final Metadata metadata;
    private final Directory directory;

    public AbstractMetaDataMapper(File file) throws ImageProcessingException, IOException {
        this.metadata = ImageMetadataReader.readMetadata(file);
        this.directory = getDirectory();
    }

    public int getXResolution() throws MetadataException {
        return directory.getInt(getXResolutionTag());
    }

    public int getYResolution() throws MetadataException {
        return directory.getInt(getYResolutionTag());
    }

    public int getImageWidth() throws MetadataException {
        return directory.getInt(getImageWidthTag());
    }

    public int getImageHeight() throws MetadataException {
        return directory.getInt(getImageHeightTag());
    }

    private Directory getDirectory() {
        return metadata.getFirstDirectoryOfType(getDirectoryType());
    }

    protected abstract Class<? extends Directory> getDirectoryType();

    protected abstract int getImageWidthTag();

    protected abstract int getImageHeightTag();

    protected abstract int getXResolutionTag();

    protected abstract int getYResolutionTag();

//    public final ImageMetaData getMetaData(File file) {
//
//        Metadata md = null;
//        try {
//            md = ImageMetadataReader.readMetadata(file);
//        } catch (ImageProcessingException | IOException ex) {
//        }
//
//        if (md == null) {
//            return new ImageMetaData().metaDataType("Not Supported");
//        }
//
//        return getImageMetaData(md).metaDataType(getMetaDataMapperType());
//    }
//
//    protected abstract ImageMetaData getImageMetaData(Metadata md);
//
//    protected abstract String getMetaDataMapperType();
}
