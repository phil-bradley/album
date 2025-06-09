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
 * @author philb
 */
public class ApplicationAdapter implements ApplicationListener {

    @Override
    public void pageEntrySelected(PageView pageView, PageEntryView view) {
    }

    @Override
    public void libraryImageSelected(ImageLibraryEntry entry) {
    }

    @Override
    public void browseLocationUpdated(File file) {
    }

    @Override
    public void albumUpdated() {
    }

    @Override
    public void pageSelected(PageView view) {
    }

}
