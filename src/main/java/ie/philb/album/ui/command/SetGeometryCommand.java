/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.Context;
import ie.philb.album.model.PageGeometry;
import ie.philb.album.view.PageView;

/**
 *
 * @author philb
 */
public class SetGeometryCommand extends AbstractCommand {

    private final PageGeometry pageGeometry;

    public SetGeometryCommand(Context context, PageGeometry pageGeometry) {
        super(context);
        this.pageGeometry = pageGeometry;
    }

    @Override
    public void execute() {

        PageView pageView = context.session().getSelectedPageView();

        if (pageView == null) {
            return;
        }

        if (!pageGeometry.equals(pageView.getPageModel().getGeometry())) {
            pageView.getPageModel().setGeometry(pageGeometry);
            context.session().getEventBus().albumUpdated();
        }
    }

}
