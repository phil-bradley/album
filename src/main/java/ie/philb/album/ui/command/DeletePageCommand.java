/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.ui.action.DeletePageAction;
import ie.philb.album.ui.common.Dialogs;
import ie.philb.album.view.PageView;

/**
 *
 * @author philb
 */
public class DeletePageCommand extends AbstractCommand {

    @Override
    public void execute() {

        PageView selected = AppContext.INSTANCE.getSelectedPageView();

        if (selected == null) {
            return;
        }

        if (Dialogs.confirm("Delete this page?")) {
            new DeletePageAction(selected.getPageModel().getPageId()).execute();
        }
    }

}
