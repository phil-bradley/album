/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.Context;
import ie.philb.album.ui.action.DeletePageAction;
import ie.philb.album.ui.common.Dialogs;
import ie.philb.album.view.PageView;

/**
 *
 * @author philb
 */
public class DeletePageCommand extends AbstractCommand {

    public DeletePageCommand(Context context) {
        super(context);
    }

    @Override
    public void execute() {

        PageView selected = context.session().getSelectedPageView();

        if (selected == null) {
            return;
        }

        if (Dialogs.confirm(context.ui(), "Delete this page?")) {
            executeAction(new DeletePageAction(context.session(), selected.getPageModel().getPageId()));
        }
    }

}
