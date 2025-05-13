/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.AppContext;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.common.ImagePanel;
import ie.philb.album.ui.common.Resources;
import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;

/**
 *
 * @author philb
 */
public class PageEntryView extends AppPanel {

    private PageEntryModel pageEntryModel;
    private final ImagePanel imagePanel;
    private boolean isSelected = false;

    public PageEntryView() {

        super();
        gridbag();

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

//    private void showPlacholder() {
//        imagePanel.setIcon(new ImageIcon(this.getClass().getResource("/ie/philb/album/placeholder.png")));
//
//    }
    public void setModel(PageEntryModel model) {
        this.pageEntryModel = model;

        if (pageEntryModel != null) {
            this.imagePanel.setIcon(pageEntryModel.getImageIcon());
        }
    }

    public void setSelected(boolean b) {
        this.isSelected = b;
        updateBorder();

        if (isSelected) {
            AppContext.INSTANCE.imageEntrySelected(this);
        }
    }
}
