/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.page;

import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.common.GridBagCellConstraints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JScrollPane;

/**
 *
 * @author Philip.Bradley
 */
public class PageSetContainerPanel extends AppPanel {

    private final PageSetPanel pageSetPanel = new PageSetPanel();

    public PageSetContainerPanel() {

        JScrollPane scrollPane = new JScrollPane(pageSetPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        gridbag();

        GridBagCellConstraints gbc = new GridBagCellConstraints(1, 1).weight(1).fillBoth();
        add(scrollPane, gbc);

        addComponentListener(new ResizeListener());
    }

//    public List<PagePanel> getPagePanels() {
//        return pageSetPanel.getPagePanels();
//    }
    class ResizeListener extends ComponentAdapter {

        @Override
        public void componentResized(ComponentEvent e) {
            pageSetPanel.positionPages();
        }
    }
}
