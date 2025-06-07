/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageModel;
import ie.philb.album.ui.common.AppPanel;
import static ie.philb.album.ui.common.Resources.COLOUR_ALBUM_BACKGROUND;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author philb
 */
public class AlbumView extends AppPanel {

    private static final Logger LOG = LoggerFactory.getLogger(AlbumView.class);

    private AlbumModel albumModel;
    private final List<PageView> pageViews = new ArrayList<>();
    private boolean isPreviewMode = false;

    public AlbumView(AlbumModel albumModel) {
        this.albumModel = albumModel;

        background(COLOUR_ALBUM_BACKGROUND);
        setLayout(null);

        setModel(albumModel);
    }

    public final void setModel(AlbumModel model) {

        this.albumModel = model;

        clearPages();
        addPages();
        positionPages();

        repaint();
        revalidate();
        repaint();
    }

    private void addPages() {

        for (PageModel page : albumModel.getPages()) {
            PageView pageView = new PageView(page);
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
        int pageWidth = albumModel.getPageSize().widthFromHeight(pageHeight);

        int horizontalInset = insetSize;

        for (PageView pageView : pageViews) {
            pageView.setWidth(pageWidth);
            pageView.setBounds(horizontalInset, insetSize, pageView.getWidth(), pageView.getHeight());
            horizontalInset += pageWidth + insetSize;

            pageView.positionEntries();
        }

        setPreferredSize(new Dimension(horizontalInset, parentHeight));
        LOG.info("Resized: Page container has size {}x{}, Page has size {}x{}", horizontalInset, parentHeight, pageWidth, pageHeight);

    }

    private boolean canPosition() {

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
}
