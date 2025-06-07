/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import java.io.File;

/**
 *
 * @author philb
 */
public class BrowseToParentCommand extends AbstractCommand {

    @Override
    public void execute() {
        File currentLocation = AppContext.INSTANCE.getBrowseLocation();
        File parent = currentLocation.getParentFile();

        if (parent != null) {
            AppContext.INSTANCE.browseLocationUpdated(parent);
        }
    }

}
