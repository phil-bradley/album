/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album;

import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageSize;
import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import ie.philb.album.util.FileUtils;
import ie.philb.album.view.PageEntryView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philip.Bradley
 */
public enum AppContext implements ApplicationListener {

    INSTANCE;

    private final List<ApplicationListener> applicationListeners = new ArrayList<>();
    private AlbumModel albumModel = new AlbumModel(PageSize.A4_Landscape);
    private File browseLocation = FileUtils.getHomeDirectory();

    public void addListener(ApplicationListener l) {
        this.applicationListeners.add(l);
    }

    public void removeListener(ApplicationListener l) {
        this.applicationListeners.remove(l);
    }

    public void setAlbumModel(AlbumModel model) {
        this.albumModel = model;
        albumUpdated();
    }

    public AlbumModel getAlbumModel() {
        return albumModel;
    }

    public File getBrowseLocation() {
        return browseLocation;
    }

    @Override
    public void imageEntrySelected(PageEntryView view) {
        getApplicationListenersCopy().forEach(appListener -> {
            appListener.imageEntrySelected(view);
        });
    }

    @Override
    public void libraryImageSelected(ImageLibraryEntry entry) {
        getApplicationListenersCopy().forEach(appListener -> {
            appListener.libraryImageSelected(entry);
        });
    }

    @Override
    public void browseLocationUpdated(File file) {

        this.browseLocation = file;

        getApplicationListenersCopy().forEach(appListener -> {
            appListener.browseLocationUpdated(file);
        });
    }

    @Override
    public void albumUpdated() {

        getApplicationListenersCopy().forEach(appListener -> {
            appListener.albumUpdated();
        });
    }

    private List<ApplicationListener> getApplicationListenersCopy() {
        return new ArrayList<>(applicationListeners);
    }
}
