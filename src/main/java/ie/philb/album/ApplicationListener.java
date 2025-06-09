/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album;

import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import ie.philb.album.view.PageEntryView;
import ie.philb.album.view.PageView;
import java.io.File;

/**
 *
 * @author Philip.Bradley
 */
public interface ApplicationListener {

    void pageEntrySelected(PageView pageView, PageEntryView view);

    void pageSelected(PageView view);

    void libraryImageSelected(ImageLibraryEntry entry);

    void browseLocationUpdated(File file);

    void albumUpdated();
}
