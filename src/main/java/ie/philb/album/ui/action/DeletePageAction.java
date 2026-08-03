/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action;

import ie.philb.album.AppSession;
import ie.philb.album.model.AlbumModel;

/**
 *
 * @author philb
 */
public class DeletePageAction extends AbstractAction<Void> {

    private final int pageId;

    public DeletePageAction(AppSession session, int pageId) {
        super(session);
        this.pageId = pageId;

    }

    @Override
    protected Void doAction() throws Exception {

        AlbumModel albumModel = session.getAlbumModel();
        albumModel.deletePage(pageId);
        session.getEventBus().albumUpdated();

        return null;
    }

}
