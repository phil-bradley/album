/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
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
import ie.philb.album.util.ImageUtils;
import static ie.philb.album.util.ImageUtils.getImageSize;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philip.Bradley
 */
public class CreatePdfAction extends AbstractAction<File> {

    private static final Logger LOG = LoggerFactory.getLogger(CreatePdfAction.class);

    private final AlbumModel albumModel;
    private final File file;

    public CreatePdfAction(File file, AlbumModel albumModel) {
        this.albumModel = albumModel;
        this.file = file;
    }

    @Override
    protected File doAction() throws Exception {

        logger.info("Creating doc...");

        // Model page is measured in mm
        // PDF page size is measured in  inch/72
        Dimension modelPageSize = albumModel.getPageSize().size();
        Dimension pageSize = new Dimension(modelUnitsToPageUnits(modelPageSize.width), modelUnitsToPageUnits(modelPageSize.height));

        try (Document doc = new Document(getPageSize(albumModel))) {

            logger.info("Creating doc {} with page size {}", file.getAbsolutePath(), doc.getPageSize());

            // Creating the writer implicitly causes the doc to be written to the file
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();

            for (PageModel pageModel : albumModel.getPages()) {

                PageGeometryMapper geometryMapper = new PageGeometryMapper(pageModel, pageSize);
                geometryMapper.setOriginLocation(PageGeometryMapper.OriginLocation.SouthWest);

                for (PageEntryModel pageEntryModel : pageModel.getPageEntries()) {

                    Point cellLocation = geometryMapper.getCellLocationOnView(pageEntryModel.getCell());

                    if (pageEntryModel.getPageEntryType() == PageEntryType.Text) {
                        String text = pageEntryModel.getTextControlModel().getText();
                        String fontFamilyName = pageEntryModel.getTextControlModel().getFontFamily();
                        int fontSize = pageEntryModel.getTextControlModel().getFontSize();

                        int centerX = cellLocation.x + (pageEntryModel.getPhysicalSize().width / 2);
                        int centerY = cellLocation.y + (pageEntryModel.getPhysicalSize().height / 2);

                        ApplicationFont appFont = ApplicationFont.byFamilyName(fontFamilyName);
                        BaseFont font = loadFont(appFont);

                        PdfContentByte canvas = writer.getDirectContent();
                        canvas.setColorFill(pageEntryModel.getTextControlModel().getFontColor());
                        canvas.beginText();
                        canvas.setFontAndSize(font, fontSize);
                        canvas.showTextAligned(Element.ALIGN_CENTER, text, centerX, centerY, 0);
                        canvas.endText();

                        continue;
                    }

                    if (pageEntryModel.getImage() == null) {
                        continue;
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

                    Point imageLocation = new Point(cellLocation.x + imageOffset.x, cellLocation.y - imageOffset.y);

                    LOG.info("PDF view size: {}x{}, Image size {}x{}, Model offset is {},{} scaling to {},{}", cellSize.width, cellSize.height, img.getWidth(), img.getHeight(), pageEntryModel.getImageViewOffset().x, pageEntryModel.getImageViewOffset().y, imageOffset.x, imageOffset.y);

                    LOG.info("Adding image with size {}x{} at [{},{}]", cellSize.width, cellSize.height, imageLocation.x, imageLocation.y);
                    img.setAbsolutePosition(imageLocation.x, imageLocation.y);
                    doc.add(img);
                }

                doc.newPage();
            }

            doc.close();
            writer.close();

        } catch (DocumentException | IOException ex) {
            LOG.info("Error creating pdf", ex);
        }

        return file;
    }

    private int modelUnitsToPageUnits(int units) {
        // Model and page both use points, no conversion required
        return units;
    }

    private Rectangle getPageSize(AlbumModel am) {
        return PageSize.A4.rotate();
    }

    private BaseFont loadFont(ApplicationFont applicationFont) throws Exception {

        File tempFontFile = File.createTempFile("tempfont", ".ttf");
        tempFontFile.deleteOnExit();

        try (InputStream is = getClass().getResourceAsStream(applicationFont.getFontPath())) {
            Files.copy(is, tempFontFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        BaseFont font = BaseFont.createFont(tempFontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        return font;
    }
}
