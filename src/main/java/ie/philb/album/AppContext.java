/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album;

import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philip.Bradley
 */
public enum AppContext {
    
    INSTANCE;
    
    //private static final AppContext INSTANCE = new AppContext();
    
//    private AppContext() {
//    }
//    
//    public static AppContext getInstance() {
//        return INSTANCE;
//    }
    
    private final List<AppListener> listeners = new ArrayList<>();
    
    public void addListener(AppListener l) {
        this.listeners.add(l);
    }
    
    public void removeListener(AppListener l) {
        this.listeners.remove(l);
    }
    
    public void imageSelected(ImageLibraryEntry entry) {
        for (AppListener l : listeners) {
            l.imageSelected(entry);
        }
    }
}
