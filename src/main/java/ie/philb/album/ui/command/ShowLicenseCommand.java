/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.ui.about.LicenseDialog;

/**
 *
 * @author philb
 */
public class ShowLicenseCommand extends AbstractCommand {

    @Override
    public void execute() {
        LicenseDialog dlg = new LicenseDialog();
        dlg.setVisible(true);
    }

}
