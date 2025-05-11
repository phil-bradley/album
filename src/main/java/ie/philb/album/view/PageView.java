/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.model.PageModel;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.Resources;
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

    private List<PageEntryView> entries;
    private boolean isPageSelected = false;
    private PageEntryView selectedEntry = null;
    private PageModel model;

    public PageView(PageModel model) {
        setModel(model);
        background(Resources.COLOUR_ALBUM_BACKGROUND);
//        setLayout(new GridLayout(model.getLayout().getX(), model.getLayout().getY()));
    }

    public final void setModel(PageModel model) {
        this.model = model;
        this.entries = new ArrayList<>(model.getLayout().entryCount());

        createEntries();
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

    private void createEntries() {

        int entryCount = getPageLayout().getX() * getPageLayout().getY();

        for (int i = 0; i < entryCount; i++) {
            PageEntryView pageEntryView = new PageEntryView();
            entries.add(pageEntryView);

        }

        layoutEntries();

    }

    private void layoutEntries() {

        for (PageEntryView pageEntryView : entries) {
            add(pageEntryView);
        }

    }

}
