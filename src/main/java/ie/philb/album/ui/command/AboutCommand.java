/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.ui.about.AboutDialog;

/**
 *
 * @author philb
 */
public class AboutCommand extends AbstractCommand {

    public AboutCommand(AppContext appContext) {
        super(appContext);
    }

    @Override
    public void execute() {
        AboutDialog dlg = getAppContext().getDialogFactory().createAboutDialog();
        dlg.setVisible(true);
    }

}
