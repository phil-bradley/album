/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.Context;
import ie.philb.album.ui.action.AddPageAction;

/**
 *
 * @author philb
 */
public class AddPageCommand extends AbstractCommand {

    public AddPageCommand(Context context) {
        super(context);
    }

    @Override
    public void execute() {
        executeAction(new AddPageAction(context.session()));
    }

}
