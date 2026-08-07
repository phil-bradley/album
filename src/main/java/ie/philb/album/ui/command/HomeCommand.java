/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.Context;
import ie.philb.album.util.FileUtils;

/**
 *
 * @author philb
 */
public class HomeCommand extends AbstractCommand {

    public HomeCommand(Context context) {
        super(context);
    }

    @Override
    public void execute() {
        context.session().getEventBus().browseLocationUpdated(FileUtils.getHomeDirectory());
    }
}
