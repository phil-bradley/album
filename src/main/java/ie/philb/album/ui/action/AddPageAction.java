/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action;

import ie.philb.album.AppSession;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageGeometryOption;
import ie.philb.album.model.PageModel;
import ie.philb.album.view.PageView;
import java.util.List;

/**
 *
 * @author philb
 */
public class AddPageAction extends AbstractAction<PageModel> {

    private int pageId;

    public AddPageAction(AppSession session) {
        super(session);
    }

    @Override
    protected PageModel doAction() throws Exception {
        AlbumModel albumModel = session.getAlbumModel();
        PageView selectedPageView = session.getSelectedPageView();
        List<PageModel> pages = albumModel.getPages();

        pageId = pages.size();

        if (selectedPageView != null) {
            pageId = selectedPageView.getPageModel().getPageId() + 1;
        }

        PageGeometry lastPageGeometry = PageGeometryOption.Columns_2_1.geometry();

        if (!pages.isEmpty()) {
            PageModel lastPage = pages.get(pages.size() - 1);
            lastPageGeometry = lastPage.getGeometry();
        }

        albumModel.addPage(pageId, lastPageGeometry);
        session.getEventBus().albumUpdated();

        List<PageModel> filtered = albumModel.getPages().stream().filter(m -> m.getPageId() == pageId).toList();

        if (filtered.size() != 1) {
            throw new IllegalStateException("Expected 1 entry with pageId " + pageId + " but found " + filtered.size());
        }

        return filtered.get(0);
    }

}
