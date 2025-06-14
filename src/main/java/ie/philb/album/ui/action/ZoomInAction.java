/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action;

import ie.philb.album.view.PageEntryView;

/**
 *
 * @author philb
 */
public class ZoomInAction extends AbstractAction<Void> {

    private final PageEntryView pageEntryView;

    public ZoomInAction(PageEntryView pageEntryView) {
        this.pageEntryView = pageEntryView;
    }

    @Override
    protected Void doAction() throws Exception {
        pageEntryView.zoomIn();
        return null;
    }

}
