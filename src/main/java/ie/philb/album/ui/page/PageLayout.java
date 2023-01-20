/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.page;

import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Philip.Bradley
 */
public class PageLayout {

    private final String name;
    private final PageSpecification pageSpecification;
    private final Insets insets = new Insets(0, 0, 0, 0);
    private final List<PageEntry> pageEntries = new ArrayList<>();

    public PageLayout(String name, PageSpecification pageSpecification, PageEntry... entries) {
        this.name = name;
        this.pageSpecification = pageSpecification;
        pageEntries.addAll(Arrays.asList(entries));
    }

    public String getName() {
        return name;
    }

    public PageSpecification getPageSpecification() {
        return pageSpecification;
    }

    public List<PageEntry> getPageEntries() {
        return pageEntries;
    }

    public Insets getInsets() {
        return insets;
    }

    public void inset(int margin) {
        this.insets.bottom = margin;
        this.insets.left = margin;
        this.insets.right = margin;
        this.insets.top = margin;        
    }

    @Override
    public String toString() {
        return "PageLayout{" + "name=" + name + ", pageSpecification=" + pageSpecification + ", insets=" + insets + ", pageEntries=" + pageEntries + '}';
    }

}
