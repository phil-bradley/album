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
    private final List<PageEntryModel> images = new ArrayList<>();

    public PageModel(PageViewLayout layout) {
        this.layout = layout;
    }

    public List<PageEntryModel> getImages() {
        return Collections.unmodifiableList(images);
    }

    public void addImage(File file) {
        this.images.add(new PageEntryModel(file));
    }

    public void addImage(PageEntryModel image) {
        this.images.add(image);
    }

    public PageViewLayout getLayout() {
        return layout;
    }

    public void setLayout(PageViewLayout layout) {
        this.layout = layout;
    }

    @Override
    public String toString() {
        return "PageModel{" + "layout=" + layout + ", images=" + images + '}';
    }

}
