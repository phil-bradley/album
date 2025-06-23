/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author philb
 */
public class AlbumModel {

    private File file = null;
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

        if (page.getPageSize() != pageSize) {
            throw new IllegalArgumentException("Cannot create page of size " + page.getPageSize() + " in album of size " + pageSize);
        }

        page.setPageId(pages.size() + 1);
        this.pages.add(page);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
