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
public class ZoomResetAction extends AbstractAction<Void> {

    private final PageEntryModel pageEntryModel;

    public ZoomResetAction(PageEntryModel pageEntryModel) {
        this.pageEntryModel = pageEntryModel;
    }

    @Override
    protected Void execute() throws Exception {
        pageEntryModel.resetZoom();
        return null;
    }
}
