/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.Context;
import ie.philb.album.ui.about.AboutDialog;

/**
 *
 * @author philb
 */
public class AboutCommand extends AbstractCommand {

    public AboutCommand(Context context) {
        super(context);
    }


    @Override
    public void execute() {
        AboutDialog dlg = new AboutDialog(context.ui());
        dlg.setVisible(true);
    }

}
