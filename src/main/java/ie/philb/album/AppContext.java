/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album;

import ie.philb.album.ui.imagelibrary.ImageEntrySelectionListener;
import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philip.Bradley
 */
public enum AppContext {

    INSTANCE;

    private final List<AppListener> listeners = new ArrayList<>();

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

}
