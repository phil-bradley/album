/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.actionlistener;

/**
 *
 * @author philb
 */
public class ZoomInActionListener extends AbstractCellActionListener {

    @Override
    protected void doAction() {
        selectedPageEntryView.zoomIn();
    }

}
