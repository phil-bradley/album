/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.model.PageModel;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.pagesizer.IsoPageSizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a page in the book with 1 or more image entries in a M x N
 * arrangement
 *
 * @author Philip.Bradley
 */
public class PageView extends AppPanel {

    private final List<PageEntryView> entries;
    private boolean isPageSelected = false;
    private PageEntryView selectedEntry = null;
    private PageModel model;

    public PageView(PageModel model) {
        this.entries = new ArrayList<>(model.getLayout().entryCount());
    }

    public void setModel(PageModel model) {
        this.model = model;
    }

    public PageViewLayout getPageLayout() {
        return model.getLayout();
    }

    public void setHeight(int height) {
        setSize(new IsoPageSizer().getWidthFromHeight(height), height);
    }

    public void setWidth(int width) {
        setSize(width, new IsoPageSizer().getHeightFromWidth(width));
    }
}
