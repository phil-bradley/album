/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.page;

import java.util.List;

/**
 *
 * @author philipb
 */
public class Page {

    private final PagePanel pagePanel;

    public Page(PageLayout layout) {
        this.pagePanel = new PagePanel(layout);
    }

    public PageLayout getPageLayout() {
        return pagePanel.getPageLayout();
    }

    public PagePanel getPagePanel() {
        return pagePanel;
    }

    public List<PagePanelEntry> getPagePanelEntries() {
        return pagePanel.getPagePanelEntries();
    }

}
