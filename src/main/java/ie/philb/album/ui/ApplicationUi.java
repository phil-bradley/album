/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui;

import ie.philb.album.AppContext;
import ie.philb.album.ApplicationListener;
import ie.philb.album.ui.action.Callback;
import ie.philb.album.ui.action.ZoomInAction;
import ie.philb.album.ui.action.ZoomOutAction;
import ie.philb.album.ui.action.ZoomResetAction;
import ie.philb.album.ui.action.ZoomToCoverFitAction;
import ie.philb.album.ui.command.CreatePdfCommand;
import ie.philb.album.ui.command.ExitCommand;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.Dialogs;
import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import ie.philb.album.ui.imagelibrary.ImageLibraryView;
import ie.philb.album.view.AlbumViewContainer;
import ie.philb.album.view.PageEntryView;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author philb
 */
public class ApplicationUi extends JFrame implements ApplicationListener {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationUi.class);

    private static final ApplicationUi INSTANCE = new ApplicationUi();

    private ImageLibraryView imageLibraryView;
    private AlbumViewContainer albumViewContainer;
    private PageEntryView selectedPageEntryView;

    private JSplitPane hSplit;
    private JSplitPane vSplit;
    private JToolBar toolBar;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JButton btnExit;
    private JButton btnPdf;
    private JButton btnZoomIn;
    private JButton btnZoomOut;
    private JButton btnZoomReset;
    private JButton btnZoomCover;

    public static ApplicationUi getInstance() {
        return INSTANCE;
    }

    private ApplicationUi() {

        super("Album");
        initComponents();

        setPreferredSize(new Dimension(1200, 800));
        setSize(getPreferredSize());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                new ExitCommand().execute();
            }

        });

        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                LOG.info("Key event " + e);
                if (e.getKeyCode() == KeyEvent.VK_Z) {
                    zoomInAction();
                }

                if (e.getKeyCode() == KeyEvent.VK_X) {
                    zoomOutAction();
                }
            }
        });

        AppContext.INSTANCE.addListener(this);
    }

    private void zoomInAction() {
        if (selectedPageEntryView == null) {
            return;
        }

        new ZoomInAction(selectedPageEntryView.getPageEntryModel()).execute(new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
            }

            @Override
            public void onFailure(Exception ex) {
                Dialogs.showErrorMessage("Zoom failed: " + ex.getMessage());
            }

        });
        LOG.info("Zooming in on " + selectedPageEntryView);
    }

    private void zoomOutAction() {
        if (selectedPageEntryView == null) {
            return;
        }
        new ZoomOutAction(selectedPageEntryView.getPageEntryModel()).execute(new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
            }

            @Override
            public void onFailure(Exception ex) {
                Dialogs.showErrorMessage("Zoom failed: " + ex.getMessage());
            }

        });
    }

    private void zoomResetAction() {
        if (selectedPageEntryView == null) {
            return;
        }
        new ZoomResetAction(selectedPageEntryView.getPageEntryModel()).execute(new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
            }

            @Override
            public void onFailure(Exception ex) {
                Dialogs.showErrorMessage("Zoom failed: " + ex.getMessage());
            }

        });
    }

    private void zoomCoverAction() {
        if (selectedPageEntryView == null) {
            return;
        }
        new ZoomToCoverFitAction(selectedPageEntryView).execute(new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
            }

            @Override
            public void onFailure(Exception ex) {
                Dialogs.showErrorMessage("Zoom failed: " + ex.getMessage());
            }

        });
    }

    private void initComponents() {

        initMenu();
        initToolBar();

        vSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        hSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        imageLibraryView = new ImageLibraryView();
        albumViewContainer = new AlbumViewContainer();

        hSplit.setLeftComponent(albumViewContainer);
        hSplit.setRightComponent(new AppPanel().background(Color.red));

        vSplit.setLeftComponent(imageLibraryView);
        vSplit.setRightComponent(hSplit);

        layoutComponents();
    }

    private void initMenu() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu();
        fileMenu.setText("File");
        menuBar.add(fileMenu);

        JMenuItem itemExit = new JMenuItem("Exit");
        itemExit.addActionListener((ActionEvent ae) -> {
            new ExitCommand().execute();
        });
        fileMenu.add(itemExit);
    }

    private void initToolBar() {
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setRollover(true);

        btnExit = new JButton();
        btnExit.setIcon(new ImageIcon(this.getClass().getResource("/ie/philb/album/icons/exit.png")));
        btnExit.addActionListener((ActionEvent ae) -> {
            new ExitCommand().execute();
        });

        toolBar.add(btnExit);

        btnPdf = new JButton();
        btnPdf.setIcon(new ImageIcon(this.getClass().getResource("/ie/philb/album/icons/page.png")));
        btnPdf.addActionListener((ActionEvent ae) -> {
            new CreatePdfCommand().execute();
        });
        toolBar.add(btnPdf);

        btnZoomIn = new JButton("+");
        btnZoomIn.addActionListener((ActionEvent ae) -> {
            zoomInAction();
        });
        toolBar.add(btnZoomIn);

        btnZoomOut = new JButton("-");
        btnZoomOut.addActionListener((ActionEvent ae) -> {
            zoomOutAction();
        });
        toolBar.add(btnZoomOut);

        btnZoomReset = new JButton("*");
        btnZoomReset.addActionListener((ActionEvent ae) -> {
            zoomResetAction();
        });
        toolBar.add(btnZoomReset);

        btnZoomCover = new JButton("=");
        btnZoomCover.addActionListener((ActionEvent ae) -> {
            zoomCoverAction();
        });
        toolBar.add(btnZoomCover);
    }

    private void layoutComponents() {

        int x = 0;
        int y = 0;

        setLayout(new GridBagLayout());
        setJMenuBar(menuBar);

        GridBagCellConstraints gbc = new GridBagCellConstraints(x, y)
                .anchorNorth()
                .fillHorizontal()
                .weight(1, 0);

        add(toolBar, gbc);

        gbc.fillBoth().xy(0, 1).weight(1);

        add(vSplit, gbc);
        vSplit.setDividerLocation(400);
        hSplit.setDividerLocation(600);

    }

    @Override
    public void imageEntrySelected(PageEntryView view) {
        this.selectedPageEntryView = view;
    }

    @Override
    public void libraryImageSelected(ImageLibraryEntry entry) {
    }
}
