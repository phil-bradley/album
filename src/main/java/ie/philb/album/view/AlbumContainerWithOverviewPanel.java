/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import javax.swing.JSplitPane;

/**
 *
 * @author philb
 */
public class AlbumContainerWithOverviewPanel extends AppPanel {

    private JSplitPane splitter;
    private AlbumViewContainer albumViewContainer;
    private AlbumOverviewPanel albumOverviewPanel;

    public AlbumContainerWithOverviewPanel() {
        initComponents();
    }

    private void initComponents() {

        splitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        albumViewContainer = new AlbumViewContainer();
        albumOverviewPanel = new AlbumOverviewPanel();

        splitter.setLeftComponent(albumViewContainer);
        splitter.setRightComponent(albumOverviewPanel);
        splitter.setResizeWeight(1);
        splitter.setResizeWeight(1);

        layoutComponents();
    }

    private void layoutComponents() {

        splitter.setDividerLocation(600);

        GridBagCellConstraints gbc = new GridBagCellConstraints()
                .anchorNorth()
                .fillBoth()
                .weight(1);

        add(splitter, gbc);
    }
}
