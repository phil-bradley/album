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
import java.awt.Point;
import java.io.File;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author philb
 */
public class AlbumDataMapper {

    public AlbumModel map(AlbumData albumData) {
        AlbumModel albumModel = new AlbumModel(albumData.getPageSize(), albumData.getDefaultMargin(), albumData.getDefaultGutter());

        for (PageData page : albumData.getPages()) {
            PageModel pageModel = new PageModel(page.pageGeometry(), albumData.getPageSize());
            pageModel.setHorizontalMargin(page.horizontalMargin());
            pageModel.setVerticalMargin(page.verticalMargin());
            pageModel.setGutter(page.gutter());

            for (int i = 0; i < page.cells().size(); i++) {
                PageEntryModel pem = pageModel.getPageEntries().get(i);
                CellData cellData = page.cells().get(i);
                applyCellData(cellData, pem);
            }

            albumModel.addPage(pageModel);
        }

        return albumModel;
    }

    private void applyCellData(CellData cellData, PageEntryModel pem) {

        pem.setPageEntryType(cellData.pageEntryType());

        if (!cellData.fileName().isBlank()) {
            File imageFile = new File(cellData.fileName());
            if (imageFile.exists()) {
                pem.setImageFile(imageFile);
            }
        }

        pem.setZoomFactor(cellData.zoom());
        pem.setGrayScale(cellData.isGreyScale());
        pem.setBrightnessAdjustment(cellData.brightness());
        pem.setImageViewOffset(new Point(cellData.offsetX(), cellData.offsetY()));
        pem.setCentered(cellData.isCentered());

        pem.getTextControlModel().setText(cellData.text());
        pem.getTextControlModel().setBold(cellData.bold());
        pem.getTextControlModel().setItalic(cellData.italic());
        pem.getTextControlModel().setUnderline(cellData.underline());
        pem.getTextControlModel().setFontColor(Color.decode(cellData.fontColor()));
        pem.getTextControlModel().setFontSize(cellData.fontSize());
        pem.getTextControlModel().setFontFamily(cellData.fontFamily());
    }

    public AlbumData map(AlbumModel albumModel) {

        AlbumData albumData = new AlbumData();
        albumData.setName("test");
        albumData.setCreated(ZonedDateTime.now());
        albumData.setLastUpdated(ZonedDateTime.now());
        albumData.setCreatedBy(System.getProperty("user.name"));
        albumData.setPageSize(albumModel.getPageSize());
        albumData.setDefaultMargin(albumModel.getDefaultMargin());
        albumData.setDefaultGutter(albumModel.getDefaultGutter());

        for (PageModel pageModel : albumModel.getPages()) {
            PageData pageData = new PageData(
                    getPageCells(pageModel),
                    pageModel.getGeometry(),
                    pageModel.getVerticalMargin(),
                    pageModel.getHorizontalMargin(),
                    pageModel.getGutter()
            );
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
