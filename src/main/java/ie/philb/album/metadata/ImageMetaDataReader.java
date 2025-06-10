/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.metadata;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifImageDirectory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.gif.GifHeaderDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.drew.metadata.png.PngDirectory;
import ie.philb.album.util.ClassBuilder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author philb
 */
public class ImageMetaDataReader {

    private static final Map<Class, Class<? extends AbstractMetaDataMapper>> mappers = Map.of(
            ExifImageDirectory.class, ExifMetaDataMapper.class,
            ExifSubIFDDirectory.class, ExifSubMetaDataMapper.class,
            ExifIFD0Directory.class, ExifIFD0Mapper.class,
            JpegDirectory.class, JpegMetaDataMapper.class,
            PngDirectory.class, PngMetaDataMapper.class,
            GifHeaderDirectory.class, GifMetaDataMapper.class
    );

    private final File file;

    public ImageMetaDataReader(File file) {
        this.file = file;
    }

    public ImageMetaData getMetaData() throws Exception {
        ImageMetaData imageMetaData = new ImageMetaData();
        imageMetaData.getMappers().addAll(getMetaDataMappers());
        return imageMetaData;
    }

    private List<AbstractMetaDataMapper> getMetaDataMappers() throws Exception {

        List<AbstractMetaDataMapper> imageMappers = new ArrayList();

        ClassBuilder classBuilder = new ClassBuilder();

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);

            for (Class c : mappers.keySet()) {
                if (metadata.containsDirectoryOfType(c)) {
                    Class<? extends AbstractMetaDataMapper> clazz = mappers.get(c);
                    AbstractMetaDataMapper mapper = classBuilder.createInstance(clazz, file);
                    imageMappers.add(mapper);
                }
            }

        } catch (ImageProcessingException | IOException ex) {
        }

        return imageMappers;
    }

    public void dumpMetaData() {

        try {
            Metadata imageMetadata = ImageMetadataReader.readMetadata(file);

            for (Directory d : imageMetadata.getDirectories()) {
                System.out.println("Got directory: " + d.getName() + ", " + d.getTagCount() + " tags");
            }

//            for (Directory d : imageMetadata.getDirectories()) {
//
//                Collection<Tag> tags = d.getTags();
//                for (Tag tag : tags) {
//                    String value = d.getString(tag.getTagType());
//                    if (value == null) {
//                        continue;
//                    }
//                    System.out.println(tag.getTagName() + ":    " + value);
//                }
//            }
        } catch (ImageProcessingException | IOException ex) {

        }
    }
}
