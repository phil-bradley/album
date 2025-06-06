/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.actionlistener;

import ie.philb.album.ui.action.Callback;
import ie.philb.album.ui.action.ZoomInAction;
import ie.philb.album.ui.common.Dialogs;

/**
 *
 * @author philb
 */
public class ZoomInActionListener extends AbstractCellActionListener {

    @Override
    protected void doAction() {

        new ZoomInAction(selectedPageEntryView.getPageEntryModel()).execute(new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
            }

            @Override
            public void onFailure(Exception ex) {
                Dialogs.showErrorMessage("Zoom failed: " + ex.getMessage());
            }

        });
    }

}
