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

    private final PageSize pageSize;
    private final List<PageModel> pages = new ArrayList();

    public AlbumModel(PageSize pageSize) {
        this.pageSize = pageSize;
    }

    public PageSize getPageSize() {
        return pageSize;
    }

    public List<PageModel> getPages() {
        return Collections.unmodifiableList(pages);
    }

    public void addPage(PageModel page) {
        this.pages.add(page);
    }
}
