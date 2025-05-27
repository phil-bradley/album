/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album;

import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageSize;
import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import ie.philb.album.view.PageEntryView;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philip.Bradley
 */
public enum AppContext implements ApplicationListener {

    INSTANCE;

    private final List<ApplicationListener> applicationListeners = new ArrayList<>();
    private final AlbumModel albumModel = new AlbumModel(PageSize.A4_Landscape);

    public void addListener(ApplicationListener l) {
        this.applicationListeners.add(l);
    }

    public void removeListener(ApplicationListener l) {
        this.applicationListeners.remove(l);
    }

    public AlbumModel getAlbumModel() {
        return albumModel;
    }

    @Override
    public void imageEntrySelected(PageEntryView view) {
        applicationListeners.forEach(appListener -> {
            appListener.imageEntrySelected(view);
        });
    }

    @Override
    public void libraryImageSelected(ImageLibraryEntry entry) {
        applicationListeners.forEach(appListener -> {
            appListener.libraryImageSelected(entry);
        });
    }
}
