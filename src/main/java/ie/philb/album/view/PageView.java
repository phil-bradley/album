/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.AppContext;
import ie.philb.album.ApplicationAdapter;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageModel;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.Resources;
import ie.philb.album.ui.pagesizer.IsoPageSizer;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a page in the book with 1 or more image entries in a M x N
 * arrangement
 *
 * @author Philip.Bradley
 */
public class PageView extends AppPanel {

    private static final Logger LOG = LoggerFactory.getLogger(PageView.class);

    private List<PageEntryView> pageEntriesViews;
    private boolean isPageSelected = false;
//    private PageEntryView selectedEntry = null;
    private PageModel model;

    public PageView(PageModel model) {
        LOG.info("Got page model " + model);
        setModel(model);
        background(Resources.COLOUR_ALBUM_BACKGROUND);
        setLayout(null);

        AppContext.INSTANCE.addListener(new ApplicationAdapter() {
            @Override
            public void imageEntrySelected(PageEntryView view) {
                clearDeselectedViews(view);
            }
        });
    }

    private void clearDeselectedViews(PageEntryView selectedView) {

        for (PageEntryView pageEntryView : pageEntriesViews) {
            if (!pageEntryView.equals(selectedView)) {
                pageEntryView.setSelected(false);

            }
        }
    }

    public final void setModel(PageModel model) {
        this.model = model;
        this.pageEntriesViews = new ArrayList<>(model.getLayout().entryCount());

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

        int entryCount = getPageLayout().getRows() * getPageLayout().getColumns();

        LOG.info("Creating {} entries", entryCount);

        for (int i = 0; i < entryCount; i++) {
            PageEntryView pageEntryView = new PageEntryView();
            pageEntriesViews.add(pageEntryView);
            add(pageEntryView);

        }

        positionEntries();
        populateEntries();

    }

    public void positionEntries() {

        if (getWidth() == 0) {
            return;
        }

        double millisToPx = (double) (getPageLayout().getPageSpecification().width()) / (double) getWidth();
        LOG.info("Page has width {} in px, {} in millis, got scaling factor {}", getWidth(), getPageLayout().getPageSpecification().width(), millisToPx);

        List<PageEntryCoordinates> coordinates = getPageLayout().getEntryCoordinates();

        int i = 0;

        for (PageEntryView pageEntryView : pageEntriesViews) {
            PageEntryCoordinates entryCoordinates = coordinates.get(i);
            LOG.info("Got coordinates for entry {}: {}", i, coordinates);

            PageEntryCoordinates scaledCoordinates = scaleCoordinates(entryCoordinates, millisToPx);
            LOG.info("Got scaled coordinates for entry {}: {}", i, scaledCoordinates);

            pageEntryView.setPreferredSize(new Dimension(scaledCoordinates.width(), scaledCoordinates.height()));
            pageEntryView.setSize(pageEntryView.getPreferredSize());

            pageEntryView.setBounds(scaledCoordinates.offsetX(), scaledCoordinates.offsetY(), scaledCoordinates.width(), scaledCoordinates.height());
            i++;
        }
    }

    private PageEntryCoordinates scaleCoordinates(PageEntryCoordinates coordinates, double scale) {
        PageEntryCoordinates scaled = new PageEntryCoordinates(
                (int) (coordinates.offsetX() / scale),
                (int) (coordinates.offsetY() / scale),
                (int) (coordinates.width() / scale),
                (int) (coordinates.height() / scale)
        );

        return scaled;
    }

    private void populateEntries() {

        int i = 0;
        for (PageEntryModel pem : model.getPageEntries()) {
            PageEntryView view = pageEntriesViews.get(i);
            view.setModel(pem);
            i++;
        }
    }
}
