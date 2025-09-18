/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action;

import ie.philb.album.io.AlbumReader;
import ie.philb.album.model.AlbumModel;
import java.io.File;

/**
 *
 * @author philb
 */
public class OpenAlbumAction extends AbstractAction<AlbumModel> {

    private final File albumFile;

    public OpenAlbumAction(File albumFile) {
        this.albumFile = albumFile;
    }

    @Override
    protected AlbumModel doAction() throws Exception {
        AlbumReader reader = new AlbumReader(albumFile);
        return reader.read();
    }

}
