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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class DeletePageActionTest {

    @Test
    public void givenAlbumWithPages_whenDeletePage_expectPageDeleted() throws Exception {

        Context context = new Context(null, new AppSession(new AppEventBus()));
        context.session().setAlbumModel(new AlbumModel(PageSize.A4_Landscape, 0, 0));

        int pageCount = 10;

        AddPageAction action = new AddPageAction(context.session());

        for (int i = 0; i < pageCount; i++) {
            action.doAction();
        }

        assertEquals(pageCount, context.session().getAlbumModel().getPages().size());

        new DeletePageAction(context.session(), 0).doAction();
        assertEquals(pageCount - 1, context.session().getAlbumModel().getPages().size());
    }

    @Test
    public void givenAlbumWithPages_whenDeleteAllPages_expectAlltPagesDeleted() throws Exception {

        Context context = new Context(null, new AppSession(new AppEventBus()));
        context.session().setAlbumModel(new AlbumModel(PageSize.A4_Landscape, 0, 0));

        int pageCount = 10;

        AddPageAction action = new AddPageAction(context.session());

        for (int i = 0; i < pageCount; i++) {
            action.doAction();
        }

        assertEquals(pageCount, context.session().getAlbumModel().getPages().size());

        for (int i = 0; i < pageCount; i++) {
            new DeletePageAction(context.session(), 0).doAction();
        }

        assertEquals(0, context.session().getAlbumModel().getPages().size());
    }

    @Test
    public void givenAlbumWithPages_whenDeletePageOutOfBounds_expecException() throws Exception {

        Context context = new Context(null, new AppSession(new AppEventBus()));
        context.session().setAlbumModel(new AlbumModel(PageSize.A4_Landscape, 0, 0));

        int pageCount = 10;

        AddPageAction action = new AddPageAction(context.session());

        for (int i = 0; i < pageCount; i++) {
            action.doAction();
        }

        assertEquals(pageCount, context.session().getAlbumModel().getPages().size());

        // Delete page -1, expect exception
        Exception thrown = assertThrows(IllegalArgumentException.class, () -> {
            new DeletePageAction(context.session(), -1).doAction();
        });

        assertTrue(thrown.getMessage().contains("Cannot delete page"));

        // Delete page after last
        thrown = assertThrows(IllegalArgumentException.class, () -> {
            new DeletePageAction(context.session(), pageCount).doAction();
        });

        assertTrue(thrown.getMessage().contains("Cannot delete page"));
    }

    @Test
    public void givenEmptyAlbum_whenDeletePage_expecException() throws Exception {

        Context context = new Context(null, new AppSession(new AppEventBus()));
        context.session().setAlbumModel(new AlbumModel(PageSize.A4_Landscape, 0, 0));

        assertEquals(0, context.session().getAlbumModel().getPages().size());

        Exception thrown = assertThrows(IllegalArgumentException.class, () -> {
            new DeletePageAction(context.session(), 0).doAction();
        });

        assertTrue(thrown.getMessage().contains("Cannot delete page from empty album"));
    }
}
