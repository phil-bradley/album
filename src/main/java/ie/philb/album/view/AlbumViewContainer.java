/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.AppContext;
import ie.philb.album.ui.actionlistener.ZoomInActionListener;
import ie.philb.album.ui.actionlistener.ZoomOutActionListener;
import ie.philb.album.ui.actionlistener.ZoomResetActionListener;
import ie.philb.album.ui.actionlistener.ZoomToCoverFitActionListener;
import ie.philb.album.ui.command.NewPageCommand;
import ie.philb.album.ui.command.SetGeometryCommand;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.common.Icons;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

/**
 *
 * @author philb
 */
public class AlbumViewContainer extends AppPanel {

    private JToolBar toolBar;
    private JScrollPane scrollPane;
    private JButton btnZoomIn;
    private JButton btnZoomOut;
    private JButton btnZoomReset;
    private JButton btnZoomCover;
    private JButton btnNewPage;
    private PageGeometrySelector slctGeometry;

    private final AlbumView albumView = new AlbumView(AppContext.INSTANCE.getAlbumModel());

    public AlbumViewContainer() {

        gridbag();

        initComponents();
        layoutComponents();

        addComponentListener(new ResizeListener());
    }

    private void layoutComponents() {
        GridBagCellConstraints gbc = new GridBagCellConstraints(0, 0).weight(1, 0).fillHorizontal().anchorNorth();
        add(toolBar, gbc);

        gbc.xy(0, 1).fillBoth().weight(1);
        add(scrollPane, gbc);
    }

    private void initComponents() {

        this.scrollPane = new JScrollPane(albumView);
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        initToolBar();
    }

    private void initToolBar() {

        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setRollover(true);

        btnZoomIn = new JButton(Icons.Small.ZOOM_IN);
        btnZoomIn.addActionListener(new ZoomInActionListener());
        toolBar.add(btnZoomIn);

        btnZoomOut = new JButton(Icons.Small.ZOOM_OUT);
        btnZoomOut.addActionListener(new ZoomOutActionListener());
        toolBar.add(btnZoomOut);

        btnZoomReset = new JButton(Icons.Small.ZOOM_RESET);
        btnZoomReset.addActionListener(new ZoomResetActionListener());
        toolBar.add(btnZoomReset);

        btnZoomCover = new JButton(Icons.Small.ZOOM_COVER);
        btnZoomCover.addActionListener(new ZoomToCoverFitActionListener());
        toolBar.add(btnZoomCover);

        btnNewPage = new JButton(Icons.Small.NEW);
        btnNewPage.addActionListener((ActionEvent ae) -> {
            new NewPageCommand().execute();
        });
        toolBar.add(btnNewPage);

        slctGeometry = new PageGeometrySelector();
        slctGeometry.addActionListener((ActionEvent ae) -> {
            new SetGeometryCommand(slctGeometry.getSelectedGeometryOption().geometry()).execute();
        });

        slctGeometry.setMaximumSize(new Dimension(100, 100));

        toolBar.add(slctGeometry);

        setZoomButtonsEnabled(false);
    }

    class ResizeListener extends ComponentAdapter {

        @Override
        public void componentResized(ComponentEvent e) {
            albumView.positionPages();
        }
    }

    private void setZoomButtonsEnabled(boolean isEnabled) {
        btnZoomIn.setEnabled(isEnabled);
        btnZoomOut.setEnabled(isEnabled);
        btnZoomReset.setEnabled(isEnabled);
        btnZoomCover.setEnabled(isEnabled);

    }

    @Override
    public void pageEntrySelected(PageEntryView view) {
        setZoomButtonsEnabled(view != null && view.getPageEntryModel().getImageIcon() != null);
    }

    @Override
    public void albumUpdated() {
        albumView.setModel(AppContext.INSTANCE.getAlbumModel());
//        albumView.positionPages();
//        scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getMaximum() + 100);
    }
}
