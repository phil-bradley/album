/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author philb
 */
public class AlbumModel {

    private final List<PageModel> pages = new ArrayList();

    public List<PageModel> getPages() {
        return Collections.unmodifiableList(pages);
    }

    public void addPage(PageModel page) {
        this.pages.add(page);
    }
}
