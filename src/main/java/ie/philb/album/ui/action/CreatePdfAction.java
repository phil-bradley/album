/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action;

import ie.philb.album.AppSession;
import ie.philb.album.exporter.AlbumExporter;
import ie.philb.album.exporter.ExportException;
import java.io.File;
import java.io.FileOutputStream;

/**
 *
 * @author Philip.Bradley
 */
public class CreatePdfAction extends AbstractAction<Void> {

    private final File file;
    private final AlbumExporter albumExporter;

    public CreatePdfAction(AppSession session, AlbumExporter exporter, File file) {
        super(session);
        this.file = file;
        this.albumExporter = exporter;
    }

    @Override
    protected Void doAction() throws Exception {

        logger.info("Creating doc...");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            albumExporter.export(session.getAlbumModel(), fos);
        } catch (Exception ex) {
            throw new ExportException("Export failed", ex);
        }

        return null;
    }
}
