/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action;

import ie.philb.album.AppContext;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageEntryType;
import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageModel;
import ie.philb.album.ui.dialog.NewAlbumParams;
import java.time.LocalDateTime;

/**
 *
 * @author philb
 */
public class NewAlbumAction extends AbstractAction<Void> {

    private final PageGeometry defaultPageGeometry = PageGeometry.square(2);

    private final NewAlbumParams params;

    public NewAlbumAction(NewAlbumParams params) {
        this.params = params;
    }

    @Override
    protected Void doAction() throws Exception {

        AlbumModel albumModel = new AlbumModel(params.pageSize(), params.margin(), params.gutter());

        // The title page - single text cell
        PageGeometry geometry = PageGeometry.square(PageEntryType.Text, 1);
        albumModel.addPage(0, geometry, 0, 0);

        PageModel titlePage = albumModel.getPages().get(0);
        PageEntryModel titleEntry = titlePage.getPageEntries().get(0);
        titleEntry.getTextControlModel().setText(params.title());

        for (int i = 1; i <= params.pages(); i++) {
            albumModel.addPage(i, defaultPageGeometry);
            albumModel.setLastSaveDate(LocalDateTime.now());
        }

        AppContext.INSTANCE.setAlbumModel(albumModel);
        return null;
    }
}
