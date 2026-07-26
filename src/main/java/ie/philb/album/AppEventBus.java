/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album;

import ie.philb.album.model.PageEntryModel;
import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import ie.philb.album.view.PageEntryView;
import ie.philb.album.view.PageView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author philb
 */
public class AppEventBus {

    private final List<ApplicationListener> applicationListeners = new ArrayList<>();

    public void addListener(ApplicationListener l) {
        this.applicationListeners.add(l);
    }

    public void removeListener(ApplicationListener l) {
        this.applicationListeners.remove(l);
    }

    public List<ApplicationListener> listeners() {
        return Collections.unmodifiableList(applicationListeners);
    }

    public void libraryImageSelected(ImageLibraryEntry entry) {
        getApplicationListenersCopy().forEach(appListener -> {
            appListener.libraryImageSelected(entry);
        });
    }

    public void albumUpdated() {

        getApplicationListenersCopy().forEach(appListener -> {
            appListener.albumUpdated();
        });
    }

    public void pageEntryUpdated(PageEntryModel pem) {
        getApplicationListenersCopy().forEach(appListener -> {
            appListener.pageEntryUpdated(pem);
        });
    }

    private List<ApplicationListener> getApplicationListenersCopy() {
        return new ArrayList<>(applicationListeners);
    }

    public void pageNavigatedTo(long pageId) {
        getApplicationListenersCopy().forEach(appListener -> {
            appListener.pageNavigatedTo(pageId);
        });
    }
    
    public void pageEntrySelected(PageView pageView, PageEntryView pageEntryView) {
        pageSelected(pageView);

        getApplicationListenersCopy().forEach(appListener -> {
            appListener.pageEntrySelected(pageView, pageEntryView);
        });
    }

    public void pageSelected(PageView view) {
        getApplicationListenersCopy().forEach(appListener -> {
            appListener.pageSelected(view);
        });
    }
}
