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
public class PageModel {

    private PageGeometry geometry;
    private final List<PageEntryModel> pageEntries = new ArrayList<>();
    private final PageSize pageSize;

    public PageModel(PageGeometry geometry, PageSize pageSize) {
        this.geometry = geometry;
        this.pageSize = pageSize;
    }

    public PageGeometry getGeometry() {
        return geometry;
    }

    public PageSize getPageSize() {
        return pageSize;
    }

    public List<PageEntryModel> getPageEntries() {
        return Collections.unmodifiableList(pageEntries);
    }

    public int getCellCount() {
        return geometry.getCells().size();
    }

    public void setImage(File file, int index) {

        if (index >= getCellCount()) {
            throw new IllegalArgumentException("Cannot add image at position " + index + " with page entry count = " + getCellCount());
        }

        pageEntries.set(index, new PageEntryModel(file));
    }

    public final void setGeometry(PageGeometry geometry) {
        this.geometry = geometry;
        this.pageEntries.clear();

        for (PageCell cell : geometry.getCells()) {
            pageEntries.add(null);
        }
    }

//    public final PageViewLayout getLayout() {
//        return layout;
//    }
//
//    public final void setLayout(PageViewLayout layout) {
//        this.layout = layout;
//        this.pageEntries = new ArrayList<>(getLayout().entryCount());
//
//        for (int i = 0; i < getLayout().entryCount(); i++) {
//            pageEntries.add(null);
//        }
//    }
}
