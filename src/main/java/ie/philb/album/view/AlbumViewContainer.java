/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.AppContext;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageEntryType;
import ie.philb.album.model.PageModel;
import ie.philb.album.ui.actionlistener.ImageCenterActionListener;
import ie.philb.album.ui.actionlistener.ToggleCellTypeActionListener;
import ie.philb.album.ui.actionlistener.ToggleGrayScaleActionListener;
import ie.philb.album.ui.actionlistener.ZoomInActionListener;
import ie.philb.album.ui.actionlistener.ZoomOutActionListener;
import ie.philb.album.ui.actionlistener.ZoomResetActionListener;
import ie.philb.album.ui.actionlistener.ZoomToCoverFitActionListener;
import ie.philb.album.ui.command.NewPageCommand;
import ie.philb.album.ui.command.SetGeometryCommand;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.common.Icons;
import ie.philb.album.ui.common.filters.BrightnessFilter;
import ie.philb.album.ui.common.numbercontrol.SlidingNumberControl;
import ie.philb.album.ui.common.numbercontrol.SlidingNumberControlListener;
import ie.philb.album.ui.common.numbercontrol.SlidingNumberControlModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
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
    private JButton btnCenter;
    private JToggleButton btnGray;
    private JToggleButton btnCellType;
    private JButton btnNewPage;
    private JButton btnPageSettings;
    private JButton btnBrightness;
    private PageGeometrySelector slctGeometry;
    private SlidingNumberControl brightnessControl;
    private JPopupMenu brightnessMenu;
    private SlidingNumberControl horizontalMarginControl;
    private SlidingNumberControl verticalMarginControl;
    private JPopupMenu pageSettingsMenu;

    private final AlbumView albumView = new AlbumView(AppContext.INSTANCE.getAlbumModel());

    public AlbumViewContainer() {

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
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        brightnessControl = new SlidingNumberControl(
                new SlidingNumberControlModel(BrightnessFilter.MIN_BRIGHTNESS, BrightnessFilter.MAX_BRIGHTNESS, 1, BrightnessFilter.DEFAULT_BRIGHTNESS)
        );

        brightnessControl.addListener(new SlidingNumberControlListener() {
            @Override
            public void valueUpdated(int value) {
                adjustBrightness();
            }
        });

        brightnessMenu = new JPopupMenu();
        brightnessMenu.setLayout(new BorderLayout());
        brightnessMenu.add(brightnessControl, BorderLayout.CENTER);

        verticalMarginControl = new SlidingNumberControl(
                new SlidingNumberControlModel(0, 200, 1, 25)
        );

        verticalMarginControl.addListener(new SlidingNumberControlListener() {
            @Override
            public void valueUpdated(int value) {
                adjustVerticalMargin();

            }
        });

        horizontalMarginControl = new SlidingNumberControl(
                new SlidingNumberControlModel(0, 200, 1, 25)
        );

        horizontalMarginControl.addListener(new SlidingNumberControlListener() {
            @Override
            public void valueUpdated(int value) {
                adjustHorizontalMargin();

            }
        });

        pageSettingsMenu = new JPopupMenu();
        pageSettingsMenu.setLayout(new BoxLayout(pageSettingsMenu, BoxLayout.Y_AXIS));
        pageSettingsMenu.add(horizontalMarginControl);
        pageSettingsMenu.add(verticalMarginControl);

        initToolBar();
    }

    private void adjustVerticalMargin() {
        PageView selected = AppContext.INSTANCE.getSelectedPageView();

        if (selected == null) {
            return;
        }

        int newMargin = verticalMarginControl.getValue();
        selected.getPageModel().setVerticalMargin(newMargin);
    }

    private void adjustHorizontalMargin() {
        PageView selected = AppContext.INSTANCE.getSelectedPageView();

        if (selected == null) {
            return;
        }

        int newMargin = horizontalMarginControl.getValue();
        selected.getPageModel().setHorizontalMargin(newMargin);
    }

    private void adjustBrightness() {

        PageEntryView selected = AppContext.INSTANCE.getSelectedPageEntryView();

        if (selected == null) {
            return;
        }

        selected.getPageEntryModel().setBrightnessAdjustment(brightnessControl.getValue());
    }

    private void initToolBar() {

        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setRollover(true);

        btnNewPage = new JButton(Icons.Small.NEW);
        btnNewPage.addActionListener((ActionEvent ae) -> {
            new NewPageCommand().execute();
        });
        btnNewPage.setToolTipText("New Page");
        toolBar.add(btnNewPage);

        btnPageSettings = new JButton(Icons.Small.MARGIN);
        btnPageSettings.addActionListener((ActionEvent ae) -> {
            pageSettingsMenu.show(btnPageSettings, 0, btnPageSettings.getHeight());

        });
        btnPageSettings.setToolTipText("Page Settings");
        toolBar.add(btnPageSettings);

        btnCellType = new JToggleButton(Icons.Small.PICTURE);
        btnCellType.setText("Image");
        btnCellType.addActionListener(new ToggleCellTypeActionListener());
        toolBar.add(btnCellType);

        btnZoomIn = new JButton(Icons.Small.ZOOM_IN);
        btnZoomIn.addActionListener(new ZoomInActionListener());
        btnZoomIn.setText("Zoom In");
        toolBar.add(btnZoomIn);

        btnZoomOut = new JButton(Icons.Small.ZOOM_OUT);
        btnZoomOut.addActionListener(new ZoomOutActionListener());
        btnZoomOut.setText("Zoom Out");
        toolBar.add(btnZoomOut);

        btnZoomReset = new JButton(Icons.Small.ZOOM_RESET);
        btnZoomReset.addActionListener(new ZoomResetActionListener());
        btnZoomReset.setText("Reset to fit");
        toolBar.add(btnZoomReset);

        btnZoomCover = new JButton(Icons.Small.ZOOM_COVER);
        btnZoomCover.addActionListener(new ZoomToCoverFitActionListener());
        btnZoomCover.setText("Reset to cover");
        toolBar.add(btnZoomCover);

        btnCenter = new JButton(Icons.Small.ALIGN_CENTER);
        btnCenter.addActionListener(new ImageCenterActionListener());
        btnCenter.setText("Center");
        toolBar.add(btnCenter);

        btnGray = new JToggleButton(Icons.Small.COLOR);
        btnGray.setText("Color");
        btnGray.addActionListener(new ToggleGrayScaleActionListener());
        toolBar.add(btnGray);

        btnBrightness = new JButton(Icons.Small.BRIGHTNESS);
        btnBrightness.addActionListener(e -> {
            brightnessMenu.show(btnBrightness, 0, btnBrightness.getHeight());
        });
        toolBar.add(btnBrightness);

        slctGeometry = new PageGeometrySelector();
        slctGeometry.addActionListener((ActionEvent ae) -> {
            new SetGeometryCommand(slctGeometry.getSelectedGeometryOption().geometry()).execute();
        });

        slctGeometry.setMaximumSize(new Dimension(100, 100));
        toolBar.add(slctGeometry);

        slctGeometry.setEnabled(false);
        setCellButtonsEnabled(false);
        btnCellType.setEnabled(false);
    }

    class ResizeListener extends ComponentAdapter {

        @Override
        public void componentResized(ComponentEvent e) {
            albumView.positionPages();
            revalidate();
        }
    }

    private void setCellButtonsEnabled(boolean isEnabled) {
        btnZoomIn.setEnabled(isEnabled);
        btnZoomOut.setEnabled(isEnabled);
        btnZoomReset.setEnabled(isEnabled);
        btnZoomCover.setEnabled(isEnabled);
        btnCenter.setEnabled(isEnabled);
        btnGray.setEnabled(isEnabled);
        btnBrightness.setEnabled(isEnabled);
    }

    @Override
    public void pageEntrySelected(PageView pageView, PageEntryView view) {

        if (view == null) {
            setCellButtonsEnabled(false);
            return;
        }

        setCellButtonsEnabled(view.getPageEntryModel().getImage() != null);
        btnCellType.setEnabled(true);
        updateSelectorButtons(view.getPageEntryModel());
        brightnessControl.setValue(view.getPageEntryModel().getBrightnessAdjustment());
    }

    @Override
    public void pageSelected(PageView view) {
        slctGeometry.setEnabled(view != null);
        if (view != null) {
            PageModel model = view.getPageModel();
            slctGeometry.setSelectedGeometry(model.getGeometry());
            verticalMarginControl.setValue(model.getVerticalMargin());
            horizontalMarginControl.setValue(model.getHorizontalMargin());
        }
    }

    @Override
    public void albumUpdated() {
        albumView.setModel(AppContext.INSTANCE.getAlbumModel());
        LOG.info("Updated album");
    }

    @Override
    public void pageEntryUpdated(PageEntryModel pem) {
        updateSelectorButtons(pem);
        brightnessControl.setValue(pem.getBrightnessAdjustment());
    }

    private void updateSelectorButtons(PageEntryModel pageEntryModel) {
        boolean isGrayScale = pageEntryModel.isGrayScale();
        btnGray.setSelected(isGrayScale);
        btnGray.setIcon(isGrayScale ? Icons.Small.GRAYSCALE : Icons.Small.COLOR);
        btnGray.setText(isGrayScale ? "B&W" : "Colour");

        btnCellType.setText(pageEntryModel.getCell().pageEntryType().name());

        if (pageEntryModel.getCell().pageEntryType() == PageEntryType.Image) {
            btnCellType.setIcon(Icons.Small.PICTURE);
        } else {
            btnCellType.setIcon(Icons.Small.TEXT);
        }
    }
}
