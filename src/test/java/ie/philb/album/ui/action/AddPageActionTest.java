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
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class AddPageActionTest {

    @Test
    public void givenAlbum_whenAddPageActionInvoked_expectPageAdded() throws Exception {

        AlbumModel albumModel = new AlbumModel(PageSize.A4_Landscape, 0, 0);
        Context context = new Context(null, new AppSession(new AppEventBus()));
        context.session().setAlbumModel(albumModel);
        
        AddPageAction action = new AddPageAction(context.session());
        action.doAction();

        assertEquals(1, albumModel.getPages().size());

        action.doAction();
        assertEquals(2, albumModel.getPages().size());
    }

}
