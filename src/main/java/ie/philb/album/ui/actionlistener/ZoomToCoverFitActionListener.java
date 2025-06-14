/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.actionlistener;

import ie.philb.album.ui.action.ZoomToCoverFitAction;

/**
 *
 * @author philb
 */
public class ZoomToCoverFitActionListener extends AbstractCellActionListener {

    @Override
    protected void doAction() {

        new ZoomToCoverFitAction(selectedPageEntryView).execute();
    }
}
