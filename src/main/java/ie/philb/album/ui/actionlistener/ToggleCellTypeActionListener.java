/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.actionlistener;

import ie.philb.album.model.PageEntryType;
import static ie.philb.album.model.PageEntryType.Image;
import static ie.philb.album.model.PageEntryType.Text;

/**
 *
 * @author philb
 */
public class ToggleCellTypeActionListener extends AbstractCellActionListener {

    @Override
    protected void doAction() {
        PageEntryType pageEntryType = selectedPageEntryView.getPageEntryModel().getPageEntryType();
        PageEntryType newType = (pageEntryType == Image) ? Text : Image;
        selectedPageEntryView.getPageEntryModel().setPageEntryType(newType);
    }
    
}
