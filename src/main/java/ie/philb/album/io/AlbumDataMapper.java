/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.io;

import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageCell;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageModel;
import java.awt.Color;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author philb
 */
public class AlbumDataMapper {

    public AlbumData map(AlbumModel albumModel) {

        AlbumData albumData = new AlbumData();
        albumData.setName("test");
        albumData.setCreated(ZonedDateTime.now());
        albumData.setLastUpdated(ZonedDateTime.now());
        albumData.setCreatedBy(System.getProperty("user.name"));
        albumData.setPageSize(albumModel.getPageSize());

        for (PageModel pageModel : albumModel.getPages()) {
            PageData pageData = new PageData(getPageCells(pageModel), pageModel.getGeometry());
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

        CellData cellData = new CellData(
                cell.size().width,
                cell.size().height,
                cell.location().x,
                cell.location().y,
                entry.getPageEntryType(),
                imagePath,
                entry.getZoomFactor(),
                entry.isGrayScale(),
                entry.isCentered(),
                entry.getImageViewOffset().x,
                entry.getImageViewOffset().y,
                entry.getBrightnessAdjustment(),
                entry.getTextControlModel().getText(),
                entry.getTextControlModel().getFontFamily(),
                entry.getTextControlModel().getFontSize(),
                encodeColor(entry.getTextControlModel().getFontColor()),
                entry.getTextControlModel().isItalic(),
                entry.getTextControlModel().isBold(),
                entry.getTextControlModel().isUnderline()
        );
        return cellData;
    }

    private String encodeColor(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

}
