/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.util.FileUtils;

/**
 *
 * @author philb
 */
public class HomeCommand extends AbstractCommand {

    public HomeCommand(AppContext appContext) {
        super(appContext);
    }
   
    @Override
    public void execute() {
        getAppContext().browseLocationUpdated(FileUtils.getHomeDirectory());
    }

}
