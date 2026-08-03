/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album;

import ie.philb.album.model.AlbumModel;
import ie.philb.album.view.PageEntryView;
import ie.philb.album.view.PageView;

/**
 *
 * @author philb
 */
public class AppSession {

    private AlbumModel albumModel = null;
    private PageView selectedPageView = null;
    private PageEntryView selectedPageEntryView = null;

    private final AppEventBus eventBus;

    public AppSession(AppEventBus eventBus) {
        this.eventBus = eventBus;

    }

    public void setAlbumModel(AlbumModel model) {
        this.albumModel = model;
        this.selectedPageView = null;
        this.selectedPageEntryView = null;
        this.eventBus.albumUpdated();
    }

    public AlbumModel getAlbumModel() {
        return albumModel;
    }

    public PageView getSelectedPageView() {
        return selectedPageView;
    }

    public PageEntryView getSelectedPageEntryView() {
        return selectedPageEntryView;
    }

    public void pageEntrySelected(PageView pageView, PageEntryView pageEntryView) {
        this.selectedPageView = pageView;
        this.selectedPageEntryView = pageEntryView;
        this.eventBus.pageEntrySelected(pageView, pageEntryView);
    }

    public void pageSelected(PageView view) {
        this.selectedPageView = view;
        this.selectedPageEntryView = null;
        this.eventBus.pageSelected(view);
    }

    public void addListener(ApplicationListener listener) {
        eventBus.addListener(listener);
    }

    public void removeListener(ApplicationListener listener) {
        eventBus.removeListener(listener);
    }
    
    public AppEventBus getEventBus() {
        return eventBus;
    }
}
