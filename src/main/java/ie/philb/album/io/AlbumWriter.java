/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageCell;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageModel;
import java.io.File;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author philb
 */
public class AlbumWriter {

    private final AlbumModel albumModel;
    private final File file;

    public AlbumWriter(File file, AlbumModel albumModel) {
        this.file = file;
        this.albumModel = albumModel;
    }

    public void write() throws Exception {
        AlbumData albumData = getData();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.writeValue(file, albumData);
    }

    private AlbumData getData() {

        AlbumData albumData = new AlbumData();
        albumData.setName("test");
        albumData.setCreated(ZonedDateTime.now());
        albumData.setLastUpdated(ZonedDateTime.now());
        albumData.setCreatedBy(System.getProperty("user.name"));

        for (PageModel pageModel : albumModel.getPages()) {
            PageData pageData = new PageData(getPageCells(pageModel));
            albumData.getPages().add(pageData);
        }

        return albumData;
    }

    private List<CellData> getPageCells(PageModel pageModel) {
        List<CellData> cellData = new ArrayList<>();
        for (PageEntryModel pem : pageModel.getPageEntries()) {
            cellData.add(mapCell(pem));
        }

        return cellData;
    }

    private CellData mapCell(PageEntryModel entry) {
        PageCell cell = entry.getCell();
        String imagePath = "";
        if (entry.getImageFile() != null) {
            imagePath = entry.getImageFile().getAbsolutePath();
        }

        CellData cellData = new CellData(cell.size().width, cell.size().height, cell.location().x, cell.location().y, imagePath, entry.getZoomFactor());
        return cellData;
    }
}
