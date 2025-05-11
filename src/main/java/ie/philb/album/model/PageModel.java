/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.model;

import ie.philb.album.view.PageViewLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author philb
 */
public class PageModel {

    private PageViewLayout layout;
    private final List<ImageModel> images = new ArrayList<>();

    public PageModel(PageViewLayout layout) {
        this.layout = layout;
    }

    public List<ImageModel> getImages() {
        return Collections.unmodifiableList(images);
    }

    public void addImage(ImageModel image) {
        this.images.add(image);
    }

    public PageViewLayout getLayout() {
        return layout;
    }

    public void setLayout(PageViewLayout layout) {
        this.layout = layout;
    }

}
