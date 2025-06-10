/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.metadata;

import com.drew.metadata.MetadataException;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author philb
 */
public class ImageMetaData {

    private final List<AbstractMetaDataMapper> mappers = new ArrayList<>();

    public ImageMetaData() {

    }

    public List<AbstractMetaDataMapper> getMappers() {
        return mappers;
    }

    public Dimension getSize() {
        return new Dimension(getWidth(), getHeight());
    }

    public Dimension getResolution() {

        int xResolution = getXResolution();
        int yResolution = getYResolution();

        return new Dimension(yResolution, yResolution);
    }

    public int getXResolution() {

        for (AbstractMetaDataMapper mapper : mappers) {
            try {
                if (mapper.getXResolution() != 0) {
                    return mapper.getXResolution();
                }
            } catch (MetadataException ex) {
            }
        }

        return 0;
    }

    public int getYResolution() {

        for (AbstractMetaDataMapper mapper : mappers) {
            try {
                if (mapper.getYResolution() != 0) {
                    return mapper.getYResolution();
                }
            } catch (MetadataException ex) {
            }
        }

        return 0;
    }

    public int getWidth() {

        for (AbstractMetaDataMapper mapper : mappers) {
            try {
                if (mapper.getImageWidth() != 0) {
                    return mapper.getImageWidth();
                }
            } catch (MetadataException ex) {
            }
        }

        return 0;
    }

    public int getHeight() {

        for (AbstractMetaDataMapper mapper : mappers) {
            try {
                if (mapper.getImageWidth() != 0) {
                    return mapper.getImageWidth();
                }
            } catch (MetadataException ex) {
            }

        }

        return 0;
    }

    @Override
    public String toString() {
        return "Size " + getWidth() + "x" + getHeight() + ", Resolution: " + getXResolution() + "x" + getYResolution();
    }
}
