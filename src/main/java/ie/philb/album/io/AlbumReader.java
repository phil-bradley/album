/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import ie.philb.album.model.AlbumModel;
import java.io.File;
import java.time.LocalDateTime;

/**
 *
 * @author philb
 */
public class AlbumReader {

    private final File file;

    public AlbumReader(File file) {
        this.file = file;
    }

    public AlbumModel read() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        AlbumData albumData = mapper.readValue(file, AlbumData.class);

        AlbumModel model =  new AlbumDataMapper().map(albumData);
        model.setFile(file);
        model.setLastSaveDate(LocalDateTime.now());
        
        return model;
    }
}
