/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.AppContext;
import ie.philb.album.model.PageCell;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageGeometryMapper;
import ie.philb.album.model.PageModel;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.Resources;
import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a page in the book with 1 or more image entries The layout of the
 * page is determined by the PageGeometry
 *
 * @author Philip.Bradley
 */
public class PageView extends AppPanel {

    private static final Logger LOG = LoggerFactory.getLogger(PageView.class);

    private List<PageEntryView> pageEntriesViews;
    private boolean isSelected = false;
    private PageEntryView selectedEntryView = null;
    private PageModel model;
    private boolean isPreviewMode = false;

    public PageView(PageModel model) {
        setModel(model);
        background(Resources.COLOUR_ALBUM_PAGE_BACKGROUND);
        setLayout(null);
    }

    public PageModel getPageModel() {
        return model;
    }

    @Override
    public void pageEntrySelected(PageView pageView, PageEntryView view) {
        updateSelectedEntryView(pageView, view);
    }

    private void updateSelectedEntryView(PageView selectedPageView, PageEntryView selectedPageEntryView) {

        pageSelected(selectedPageView);

        if (selectedPageEntryView == null) {
            return;
        }

        boolean pageMatches = (selectedPageView.getPageModel().getPageId() == this.getPageModel().getPageId());
        setSelected(pageMatches);

        for (PageEntryView pageEntryView : pageEntriesViews) {
            PageCell selectedCell = selectedPageEntryView.getPageCell();
            boolean cellMatches = pageEntryView.getPageCell().equals(selectedCell);

            if (pageMatches && cellMatches) {
                pageEntryView.setSelected(true);
                this.selectedEntryView = pageEntryView;
            }
        }

        repaint();
    }

    private void clearSelection() {
        this.selectedEntryView = null;
        setSelected(false);
        pageEntriesViews.forEach(pev -> setSelected(false));
    }

    private int getSelectedIndex() {

        int selectedIndex = 0;
        for (PageEntryView view : pageEntriesViews) {
            if (view.equals(selectedEntryView)) {
                return selectedIndex;
            }

            selectedIndex++;
        }

        return -1;
    }

    @Override
    public void libraryImageSelected(ImageLibraryEntry imageLibraryEntry) {

        int selectedIdx = getSelectedIndex();

        if (selectedIdx == -1) {
            return;
        }

        if (imageLibraryEntry == null) {
            return;
        }

        model.setImage(imageLibraryEntry.getFile(), selectedIdx);
        selectedEntryView.setSelected(true);
        selectedEntryView.centerImage();

        AppContext.INSTANCE.pageEntrySelected(selectedEntryView.getPageView(), selectedEntryView);
    }

    public final void setModel(PageModel model) {
        this.model = model;
        this.pageEntriesViews = new ArrayList<>(model.getCellCount());

        createEntries();
    }

    public void setHeight(int height) {
        int width = model.getPageSize().widthFromHeight(height);
        setSize(width, height);
    }

    public void setWidth(int width) {
        int height = model.getPageSize().heigthFromWidth(width);
        setSize(width, height);
    }

    @Override
    public void setSize(int width, int height) {
        //LOG.info("Setting size to {}x{}", width, height);
        super.setSize(width, height);
    }

    private void createEntries() {

        int entryCount = model.getGeometry().getCells().size();

        pageEntriesViews.clear();

        for (int i = 0; i < entryCount; i++) {
            PageEntryModel pem = model.getPageEntries().get(i);
            PageEntryView pageEntryView = new PageEntryView(this, pem);

            pageEntriesViews.add(pageEntryView);
            add(pageEntryView);

        }

        positionEntries();
    }

    public void positionEntries() {

        if (getWidth() == 0) {
            return;
        }

        PageGeometryMapper geometryMapper = new PageGeometryMapper(model, getSize());

        for (PageEntryView pageEntryView : pageEntriesViews) {

            PageEntryModel pageEntryModel = pageEntryView.getPageEntryModel();
            Dimension size = geometryMapper.getCellSizeOnView(pageEntryModel);
            Point location = geometryMapper.getCellLocationOnView(pageEntryView.getPageCell());

            pageEntryView.setPreferredSize(size);
            pageEntryView.setSize(size);

            pageEntryView.setBounds(location.x, location.y, size.width, size.height);
        }
    }

    public void setPreviewMode(boolean previewMode) {
        this.isPreviewMode = previewMode;

        for (PageEntryView view : pageEntriesViews) {
            view.setPreviewMode(isPreviewMode);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {

        if (isPreviewMode) {
            return;
        }

        AppContext.INSTANCE.pageSelected(this);
    }

    private void setSelected(boolean b) {

        if (isPreviewMode) {
            return;
        }

        this.isSelected = b;
        updateBorder();
    }

    private void updateBorder() {
        setBorder(BorderFactory.createLineBorder(getBorderColor()));
        repaint();
    }

    private Color getBorderColor() {
        return isSelected ? Resources.COLOR_PHOTO_BORDER_SELECTED : Resources.COLOR_PHOTO_BORDER;
    }

    @Override
    public void pageSelected(PageView selectedView) {
        clearSelection();

        if (selectedView != null) {
            boolean pageSelected = (this.model.getPageId() == selectedView.getPageModel().getPageId());
            setSelected(pageSelected);
        }
    }
}
