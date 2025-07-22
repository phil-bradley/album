/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.AppContext;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
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
        this.albumView.setPreferredSize(getPreferredSize());
        this.albumView.setPreviewMode(true);
        this.albumView.setVisible(true);

        // Simulate a glasspane in order to capture mouse events, that is,
        // prevent them from being handled by lower level elements.
        LayerUI<JScrollPane> mouseInterceptLayer = new LayerUI<>() {
            @Override
            public void installUI(JComponent c) {
                super.installUI(c);
                ((JLayer<?>) c).setLayerEventMask(AWTEvent.MOUSE_EVENT_MASK);
            }

            @Override
            public void uninstallUI(JComponent c) {
                ((JLayer<?>) c).setLayerEventMask(0);
                super.uninstallUI(c);
            }

            @Override
            protected void processMouseEvent(MouseEvent e, JLayer<? extends JScrollPane> l) {
                if (e.getID() == MouseEvent.MOUSE_CLICKED) {
                    Component target = SwingUtilities.getDeepestComponentAt(l.getView(), e.getX(), e.getY());
                    System.out.println("Intercepted click at: " + e.getPoint() + ", would have hit " + target);
                }
            }
        };

        this.scrollPane = new JScrollPane();
        this.scrollPane.setViewportView(albumView);
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JLayer<JScrollPane> layeredAlbumView = new JLayer<>(scrollPane, mouseInterceptLayer);

        add(layeredAlbumView, new GridBagCellConstraints().fillBoth().weight(1));
        addComponentListener(new ResizeListener());

        setFocusable(true);
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        LOG.info("mouse clicked on overview");
    }

    @Override
    public void mousePressed(MouseEvent evt) {
        LOG.info("mouse pressed on overview");
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
            revalidate();
        }
    }

}
