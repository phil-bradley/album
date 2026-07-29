/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.actionlistener;

import ie.philb.album.Context;
import ie.philb.album.ui.action.ZoomToCoverFitAction;
import ie.philb.album.ui.action.callback.DefaultNoResultCallback;

/**
 *
 * @author philb
 */
public class ZoomToCoverFitActionListener extends AbstractCellActionListener {

    public ZoomToCoverFitActionListener(Context context) {
        super(context);
    }

    @Override
    protected void doAction() {
        new ZoomToCoverFitAction(context.session(), selectedPageEntryView).execute(
                new DefaultNoResultCallback<>(context.ui())
        );
    }
}
