/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.Context;
import ie.philb.album.model.PageModel;
import ie.philb.album.ui.common.AppPanel;
import static ie.philb.album.ui.resources.Colors.COLOUR_ALBUM_BACKGROUND;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author philb
 */
public class AlbumView extends AppPanel {

    private final List<PageView> pageViews = new ArrayList<>();
    private boolean isPreviewMode = false;

    public AlbumView(Context context) {
        super(context);

        background(COLOUR_ALBUM_BACKGROUND);
        setLayout(null);

        refreshAlbum();
    }

    // TODO This should not exist
    public final void refreshAlbum() {

        clearPages();

        if (context.session().getAlbumModel() == null) {
            return;
        }

        addPages();
        positionPages();

        PageView selectedPageView = context.session().getSelectedPageView();
        PageEntryView selectedPageEntryView = context.session().getSelectedPageEntryView();

        updateSelectedEntry(selectedPageView, selectedPageEntryView);

        repaint();
        revalidate();
        repaint();
    }

    private void addPages() {

        for (PageModel page : context.session().getAlbumModel().getPages()) {
            PageView pageView = new PageView(context, page);
            this.pageViews.add(pageView);
            add(pageView);
        }

        setPreviewMode(isPreviewMode);
    }

    private void clearPages() {
        for (PageView pageView : pageViews) {
            remove(pageView);
        }

        pageViews.clear();
    }

    public void positionPages() {

        if (!canPosition()) {
            return;
        }

        int insetSize = 10;
        int parentHeight = getParent().getHeight();

        int pageHeight = parentHeight - (insetSize * 2);
        int pageWidth = context.session().getAlbumModel().getPageSize().widthFromHeight(pageHeight);

        int horizontalInset = insetSize;

        for (PageView pageView : pageViews) {
            pageView.setWidth(pageWidth);
            pageView.setBounds(horizontalInset, insetSize, pageView.getWidth(), pageView.getHeight());
            horizontalInset += pageWidth + insetSize;

            pageView.positionEntries();
        }

        setPreferredSize(new Dimension(horizontalInset, parentHeight));
        //LOG.info("Resized: Page container has size {}x{}, Page has size {}x{}", horizontalInset, parentHeight, pageWidth, pageHeight);

    }

    private boolean canPosition() {

        if (context.session().getAlbumModel() == null) {
            return false;
        }

        if (getParent() == null) {
            return false;
        }

        if (getParent().getWidth() == 0) {
            return false;
        }

        if (getParent().getHeight() == 0) {
            return false;
        }

        return true;
    }

    public void setPreviewMode(boolean previewMode) {
        this.isPreviewMode = previewMode;

        for (PageView pageView : pageViews) {
            pageView.setPreviewMode(isPreviewMode);
        }
    }

    private void updateSelectedEntry(PageView selectedPageView, PageEntryView selectedPageEntryView) {

        if (selectedPageView == null || selectedPageEntryView == null) {
            return;
        }

        selectedPageView.pageEntrySelected(selectedPageView, selectedPageEntryView);
    }

    public PageView getPageViewById(long pageId) {

        for (PageView pageView : pageViews) {
            if (pageView.getPageModel().getPageId() == pageId) {
                return pageView;
            }
        }

        return null;
    }
}
