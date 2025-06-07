/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;

/**
 *
 * @author philb
 */
public class BrowseToParentCommand extends AbstractCommand {

    @Override
    public void execute() {
        AppContext.INSTANCE.browseLocationUpdated(AppContext.INSTANCE.getBrowseLocation().getParentFile());
    }

}
