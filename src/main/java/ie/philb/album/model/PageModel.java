/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import ie.philb.album.view.PageViewLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author philb
 */
public class PageModel {

    private PageViewLayout layout;
    private List<PageEntryModel> pageEntries;

    public PageModel(PageViewLayout layout) {
        setLayout(layout);
    }

    public List<PageEntryModel> getPageEntries() {
        return Collections.unmodifiableList(pageEntries);
    }

    public void setImage(File file, int index) {

        if (index >= layout.entryCount()) {
            throw new IllegalArgumentException("Cannot add image at position " + index + " with page entry count = " + layout.entryCount());
        }

        pageEntries.set(index, new PageEntryModel(file));
    }

    public final PageViewLayout getLayout() {
        return layout;
    }

    public final void setLayout(PageViewLayout layout) {
        this.layout = layout;
        this.pageEntries = new ArrayList<>(getLayout().entryCount());

        for (int i = 0; i < getLayout().entryCount(); i++) {
            pageEntries.add(null);
        }
    }

    @Override
    public String toString() {
        return "PageModel{" + "layout=" + layout + ", images=" + pageEntries + '}';
    }

}
