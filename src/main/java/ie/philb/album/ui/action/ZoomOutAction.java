/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action;

import ie.philb.album.model.PageEntryModel;

/**
 *
 * @author philb
 */
public class ZoomOutAction extends AbstractAction<Void> {

    private final PageEntryModel pageEntryModel;

    public ZoomOutAction(PageEntryModel pageEntryModel) {
        this.pageEntryModel = pageEntryModel;
    }

    @Override
    protected Void doAction() throws Exception {
        pageEntryModel.zoomOut();
        return null;
    }

}
