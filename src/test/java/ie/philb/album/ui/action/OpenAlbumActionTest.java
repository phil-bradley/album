/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ie.philb.album.ui.action;

import ie.philb.album.AppEventBus;
import ie.philb.album.AppSession;
import ie.philb.album.Context;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageSize;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class OpenAlbumActionTest {

    @Test
    public void givenSavedAlbum_whenAlbumLoaded_expectPageCountMatches() throws Exception {

        Context context = new Context(null, new AppSession(new AppEventBus()));
        context.session().setAlbumModel(new AlbumModel(PageSize.A4_Landscape, 0, 0));

        int pageCount = 10;

        for (int i = 0; i < pageCount; i++) {
            new AddPageAction(context.session()).doAction();
        }

        File saveFile = File.createTempFile("test", "album");

        new SaveAlbumAction(context.session(), saveFile).doAction();

        AlbumModel loaded = new OpenAlbumAction(context.session(), saveFile).doAction();
        assertEquals(pageCount, loaded.getPages().size());
    }

}
