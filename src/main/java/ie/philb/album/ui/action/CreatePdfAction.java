/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageModel;
import ie.philb.album.ui.common.Resources;
import ie.philb.album.ui.page.PageSpecification;
import ie.philb.album.view.PageEntryCoordinates;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Philip.Bradley
 */
public class CreatePdfAction extends AbstractAction<File> {

    private final AlbumModel albumModel;

    private static final double MILLIS_TO_INCH = 0.0393701d;

    public CreatePdfAction(AlbumModel albumModel) {
        this.albumModel = albumModel;
    }

    @Override
    protected File execute() throws Exception {

        logger.info("Creating doc...");
        File outFile = null;

        try (Document doc = new Document(getPageSize(albumModel))) {
            outFile = File.createTempFile("album-", "pdf");

            logger.info("Doc has size " + doc.getPageSize());

            // Creating the writer implicitly causes the doc to be written to the file
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(outFile));
            doc.open();

            for (PageModel pageModel : albumModel.getPages()) {

                int i = 0;
                for (PageEntryModel pageEntryModel : pageModel.getPageEntries()) {

                    PageSpecification pageSpecification = pageModel.getLayout().getPageSpecification();
                    PageEntryCoordinates coordinates = pageModel.getLayout().getEntryCoordinates().get(i);

                    java.awt.Image albumImage = null;

                    if (pageEntryModel != null) {
                        albumImage = pageEntryModel.getImageIcon().getImage();
                    }

                    if (albumImage == null) {
                        albumImage = ImageIO.read(this.getClass().getResourceAsStream("/ie/philb/album/placeholder.png"));
                    }

                    Image img = Image.getInstance(albumImage, null);
                    int widthUnits = millisToUnits(coordinates.width());
                    int heightUnits = millisToUnits(coordinates.height());
                    int xOffsetUnits = millisToUnits(coordinates.offsetX());

                    // Coordinate system in the spec starts at top left, openpdf starts at bottom left
                    int yOffsetUnits = millisToUnits(pageSpecification.height() - (coordinates.height() + coordinates.offsetY()));

                    //img.scaleToFit(widthUnits, heightUnits);
                    img.scaleAbsolute(widthUnits, heightUnits);

                    img.setAbsolutePosition(xOffsetUnits, yOffsetUnits);

                    img.setBorder(Rectangle.BOX);
                    img.setBorderColor(Resources.COLOR_PHOTO_BORDER);
                    img.setBorderWidth(0.01f);

                    doc.add(img);
                    i++;
                }

                doc.newPage();
            }

            doc.close();
            writer.close();
        } catch (DocumentException | IOException ex) {

        }

        return outFile;
    }

    private int millisToUnits(int millis) {
        // Unit = inch/72
        // mm to inch = 0.0393701
        // X mm to units = X * 72 * 0.0393701

        return (int) (millis * 72d * MILLIS_TO_INCH);
    }

    private Rectangle getPageSize(AlbumModel am) {
        return PageSize.A4.rotate();
    }

}
