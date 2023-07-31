/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.ui.common.Dialogs;

/**
 *
 * @author Philip.Bradley
 */
public class ExitCommand extends AbstractCommand {

    @Override
    public void execute() {

        if (Dialogs.confirm("Really exit?")) {
            System.exit(0);
        }
    }

}
