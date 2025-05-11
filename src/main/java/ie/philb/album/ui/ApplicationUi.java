/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui;

import ie.philb.album.ui.command.ExitCommand;
import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.imagelibrary.ImageLibraryView;
import ie.philb.album.ui.page.PageOverviewPanel;
import ie.philb.album.view.AlbumViewContainer;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

/**
 *
 * @author philb
 */
public class ApplicationUi extends JFrame {

    private ImageLibraryView imageLibraryView;
    private PageOverviewPanel pageOverviewPanel;
    private AlbumViewContainer albumViewContainer;

    private JSplitPane hSplit;
    private JSplitPane vSplit;
//    private JToolBar toolBar;
    private JMenuBar menuBar;
    private JMenu fileMenu;

    public ApplicationUi() {

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
    }

    private void initComponents() {

        initMenu();

        vSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        hSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        imageLibraryView = new ImageLibraryView();
        pageOverviewPanel = new PageOverviewPanel();
        albumViewContainer = new AlbumViewContainer();

        hSplit.setLeftComponent(albumViewContainer);
        hSplit.setRightComponent(pageOverviewPanel);

        vSplit.setLeftComponent(imageLibraryView);
        vSplit.setRightComponent(hSplit);
        layoutComponents();
    }

    private void initMenu() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu();
        fileMenu.setText("File");
        menuBar.add(fileMenu);
    }

    private void initToolBar() {
        //toolBar = new JToolBar();

    }

    private void layoutComponents() {

        int x = 0;
        int y = 0;

        setLayout(new GridBagLayout());
        setJMenuBar(menuBar);

        GridBagCellConstraints gbc = new GridBagCellConstraints(x, y)
                .anchorNorth()
                .fillBoth()
                .weight(1);

        add(vSplit, gbc);
        vSplit.setDividerLocation(200);
        hSplit.setDividerLocation(200);

    }
}
