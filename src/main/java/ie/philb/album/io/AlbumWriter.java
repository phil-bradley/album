/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import ie.philb.album.model.AlbumModel;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author philb
 */
public class AlbumWriter {

    private final AlbumDataMapper dataMapper;

    public AlbumWriter(AlbumDataMapper dataMapper) {
        this.dataMapper = dataMapper;
    }

    public void write(File file, AlbumModel albumModel) throws IOException {
        AlbumData albumData = dataMapper.map(albumModel);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.writeValue(file, albumData);
    }

}
