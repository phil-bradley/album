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
import ie.philb.album.view.PageView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philip.Bradley
 */
public enum AppContext implements ApplicationListener {

    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(AppContext.class);

    private final List<ApplicationListener> applicationListeners = new ArrayList<>();
    private AlbumModel albumModel = new AlbumModel(PageSize.A4_Landscape);
    private File browseLocation = FileUtils.getHomeDirectory();
    private PageView selectedPageView = null;
    private PageEntryView selectedPageEntryView = null;

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

    public PageView getSelectedPageView() {
        return selectedPageView;
    }

    public PageEntryView getSelectedPageEntryView() {
        return selectedPageEntryView;
    }

    @Override
    public void pageEntrySelected(PageView pageView, PageEntryView view) {
        this.selectedPageEntryView = view;
        pageSelected(pageView);

        getApplicationListenersCopy().forEach(appListener -> {
            appListener.pageEntrySelected(pageView, view);
        });
    }

    @Override
    public void pageSelected(PageView view) {
        this.selectedPageView = view;
        getApplicationListenersCopy().forEach(appListener -> {
            appListener.pageSelected(view);
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

        LOG.info("Setting browse location to " + file.getAbsolutePath());
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
