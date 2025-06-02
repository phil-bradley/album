/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.AppContext;
import ie.philb.album.model.PageCell;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.common.ImagePanel;
import ie.philb.album.ui.common.Resources;
import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

/**
 *
 * @author philb
 */
public class PageEntryView extends AppPanel {

    private final PageEntryModel pageEntryModel;
    private final ImagePanel imagePanel;
    private boolean isSelected = false;

    public PageEntryView(PageEntryModel entryModel) {

        super();
        gridbag();

        this.pageEntryModel = entryModel;
        imagePanel = new ImagePanel();

        GridBagCellConstraints gbc = new GridBagCellConstraints(0, 0).fillBoth().weight(1);
        add(imagePanel, gbc);

        updateBorder();
        imagePanel.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        setSelected(!isSelected);
    }

    private void updateBorder() {
        setBorder(BorderFactory.createLineBorder(getBorderColor()));
    }

    private Color getBorderColor() {
        return isSelected ? Resources.COLOR_PHOTO_BORDER_SELECTED : Resources.COLOR_PHOTO_BORDER;
    }

    public void setImage(ImageIcon image) {
        this.pageEntryModel.setImageIcon(image);
        this.imagePanel.setIcon(image);
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
        this.imagePanel.zoomIn();
    }

    public void zoomOut() {
        this.imagePanel.zoomOut();
    }
}
