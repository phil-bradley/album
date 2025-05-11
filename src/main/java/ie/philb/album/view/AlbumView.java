/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageModel;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.pagesizer.IsoPageSizer;
import java.awt.Color;
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

    private static final Logger logger = LoggerFactory.getLogger(AlbumView.class);

    private final AlbumModel albumModel;
    private final List<PageView> pageViews = new ArrayList<>();

    public AlbumView(AlbumModel albumModel) {
        this.albumModel = albumModel;

        background(Color.GRAY);
        setLayout(null);

        for (PageModel page : albumModel.getPages()) {
            this.pageViews.add(new PageView(page));
        }

        for (PageView pageView : pageViews) {
            add(pageView);
        }

        revalidate();
        repaint();
    }

    public void positionPages() {

        int defaultInset = 10;
        int parentWidth = getParent().getWidth();
        int parentHeight = getParent().getHeight();

        IsoPageSizer ips = new IsoPageSizer();

        int maxPageWidth = parentWidth - defaultInset * 2;
        int maxPageHeight = parentHeight - defaultInset * 2;

        int pageWidth = Math.min(maxPageWidth, ips.getWidthFromHeight(maxPageHeight));
        int pageHeight = Math.min(maxPageHeight, ips.getHeightFromWidth(maxPageWidth));

        int horizontalInset = defaultInset;
        int verticalInset = (parentHeight - pageHeight) / 2;

        for (PageView pageView : pageViews) {
            pageView.setWidth(pageWidth);
            pageView.setBounds(horizontalInset, verticalInset, pageView.getWidth(), pageView.getHeight());
            horizontalInset += pageWidth + defaultInset;
        }

        setPreferredSize(new Dimension(horizontalInset, parentHeight));
        logger.info("Resized: Page container has size {}x{}, Page has size {}x{}", horizontalInset, parentHeight, pageWidth, pageHeight);

    }
}
