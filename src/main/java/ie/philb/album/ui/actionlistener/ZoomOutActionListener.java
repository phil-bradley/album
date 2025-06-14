/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.actionlistener;

import ie.philb.album.ui.action.callback.Callback;
import ie.philb.album.ui.action.ZoomOutAction;
import ie.philb.album.ui.common.Dialogs;

/**
 *
 * @author philb
 */
public class ZoomOutActionListener extends AbstractCellActionListener {

    @Override
    protected void doAction() {
        new ZoomOutAction(selectedPageEntryView.getPageEntryModel()).execute(new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
            }

            @Override
            public void onFailure(Exception ex) {
                Dialogs.showErrorMessage("Zoom failed", ex);
            }

        });
    }

}
