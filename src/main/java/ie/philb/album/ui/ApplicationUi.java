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
import ie.philb.album.ui.command.NewAlbumCommand;
import ie.philb.album.ui.command.OpenAlbumCommand;
import ie.philb.album.ui.command.PrintAlbumCommand;
import ie.philb.album.ui.command.SaveAlbumCommand;
import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import ie.philb.album.ui.imagelibrary.ImageLibraryView;
import ie.philb.album.ui.resources.Icons;
import ie.philb.album.view.AlbumOverviewPanel;
import ie.philb.album.view.AlbumViewContainer;
import ie.philb.album.view.PageEntryView;
import ie.philb.album.view.PageView;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
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
import javax.swing.KeyStroke;
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
    private AlbumOverviewPanel albumOverviewPanel;
    private PageView selectedPageView;
    private PageEntryView selectedPageEntryView;
    private JSplitPane hSplit;
    private JSplitPane vSplit;
    private JToolBar toolBar;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JButton btnPdf;
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
        albumOverviewPanel = new AlbumOverviewPanel();

        hSplit.setLeftComponent(albumViewContainer);
        hSplit.setRightComponent(albumOverviewPanel);
        hSplit.setResizeWeight(1);

        vSplit.setLeftComponent(imageLibraryView);
        vSplit.setRightComponent(hSplit);

        layoutComponents();
    }

    private void initMenu() {
        menuBar = new JMenuBar();
        menuBar.add(fileMenu = new JMenu("File"));
        fileMenu.setMnemonic(KeyEvent.VK_F);

        addMenuItem(fileMenu, Icons.Small.NEW, "New", new NewAlbumCommand(), KeyEvent.VK_N);
        addMenuItem(fileMenu, Icons.Small.OPEN, "Open", new OpenAlbumCommand(), KeyEvent.VK_O);
        addMenuItem(fileMenu, Icons.Small.SAVE, "Save", new SaveAlbumCommand(), KeyEvent.VK_S);
        addMenuItem(fileMenu, Icons.Small.PDF, "Export to PDF", new CreatePdfCommand(), KeyEvent.VK_E);
        addMenuItem(fileMenu, Icons.Small.PRINT, "Print", new PrintAlbumCommand(), KeyEvent.VK_P);
        addMenuItem(fileMenu, Icons.Small.EXIT, "Exit", new ExitCommand(), KeyEvent.VK_X);
    }

    private void addMenuItem(JMenu menu, ImageIcon icon, String title, AbstractCommand command, int shortCutKey) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.setIcon(icon);
        menuItem.addActionListener((ActionEvent ae) -> {
            command.execute();
        });
        menuItem.setAccelerator(KeyStroke.getKeyStroke(shortCutKey, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);
    }

    private void initToolBar() {
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setRollover(true);

        initToolbarButton(btnNew, Icons.Regular.NEW, "New Album", new NewAlbumCommand());
        initToolbarButton(btnOpen, Icons.Regular.OPEN, "Open existing album", new OpenAlbumCommand());
        initToolbarButton(btnSave, Icons.Regular.SAVE, "Save album", new SaveAlbumCommand());
        initToolbarButton(btnPdf, Icons.Regular.PDF, "Export to PDF", new CreatePdfCommand());
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
    public void pageEntrySelected(PageView pageView, PageEntryView pageEntryView) {
        this.selectedPageView = pageView;
        this.selectedPageEntryView = pageEntryView;
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

    @Override
    public void pageSelected(PageView pageView) {
    }
}
