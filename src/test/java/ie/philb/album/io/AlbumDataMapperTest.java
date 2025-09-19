/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageSize;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class AlbumDataMapperTest {

    private static AlbumModel albumModel;

    @BeforeAll
    static void readAlbum() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());

        File testAlbumFile = new File(AlbumDataMapperTest.class.getResource("/test-album.json").getPath());
        AlbumData albumData = mapper.readValue(testAlbumFile, AlbumData.class);
        albumModel = new AlbumDataMapper().map(albumData);
    }

    @Test
    void albumReadFromJson() throws Exception {
        assertNotNull(albumModel);

        assertEquals(PageSize.A4_Landscape, albumModel.getPageSize());
        assertEquals(10, albumModel.getDefaultMargin());
        assertEquals(25, albumModel.getDefaultGutter());

        assertEquals(21, albumModel.getPages().size());
    }

    @Test
    @Disabled
    void pageGeometryReadFromJson() throws Exception {

    }

    @Test
    @Disabled
    void imagePropertiesReadFromJson() throws Exception {

    }

    @Test
    @Disabled
    void textPropertiesReadFromJson() throws Exception {

    }
}
