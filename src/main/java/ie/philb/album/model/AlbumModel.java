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

    public PageSize getPageSize() {
        return pageSize;
    }

    public List<PageModel> getPages() {
        return Collections.unmodifiableList(pages);
    }

    public void addPage(PageGeometry geometry) {
        addPage(geometry, defaultMargin, defaultGutter);
    }

    public void addPage(PageGeometry geometry, int margin, int gutter) {
        addPage(new PageModel(geometry, getPageSize()).withMargin(margin).withGutter(gutter));
    }

    public void addPage(PageModel pageModel) {
        pageModel.setPageId(pages.size() + 1);
        this.pages.add(pageModel);
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
}
