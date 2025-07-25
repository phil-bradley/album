/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.AppContext;
import ie.philb.album.model.PageCell;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageEntryType;
import ie.philb.album.model.PageGeometryMapper;
import ie.philb.album.model.PageModel;
import ie.philb.album.model.PageModelListener;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.resources.Colors;
import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
public class PageView extends AppPanel implements PageModelListener {

    private static final Logger LOG = LoggerFactory.getLogger(PageView.class);

    protected List<PageEntryView> pageEntryViews;
    protected boolean isSelected = false;
    protected PageEntryView selectedPageEntryView = null;
    private PageModel model;
    private boolean isPreviewMode = false;

    public PageView(PageModel model) {
        setModel(model);
        background(Colors.COLOUR_ALBUM_PAGE_BACKGROUND);
        setLayout(null);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                for (PageEntryView pev : pageEntryViews) {
                    pev.setPhysicalToViewScalingFactor(getPhysicalToViewScalingFactor());
                }
                revalidate();
            }
        });
    }

    public PageModel getPageModel() {
        return model;
    }

    @Override
    public void pageEntrySelected(PageView pageView, PageEntryView view) {
        updateSelectedEntryView(pageView, view);
    }

    private void updateSelectedEntryView(PageView selectedPageView, PageEntryView selectedPageEntryView) {

        clearSelection();
        pageSelected(selectedPageView);

        if (selectedPageEntryView == null) {
            return;
        }

        boolean pageMatches = (selectedPageView.getPageModel().getPageId() == this.getPageModel().getPageId());
        setSelected(pageMatches);

        for (PageEntryView pageEntryView : pageEntryViews) {
            PageCell selectedCell = selectedPageEntryView.getPageCell();
            boolean cellMatches = pageEntryView.getPageCell().equals(selectedCell);

            if (pageMatches && cellMatches) {
                pageEntryView.setSelected(true);
                this.selectedPageEntryView = pageEntryView;
            }
        }

        repaint();
    }

    private void clearSelection() {
        this.selectedPageEntryView = null;
        setSelected(false);
        pageEntryViews.forEach(pev -> setSelected(false));
    }

    private int getSelectedIndex() {

        int selectedIndex = 0;
        for (PageEntryView view : pageEntryViews) {
            if (view.equals(selectedPageEntryView)) {
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

        if (selectedPageEntryView.getPageEntryModel().getPageEntryType() != PageEntryType.Image) {
            return;
        }

        model.setImage(imageLibraryEntry.getFile(), selectedIdx);
        selectedPageEntryView.setSelected(true);
        selectedPageEntryView.centerImage();

        AppContext.INSTANCE.pageEntrySelected(selectedPageEntryView.getPageView(), selectedPageEntryView);
    }

    private void setModel(PageModel model) {
        this.model = model;
        this.pageEntryViews = new ArrayList<>(model.getCellCount());
        this.model.addListener(this);
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

    private void createEntries() {

        int entryCount = model.getGeometry().getCells().size();

        pageEntryViews.clear();

        for (int i = 0; i < entryCount; i++) {
            PageEntryModel pem = model.getPageEntries().get(i);
            PageEntryView pageEntryView = new PageEntryView(this, pem);

            pageEntryViews.add(pageEntryView);
            add(pageEntryView);

        }

        positionEntries();
    }

    public void positionEntries() {

        if (getWidth() == 0) {
            return;
        }

        PageGeometryMapper geometryMapper = new PageGeometryMapper(model, getSize());

        for (PageEntryView pageEntryView : pageEntryViews) {

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

        for (PageEntryView view : pageEntryViews) {
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
        return isSelected ? Colors.COLOR_PHOTO_BORDER_SELECTED : Colors.COLOR_PHOTO_BORDER;
    }

    @Override
    public void pageSelected(PageView selectedView) {
        clearSelection();

        if (selectedView != null) {
            boolean pageSelected = (this.model.getPageId() == selectedView.getPageModel().getPageId());
            setSelected(pageSelected);
        }
    }

    @Override
    public void pageUpdated(PageModel model) {

        positionEntries();

        revalidate();
        repaint();
    }

    private double getPhysicalToViewScalingFactor() {

        double viewWidth = getSize().width;

        if (viewWidth == 0) {
            return 0;
        }

        double physicalWidth = model.getPageSize().width();

        // No scaling set, assume 1:1
        if (physicalWidth == 0) {
            return 1;
        }

        return viewWidth / physicalWidth;
    }
}
