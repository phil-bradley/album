/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.actionlistener;

import ie.philb.album.Context;

/**
 *
 * @author philb
 */
public class ZoomOutActionListener extends AbstractCellActionListener {

    public ZoomOutActionListener(Context context) {
        super(context);
    }

    @Override
    protected void doAction() {
        selectedPageEntryView.zoomOut();
    }
}
