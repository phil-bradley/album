/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.exporter;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageEntryType;
import ie.philb.album.model.PageGeometryMapper;
import ie.philb.album.model.PageModel;
import ie.philb.album.ui.common.font.ApplicationFont;
import ie.philb.album.ui.common.textcontrol.TextControlModel;
import ie.philb.album.util.ImageUtils;
import static ie.philb.album.util.ImageUtils.getImageSize;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author philb
 */
public class OpenPdfExporter implements AlbumExporter {

    private static final Logger LOG = LoggerFactory.getLogger(OpenPdfExporter.class);

    private AlbumModel album;
    private File file;

    public OpenPdfExporter(AlbumModel album) {
        this.album = album;
    }

    @Override
    public void export(File file) throws Exception {

        try (Document doc = new Document(getPageSize()); PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(file))) {

            doc.open();

            for (PageModel pageModel : album.getPages()) {

                PageGeometryMapper geometryMapper = new PageGeometryMapper(pageModel, pageModel.getPageSize().size());
                geometryMapper.setOriginLocation(PageGeometryMapper.OriginLocation.SouthWest);

                for (PageEntryModel pageEntryModel : pageModel.getPageEntries()) {
                    writePageEntry(doc, writer, geometryMapper, pageEntryModel);
                }

                doc.newPage();
            }

        } catch (Exception ex) {
            throw new Exception("Error creating PDF", ex);
        }
    }

    private void writePageEntry(Document doc, PdfWriter writer, PageGeometryMapper geometryMapper, PageEntryModel pem) throws Exception {

        if (pem.getPageEntryType() == PageEntryType.Text) {
            writeTextPageEntry(doc, writer, geometryMapper, pem);
        } else {
            writeImagePageEntry(doc, writer, geometryMapper, pem);
        }
    }

    private Rectangle getPageSize() {
        return new Rectangle((float) album.getPageSize().width(), (float) album.getPageSize().height());
    }

    private void writeTextPageEntry(Document doc, PdfWriter writer, PageGeometryMapper geometryMapper, PageEntryModel pageEntryModel) throws Exception {

        TextControlModel tcm = pageEntryModel.getTextControlModel();
        String text = tcm.getText();
        ApplicationFont appFont = ApplicationFont.byFamilyName(tcm.getFontFamily());
        int fontSize = tcm.getFontSize();
        BaseFont font = loadFont(appFont, tcm.isBold(), tcm.isItalic());
        Color fontColor = tcm.getFontColor();

        Point cellLocation = geometryMapper.getCellLocationOnView(pageEntryModel.getCell());
        Dimension cellSize = geometryMapper.getCellSizeOnView(pageEntryModel);

        int centerX = cellLocation.x + (cellSize.width / 2);
        int centerY = cellLocation.y + (cellSize.height / 2);

        PdfContentByte cb = writer.getDirectContent();
        cb.setColorFill(fontColor);
        cb.beginText();
        cb.setFontAndSize(font, fontSize);
        cb.showTextAligned(Element.ALIGN_CENTER, text, centerX, centerY, 0);
        cb.endText();

        // Underline is not handled directly, we just have to draw a horizontal line manually
        if (tcm.isUnderline()) {
            float textWidth = font.getWidthPoint(text, fontSize);
            float underlineY = centerY - 4f;
            float underlineX = centerX - (textWidth / 2);

            // Draw the line
            cb.setLineWidth(2f);
            cb.setColorStroke(fontColor);
            cb.moveTo(underlineX, underlineY);
            cb.lineTo(underlineX + textWidth, underlineY);
            cb.stroke();
        }
    }

    private void writeImagePageEntry(Document doc, PdfWriter writer, PageGeometryMapper geometryMapper, PageEntryModel pageEntryModel) throws Exception {

        if (pageEntryModel.getImage() == null) {
            return;
        }

        Dimension cellSize = geometryMapper.getCellSizeOnView(pageEntryModel);
        double cellAspectRatio = ImageUtils.getAspectRatio(cellSize);

        Dimension imageSize = getImageSize(pageEntryModel.getImage());

        // Create a bounding box with size >= original image size so that no
        // scaling down is done. Scaling is done by the PDF using scaleToFit
        Dimension boundingBoxSize = ImageUtils.getBoundingBoxWithAspectRatio(imageSize, cellAspectRatio);
        BufferedImage scaledImage = pageEntryModel.getScaledImage(boundingBoxSize, geometryMapper);

        // Now we have a scaled image, we need to crop it using scaled offsets
        double cellToBoundingBoxScale = (double) boundingBoxSize.height / (double) cellSize.height;
        Point scaledOffset = new Point(pageEntryModel.getImageViewOffset());
        scaledOffset.x = (int) (scaledOffset.x * cellToBoundingBoxScale);
        scaledOffset.y = (int) (scaledOffset.y * cellToBoundingBoxScale);

        BufferedImage cropped = ImageUtils.getSubimage(scaledImage, scaledOffset, boundingBoxSize);

        Image img = Image.getInstance(cropped, null);
        img.scaleToFit(cellSize.width, cellSize.height);

        Point imageOffset = geometryMapper.locationAsPointsToViewUnits(pageEntryModel.getImageViewOffset());

        if (imageOffset.x < 0) {
            imageOffset.x = 0;
        }

        if (imageOffset.y < 0) {
            imageOffset.y = 0;
        }

        Point cellLocation = geometryMapper.getCellLocationOnView(pageEntryModel.getCell());
        Point imageLocation = new Point(cellLocation.x + imageOffset.x, cellLocation.y - imageOffset.y);

        LOG.info("PDF view size: {}x{}, Image size {}x{}, Model offset is {},{} scaling to {},{}", cellSize.width, cellSize.height, img.getWidth(), img.getHeight(), pageEntryModel.getImageViewOffset().x, pageEntryModel.getImageViewOffset().y, imageOffset.x, imageOffset.y);

        LOG.info("Adding image with size {}x{} at [{},{}]", cellSize.width, cellSize.height, imageLocation.x, imageLocation.y);
        img.setAbsolutePosition(imageLocation.x, imageLocation.y);
        doc.add(img);
    }

    private BaseFont loadFont(ApplicationFont applicationFont, boolean bold, boolean italic) throws Exception {

        File tempFontFile = File.createTempFile("tempfont", ".ttf");
        tempFontFile.deleteOnExit();

        try (InputStream is = getClass().getResourceAsStream(applicationFont.getFontPath(bold, italic))) {
            Files.copy(is, tempFontFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        BaseFont font = BaseFont.createFont(tempFontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return font;
    }
}
