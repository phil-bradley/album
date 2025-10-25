/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.AppContext;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.util.UiUtils;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.LayerUI;

/**
 *
 * @author philb
 */
public class AlbumOverviewPanel extends AppPanel {

    private AppContext appContext;
    private AlbumView albumView;
    private JScrollPane scrollPane;

    public AlbumOverviewPanel(AppContext appContext) {
        this.appContext = appContext;
        this.albumView = new AlbumView(appContext);
        this.albumView.setPreferredSize(getPreferredSize());
        this.albumView.setPreviewMode(true);
        this.albumView.setVisible(true);

        // Simulate a glasspane in order to capture mouse events, that is,
        // prevent them from being handled by lower level elements.
        LayerUI<JScrollPane> mouseInterceptLayer = new LayerUI<>() {

            private Point lastDragPoint;

            @Override
            public void installUI(JComponent c) {
                super.installUI(c);
                ((JLayer<?>) c).setLayerEventMask(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
            }

            @Override
            public void uninstallUI(JComponent c) {
                ((JLayer<?>) c).setLayerEventMask(0);
                super.uninstallUI(c);
            }

            @Override
            protected void processMouseMotionEvent(MouseEvent e, JLayer<? extends JScrollPane> layer) {

                Component c = SwingUtilities.getDeepestComponentAt(layer.getView(), e.getPoint().x, e.getPoint().y);

                if (isScrollbar(c)) {
                    // Let the event pass through normally
                    e.setSource(c); // Re-target to scrollbar
                    c.dispatchEvent(SwingUtilities.convertMouseEvent(layer, e, c));
                    return;
                }

                // Panning behaviour
                if (e.getID() == MouseEvent.MOUSE_DRAGGED) {

                    Point newPoint = e.getLocationOnScreen();

                    int dx = lastDragPoint.x - newPoint.x;

                    JScrollBar hBar = scrollPane.getHorizontalScrollBar();
                    int newPos = hBar.getValue() + dx;
                    if (newPos < 0) {
                        newPos = 0;
                    }

                    hBar.setValue(newPos);
                    lastDragPoint = newPoint;
                }
            }

            @Override
            protected void processMouseEvent(MouseEvent e, JLayer<? extends JScrollPane> layer) {

                Component c = SwingUtilities.getDeepestComponentAt(layer.getView(), e.getPoint().x, e.getPoint().y);

                if (isScrollbar(c)) {
                    // Let the event pass through normally
                    e.setSource(c); // Re-target to scrollbar
                    c.dispatchEvent(SwingUtilities.convertMouseEvent(layer, e, c));
                    return;
                }

                if (e.getID() == MouseEvent.MOUSE_PRESSED) {
                    lastDragPoint = e.getLocationOnScreen();
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                if (e.getID() == MouseEvent.MOUSE_RELEASED) {
                    lastDragPoint = null;
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }

                if (e.getID() == MouseEvent.MOUSE_CLICKED) {

                    AlbumView av = (AlbumView) scrollPane.getViewport().getView();
                    Point avPoint = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), av);

                    PageView pageView = UiUtils.getChildOfType(av, avPoint.x, avPoint.y, PageView.class);

                    if (pageView != null) {
                        appContext.pageNavigatedTo(pageView.getPageModel().getPageId());
                    }

                    e.consume();
                }

            }

            private boolean isScrollbar(Component comp) {
                // Check if the component is a scrollbar or inside one
                while (comp != null) {
                    if (comp instanceof JScrollBar) {
                        return true;
                    }
                    comp = comp.getParent();
                }
                return false;
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
        albumView.refresh();
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
