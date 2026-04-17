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

    private final int defaultMargin;
    private final int defaultGutter;
    private final int pageCount;
    private final PageSize pageSize;
    private final String title;
    private final PageGeometry defaultPageGeometry;

    public NewAlbumCommand() {
        this(10, 25, 10, PageSize.A4_Landscape, "The Title", PageGeometry.square(2));
    }

    public NewAlbumCommand(int defaultMargin, int defaultGutter, int pageCount, PageSize pageSize, String title, PageGeometry pageGeometry) {
        this.defaultMargin = defaultMargin;
        this.defaultGutter = defaultGutter;
        this.pageCount = pageCount;
        this.pageSize = pageSize;
        this.title = title;
        this.defaultPageGeometry = pageGeometry;
    }

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

        for (int i = 0; i < pageCount; i++) {
            albumModel.addPage(defaultPageGeometry);
            albumModel.setLastSaveDate(LocalDateTime.now());
        }

        AppContext.INSTANCE.setAlbumModel(albumModel);
    }
}
