/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.AppContext;
import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JScrollPane;

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

        this.scrollPane = new JScrollPane(albumView);
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, new GridBagCellConstraints().fillBoth().weight(1));

        addComponentListener(new ResizeListener());
    }

    @Override
    public void albumUpdated() {
        albumView.setModel(AppContext.INSTANCE.getAlbumModel());
    }

    class ResizeListener extends ComponentAdapter {

        @Override
        public void componentResized(ComponentEvent e) {
            albumView.positionPages();
        }
    }
}
