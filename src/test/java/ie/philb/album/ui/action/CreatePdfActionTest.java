/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ie.philb.album.ui.action;

import ie.philb.album.AppEventBus;
import ie.philb.album.AppSession;
import ie.philb.album.Context;
import ie.philb.album.exporter.OpenPdfExporter;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageSize;
import java.io.File;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class CreatePdfActionTest {

    @Test
    public void givenAlbum_whenPdfActionInvoked_expectedPdfWritten() throws Exception {

        Context context = new Context(null, new AppSession(new AppEventBus()));
        context.session().setAlbumModel(new AlbumModel(PageSize.A4_Landscape, 0, 0));

        int pageCount = 10;

        AddPageAction action = new AddPageAction(context.session());

        for (int i = 0; i < pageCount; i++) {
            action.doAction();
        }

        File outputFile = File.createTempFile("test", "pdf");
        assertTrue(outputFile.exists());

        CreatePdfAction pdfAction = new CreatePdfAction(context.session(), new OpenPdfExporter(), outputFile);
        pdfAction.doAction();

        PDDocument document = Loader.loadPDF(outputFile);

        // Expect pageCount pages, + 1 additional blank page, 
        assertEquals(pageCount + 1, document.getNumberOfPages());

        outputFile.delete();
        assertFalse(outputFile.exists());
    }

}
