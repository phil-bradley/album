/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.AppContext;
import ie.philb.album.model.PageCell;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageEntryModelListener;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.Resources;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author philb
 */
public class PageEntryView extends AppPanel implements PageEntryModelListener {

    private static final Logger LOG = LoggerFactory.getLogger(PageEntryView.class);

    private final PageEntryModel pageEntryModel;
    private boolean isSelected = false;

    public PageEntryView(PageEntryModel entryModel) {

        super();
        setFocusable(true);
        this.pageEntryModel = entryModel;
        this.pageEntryModel.addListener(this);
        updateBorder();
    }

    @Override
    protected void paintComponent(Graphics g) {

        if (pageEntryModel.getImageIcon() == null) {
            return;
        }

        drawImage(g);
    }

    private void drawImage(Graphics g) {

        Dimension viewSize = new Dimension(getBounds().width, getBounds().height);
        BufferedImage viewImage = pageEntryModel.getViewImage(viewSize);

        // Centre image if it's less tall or less wide than the available space
        int x = (getAvailableWidth() - viewImage.getWidth()) / 2;
        int y = (getAvailableHeight() - viewImage.getHeight()) / 2;

        g.drawImage(viewImage, x, y, null);
    }

    private int getAvailableWidth() {
        int availableWidth = getBounds().width;
        return availableWidth;
    }

    private int getAvailableHeight() {
        int availableHeight = getBounds().height;
        return availableHeight;
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        setSelected(!isSelected);
        repaint();
    }

    private void updateBorder() {
        setBorder(BorderFactory.createLineBorder(getBorderColor()));
        repaint();
    }

    private Color getBorderColor() {
        return isSelected ? Resources.COLOR_PHOTO_BORDER_SELECTED : Resources.COLOR_PHOTO_BORDER;
    }

    public void setSelected(boolean b) {
        this.isSelected = b;
        updateBorder();

        if (isSelected) {
            AppContext.INSTANCE.imageEntrySelected(this);
        }
    }

    public PageCell getPageCell() {
        return pageEntryModel.getCell();
    }

    public void zoomIn() {
        this.pageEntryModel.zoomIn();
    }

    public void zoomOut() {
        this.pageEntryModel.zoomOut();
    }

    @Override
    public void imageUpdated() {
        //LOG.info("Image updated on cell {} with view size {}, isVisible: {}", pageEntryModel.getCell(), getSize(), isVisible());
        repaint();
    }
}
