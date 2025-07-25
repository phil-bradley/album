/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageEntryType;
import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageModel;
import ie.philb.album.model.PageSize;
import ie.philb.album.ui.common.Dialogs;
import java.time.LocalDateTime;

/**
 *
 * @author philb
 */
public class NewAlbumCommand extends AbstractCommand {

    private final int defaultMargin = 10;
    private final int defaultGutter = 25;

    @Override
    public void execute() {

        AlbumModel albumModel = AppContext.INSTANCE.getAlbumModel();

        if (albumModel != null && albumModel.hasUnSavedChanges()) {

            boolean ok = Dialogs.confirm("Are you sure?");

            if (!ok) {
                return;
            }
        }

        albumModel = new AlbumModel(PageSize.A4_Landscape, defaultMargin, defaultGutter);

        // The title page - single text cell
        PageGeometry geometry = PageGeometry.square(PageEntryType.Text, 1);
        albumModel.addPage(geometry, 0, 0);

        PageModel titlePage = albumModel.getPages().get(0);
        PageEntryModel titleEntry = titlePage.getPageEntries().get(0);
        titleEntry.getTextControlModel().setText("The Title!");

        // First album page, 2x2 cells by default
        albumModel.addPage(PageGeometry.square(2));
        albumModel.setLastSaveDate(LocalDateTime.now());

        AppContext.INSTANCE.setAlbumModel(albumModel);
    }
}
