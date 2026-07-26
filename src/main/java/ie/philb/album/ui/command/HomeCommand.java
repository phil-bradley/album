/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.Context;

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
        // TODO
        //AppContext.INSTANCE.browseLocationUpdated(FileUtils.getHomeDirectory());
    }

}
