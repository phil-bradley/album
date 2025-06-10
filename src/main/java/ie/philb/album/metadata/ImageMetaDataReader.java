/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.metadata;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.jpeg.JpegDirectory;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author philb
 */
public class ImageMetaDataReader {

    private static final Map<Class, MetaDataMapper> mappers = Map.of(
            JpegDirectory.class, new JpegMetaDataMapper()
    );

    private final File file;

    public ImageMetaDataReader(File file) {
        this.file = file;
    }

    public ImageMetaData getMetaData() {
        return getMetaDataMapper().getMetaData(file);
    }

    private MetaDataMapper getMetaDataMapper() {

        MetaDataMapper mapper = null;

        for (Class c : mappers.keySet()) {
            if (mappers.containsKey(c)) {
                mapper = mappers.get(c);
            }
        }

        if (mapper == null) {
            return new NullMetaDataMapper();
        }

        return mapper;
    }

    public void dumpMetaData() {

        try {
            Metadata imageMetadata = ImageMetadataReader.readMetadata(file);

            for (Directory d : imageMetadata.getDirectories()) {
                System.out.println("Got directory: " + d.getName());
                Collection<Tag> tags = d.getTags();
                for (Tag tag : tags) {
                    String value = d.getString(tag.getTagType());
                    if (value == null) {
                        continue;
                    }
                    System.out.println(tag.getTagName() + ":    " + value);
                }
            }
        } catch (ImageProcessingException | IOException ex) {

        }
    }
}
