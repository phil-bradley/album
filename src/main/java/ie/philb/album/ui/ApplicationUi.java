/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui;

import ie.philb.album.AppContext;
import ie.philb.album.ApplicationListener;
import ie.philb.album.ui.command.AbstractCommand;
import ie.philb.album.ui.command.CreatePdfCommand;
import ie.philb.album.ui.command.ExitCommand;
import ie.philb.album.ui.command.HomeCommand;
import ie.philb.album.ui.command.NewAlbumCommand;
import ie.philb.album.ui.command.OpenAlbumCommand;
import ie.philb.album.ui.command.SaveAlbumCommand;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.common.Icons;
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
import java.io.File;
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
    private JButton btnHome;
    private JButton btnNew;
    private JButton btnOpen;
    private JButton btnSave;

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
                    LOG.info("Would zoom in");
                }

                if (e.getKeyCode() == KeyEvent.VK_X) {
                    LOG.info("Would zoom out");
                }
            }
        });

        AppContext.INSTANCE.addListener(this);
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

//        initToolbarButton(btnExit, Icons.EXIT, "Exit", new ExitCommand());
        initToolbarButton(btnHome, Icons.HOME, "Home", new HomeCommand());
        initToolbarButton(btnNew, Icons.NEW, "New Album", new NewAlbumCommand());
        initToolbarButton(btnOpen, Icons.OPEN, "Open existing album", new OpenAlbumCommand());
        initToolbarButton(btnSave, Icons.SAVE, "Save album", new SaveAlbumCommand());
        initToolbarButton(btnPdf, Icons.PDF, "Export to PDF", new CreatePdfCommand());
    }

    private void initToolbarButton(JButton button, ImageIcon icon, String title, AbstractCommand command) {

        button = new JButton(icon);
        button.setToolTipText(title);
        button.addActionListener((ActionEvent ae) -> {
            command.execute();
        });

        toolBar.add(button);
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

    @Override
    public void browseLocationUpdated(File file) {
    }

    @Override
    public void albumUpdated() {
    }
}
