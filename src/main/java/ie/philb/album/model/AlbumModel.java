/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import java.io.File;
import java.time.LocalDateTime;
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
    private LocalDateTime lastSaveDate = null;
    private final int defaultMargin;
    private final int defaultGutter;

    public AlbumModel(PageSize pageSize, int margin, int gutter) {
        this.pageSize = pageSize;
        this.defaultMargin = margin;
        this.defaultGutter = gutter;
    }

    public int getDefaultMargin() {
        return defaultMargin;
    }

    public int getDefaultGutter() {
        return defaultGutter;
    }

    public PageSize getPageSize() {
        return pageSize;
    }

    public List<PageModel> getPages() {
        return Collections.unmodifiableList(pages);
    }

    public void addPage(int index, PageGeometry geometry) {
        addPage(index, geometry, defaultMargin, defaultGutter);
    }

    public void addPage(int index, PageGeometry geometry, int margin, int gutter) {
        addPage(index, new PageModel(geometry, getPageSize()).withMargin(margin).withGutter(gutter));
    }

    public void addPage(int index, PageModel pageModel) {
        pageModel.setPageId(index);
        this.pages.add(index, pageModel);
        renumberPages();
    }

    public void deletePage(int index) {
        this.pages.remove(index);
        renumberPages();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    LocalDateTime getLastChangeDate() {
        LocalDateTime lastChangeDate = LocalDateTime.MIN;

        for (PageModel pm : pages) {
            if (pm.getLastChangeDate().isAfter(lastChangeDate)) {
                lastChangeDate = pm.getLastChangeDate();
            }
        }

        return lastChangeDate;
    }

    public boolean hasUnSavedChanges() {
        return lastSaveDate == null || getLastChangeDate().isAfter(lastSaveDate);
    }

    public void setLastSaveDate(LocalDateTime saveDate) {
        this.lastSaveDate = saveDate;
    }

    private void renumberPages() {

        int idx = 0;
        for (PageModel pageModel : pages) {
            pageModel.setPageId(idx);
            idx++;
        }
    }
}
