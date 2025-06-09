/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.model.PageGeometry;
import ie.philb.album.view.PageView;

/**
 *
 * @author philb
 */
public class SetGeometryCommand extends AbstractCommand {

    @Override
    public void execute() {

        PageView pageView = AppContext.INSTANCE.getSelectedPageView();

        if (pageView == null) {
            return;
        }
        pageView.getPageModel().setGeometry(PageGeometry.rectangle(4, 4));
        AppContext.INSTANCE.albumUpdated();
    }

}
