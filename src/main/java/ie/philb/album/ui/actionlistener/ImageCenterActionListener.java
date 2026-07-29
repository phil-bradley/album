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
public class ImageCenterActionListener extends AbstractCellActionListener {

    public ImageCenterActionListener(Context context) {
        super(context);
    }
    
    @Override
    protected void doAction() {
        selectedPageEntryView.centerImage();
    }
}
