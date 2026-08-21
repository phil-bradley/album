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

        if (albumModel.getPages().isEmpty()) {
            throw new IllegalArgumentException("Cannot delete page from empty album");
        }

        if (pageId < 0) {
            throw new IllegalArgumentException("Cannot delete page < 0: " + pageId);
        }

        int maxPage = albumModel.getPages().size();

        if (pageId >= maxPage) {
            throw new IllegalArgumentException("Cannot delete page > " + maxPage + ": " + pageId);
        }

        albumModel.deletePage(pageId);
        session.getEventBus().albumUpdated();

        return null;
    }

}
