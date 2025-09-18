/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageModel;
import java.awt.Color;
import java.awt.Point;
import java.io.File;

/**
 *
 * @author philb
 */
public class AlbumReader {

    // TODO:
    //   default margin and gutter
    //   image offset
    //   brightness adjustment
    //   greyscale
    //   page margin and gutter
    private final File file;

    public AlbumReader(File file) {
        this.file = file;
    }

    public AlbumModel read() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        AlbumData albumData = mapper.readValue(file, AlbumData.class);

        AlbumModel albumModel = new AlbumModel(albumData.getPageSize(), 0, 0);

        for (PageData page : albumData.getPages()) {
            PageModel pageModel = new PageModel(page.pageGeometry(), albumData.getPageSize());

            for (int i = 0; i < page.cells().size(); i++) {
                PageEntryModel pem = pageModel.getPageEntries().get(i);
                CellData cellData = page.cells().get(i);

                pem.setPageEntryType(cellData.pageEntryType());

                if (!cellData.fileName().isBlank()) {
                    pem.setImageFile(new File(cellData.fileName()));
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

            albumModel.addPage(pageModel);
        }

        return albumModel;
    }
}
