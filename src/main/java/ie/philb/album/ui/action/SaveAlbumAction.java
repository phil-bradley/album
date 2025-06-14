/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action;

import ie.philb.album.io.AlbumWriter;
import ie.philb.album.model.AlbumModel;
import java.io.File;

/**
 *
 * @author philb
 */
public class SaveAlbumAction extends AbstractAction<Void> {

    private final File saveFile;
    private final AlbumModel albumModel;

    public SaveAlbumAction(File saveFile, AlbumModel albumModel) {
        this.saveFile = saveFile;
        this.albumModel = albumModel;
    }

    @Override
    protected Void doAction() throws Exception {
        AlbumWriter writer = new AlbumWriter(saveFile, albumModel);
        writer.write();
        return null;
    }

}
