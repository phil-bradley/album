/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action;

import ie.philb.album.AppSession;
import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageModel;

/**
 *
 * @author philb
 */
public class SetGeometryAction extends AbstractAction<Void> {

    private final PageModel pageModel;
    private final PageGeometry pageGeometry;

    public SetGeometryAction(AppSession session, PageModel pageModel, PageGeometry pageGeometry) {
        super(session);
        this.pageModel = pageModel;
        this.pageGeometry = pageGeometry;
    }

    @Override
    protected Void doAction() throws Exception {

        if (!pageGeometry.equals(pageModel.getGeometry())) {
            pageModel.setGeometry(pageGeometry);
            session.getEventBus().albumUpdated();
        }
        
        return null;
    }

}
