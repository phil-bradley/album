/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album;

import ie.philb.album.ui.imagelibrary.ImageEntrySelectionListener;
import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import ie.philb.album.ui.page.PageLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Philip.Bradley
 */
public enum AppContext {

    INSTANCE;

    private final List<AppListener> listeners = new ArrayList<>();
    private final List<PageLayout> pageLayouts = new ArrayList<>();

    private ImageEntrySelectionListener listener;

    public void addListener(AppListener l) {
        this.listeners.add(l);
    }

    public void removeListener(AppListener l) {
        this.listeners.remove(l);
    }

    public void imageSelected(ImageLibraryEntry entry) {
        if (listener != null) {
            listener.imageSelected(entry);
        }
    }

    public void setImageEntryListener(ImageEntrySelectionListener imageEntryListener) {
        this.listener = imageEntryListener;

        for (AppListener l : listeners) {
            l.listenerSelected(imageEntryListener);
        }
    }

    public void setPageLayouts(List<PageLayout> l) {
        this.pageLayouts.clear();
        this.pageLayouts.addAll(l);
    }

    public List<PageLayout> getPageLayouts() {
        return Collections.unmodifiableList(pageLayouts);
    }

}
