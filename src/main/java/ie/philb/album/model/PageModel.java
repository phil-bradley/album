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

    private int pageId;
    private int marginMillis = 10;
    private PageGeometry geometry;
    private final List<PageEntryModel> pageEntries = new ArrayList<>();
    private final PageSize pageSize;

    public PageModel(PageGeometry geometry, PageSize pageSize) {
        this.pageSize = pageSize;
        setGeometry(geometry);
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
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

        PageEntryModel pem = pageEntries.get(index);
        pem.setImageFile(file);

    }

    public final void setGeometry(PageGeometry geometry) {

        List<PageEntryModel> oldEntries = new ArrayList<>();
        oldEntries.addAll(getPageEntries());

        this.geometry = geometry;
        this.pageEntries.clear();

        int i = 0;

        for (PageCell cell : geometry.getCells()) {
            PageEntryModel pem = new PageEntryModel(cell);

            if (i < oldEntries.size()) {
                PageEntryModel oldEntry = oldEntries.get(i);
                pem.setImageFile(oldEntry.getImageFile());
                pem.setZoomFactor(oldEntry.getZoomFactor());
            }

            pageEntries.add(pem);
            i++;
        }
    }

    public int getMarginMillis() {
        return marginMillis;
    }

    public void setMarginMillis(int marginMillis) {
        this.marginMillis = marginMillis;
    }

    public PageModel withMarginMillis(int margin) {
        setMarginMillis(margin);
        return this;
    }
}
