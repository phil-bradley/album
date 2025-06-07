/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album;

import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import ie.philb.album.view.PageEntryView;
import java.io.File;

/**
 *
 * @author Philip.Bradley
 */
public interface ApplicationListener {

    void imageEntrySelected(PageEntryView view);

    void libraryImageSelected(ImageLibraryEntry entry);

    void browseLocationUpdated(File file);
}
