/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.AppContext;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JScrollPane;
import javax.swing.plaf.LayerUI;

/**
 *
 * @author philb
 */
public class AlbumOverviewPanel extends AppPanel {

    private AlbumView albumView;
    private JScrollPane scrollPane;

    public AlbumOverviewPanel() {
        this.albumView = new AlbumView(AppContext.INSTANCE.getAlbumModel());
        this.albumView.setPreviewMode(true);

        // Simulate a glasspane in order to capture mouse events, that is,
        // prevent them from being handled by lower level elements.
        JLayer<AlbumView> layeredAlbumView = new JLayer<>(albumView, new LayerUI<AlbumView>() {

            @Override
            public void paint(Graphics g, JComponent c) {
                super.paint(g, c);

                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(0, 0, 0, 100));  // translucent black
                g2.fillRect(0, 0, getWidth(), getHeight());
            }

            @Override
            protected void processMouseEvent(MouseEvent e, JLayer<? extends AlbumView> l) {
                System.out.println("Mouse event intercepted at: " + e.getPoint());
                e.consume();  //  block event from reaching underlying components
            }
        });

        albumView.setVisible(true);
        layeredAlbumView.setVisible(true);
        layeredAlbumView.setOpaque(false);
        setVisible(true);

        this.scrollPane = new JScrollPane(albumView);
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        add(scrollPane, new GridBagCellConstraints().fillBoth().weight(1));
        addComponentListener(new ResizeListener());

        setFocusable(true);
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        LOG.info("mouse clicked on overview");
    }

    @Override
    public void albumUpdated() {
        albumView.setModel(AppContext.INSTANCE.getAlbumModel());
        scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getMaximum());
    }

    class ResizeListener extends ComponentAdapter {

        @Override
        public void componentResized(ComponentEvent e) {
            albumView.positionPages();
        }
    }

}
