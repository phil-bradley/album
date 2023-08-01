/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author philipb
 */
public class Album {

    private final List<Page> pages = new ArrayList<>();

    public Album() {

    }

    public List<Page> getPages() {
        return Collections.unmodifiableList(pages);
    }

    public void createPage(PageLayout layout) {
        Page page = new Page(layout);
        pages.add(page);
    }
    
    public void clearPages() {
        pages.clear();
    }
        
}
