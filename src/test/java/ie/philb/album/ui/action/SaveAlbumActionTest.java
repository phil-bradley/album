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
import ie.philb.album.ui.action.callback.Callback;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class SaveAlbumActionTest {

    @Test
    void givenAlbum_whenSaved_expectLastSavedDateUpdated() throws Exception {

        Context context = new Context(null, new AppSession(new AppEventBus()));
        context.session().setAlbumModel(new AlbumModel(PageSize.A4_Landscape, 0, 0));

        AlbumModel model = context.session().getAlbumModel();

        int pageCount = 50;

        for (int i = 0; i < pageCount; i++) {
            new AddPageAction(context.session()).doAction();
        }

        assertNull(model.getLastSaveDate());
        assertTrue(model.hasUnSavedChanges());

        LocalDateTime preSaveDateTime = LocalDateTime.now();

        // Short sleep to ensnure save timestamp is after preSaveDateTie
        Thread.sleep(500);

        File saveFile = File.createTempFile("test", "album");
        new SaveAlbumAction(context.session(), saveFile).doAction();

        assertNotNull(model.getLastSaveDate(), "Save date should not be null");
        assertTrue(model.getLastSaveDate().isAfter(preSaveDateTime), "Save date " + model.getLastSaveDate() + " should be after " + preSaveDateTime);
        assertFalse(model.hasUnSavedChanges());
    }

    @Test
    void givenSaveFileNotExists_whenSaved_expectEception() throws Exception {

        Context context = new Context(null, new AppSession(new AppEventBus()));
        context.session().setAlbumModel(new AlbumModel(PageSize.A4_Landscape, 0, 0));

        File saveFile = new File("/does/not/exist/anywhere/" + UUID.randomUUID().toString());

        CallbackWithException callBack = new CallbackWithException();

        new SaveAlbumAction(context.session(), saveFile).execute(callBack);
        assertFalse(callBack.getSucceeded());
        assertTrue(callBack.getException() instanceof FileNotFoundException);

    }

    class CallbackWithException implements Callback<Void> {

        private Exception exception;
        Boolean succeeded = null;

        @Override
        public void onFailure(Exception exception) {
            this.exception = exception;
            this.succeeded = Boolean.FALSE;
        }

        @Override
        public void onSuccess(Void result) {
            this.succeeded = Boolean.TRUE;
        }

        public Exception getException() {
            return exception;
        }

        public Boolean getSucceeded() {
            return succeeded;
        }
    }
}
