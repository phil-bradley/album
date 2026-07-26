/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action;

import ie.philb.album.AppSession;
import ie.philb.album.io.AlbumDataMapper;
import ie.philb.album.io.AlbumWriter;
import java.io.File;
import java.time.LocalDateTime;

/**
 *
 * @author philb
 */
public class SaveAlbumAction extends AbstractAction<Void> {

    private final File saveFile;

    public SaveAlbumAction(AppSession session, File saveFile) {
        super(session);
        this.saveFile = saveFile;
    }

    @Override
    protected Void doAction() throws Exception {
        session.getAlbumModel().setLastSaveDate(LocalDateTime.now());

        AlbumWriter writer = new AlbumWriter(new AlbumDataMapper());
        writer.write(saveFile, session.getAlbumModel());
        
        logger.info("Saved album to {}", saveFile.getAbsolutePath());
        return null;
    }

}
