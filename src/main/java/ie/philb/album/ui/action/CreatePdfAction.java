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
import ie.philb.album.model.PageGeometryMapper;
import ie.philb.album.model.PageModel;
import ie.philb.album.util.ImageUtils;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philip.Bradley
 */
public class CreatePdfAction extends AbstractAction<File> {

    private static final Logger LOG = LoggerFactory.getLogger(CreatePdfAction.class);

    private final AlbumModel albumModel;

    private static final double MILLIS_TO_INCH = 0.0393701d;

    public CreatePdfAction(AlbumModel albumModel) {
        this.albumModel = albumModel;
    }

    @Override
    protected File execute() throws Exception {

        logger.info("Creating doc...");
        File outFile = null;

        // Model page is measured in mm
        // PDF page size is measured in  inch/72
        Dimension modelPageSize = albumModel.getPageSize().size();
        Dimension pageSize = new Dimension(millisToUnits(modelPageSize.width), millisToUnits(modelPageSize.height));

        try (Document doc = new Document(getPageSize(albumModel))) {
            outFile = File.createTempFile("album-", ".pdf");

            logger.info("Creating doc {} with page size {}", outFile.getAbsolutePath(), doc.getPageSize());

            // Creating the writer implicitly causes the doc to be written to the file
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(outFile));
            doc.open();

            for (PageModel pageModel : albumModel.getPages()) {

                PageGeometryMapper geometryMapper = new PageGeometryMapper(pageModel, pageSize);
                geometryMapper.setOriginLocation(PageGeometryMapper.OriginLocation.SouthWest);

                for (PageEntryModel pageEntryModel : pageModel.getPageEntries()) {
                    Dimension cellSize = geometryMapper.getCellSizeOnView(pageEntryModel);
                    BufferedImage viewImage = pageEntryModel.getViewImage(cellSize);

                    Point cellLocation = geometryMapper.getCellLocationOnView(pageEntryModel.getCell());
                    Point imageOffset = ImageUtils.getCenteredCoordinates(viewImage, cellSize);
                    Point imageLocation = new Point(cellLocation.x + imageOffset.x, cellLocation.y + imageOffset.y);

                    if (viewImage == null) {
                        viewImage = ImageIO.read(this.getClass().getResourceAsStream("/ie/philb/album/placeholder.png"));
                    }

                    Image img = Image.getInstance(viewImage, null);

                    // Centre image if it's less tall or less wide than the available space
//                    int xOffset = (imageSize.width - albumImage.getWidth()) / 2;
//                    int yOffset = (imageSize.height - albumImage.getHeight()) / 2;
                    img.setAbsolutePosition(imageLocation.x, imageLocation.y);

//                    Rectangle rect = new Rectangle(cell.location().x, cell.location().y, cell.location().x + cell.size().width, cell.location().y + cell.size().height);
//                    rect.setBorder(Rectangle.BOX);
//                    rect.setBorderWidth(2);
//                    rect.setBorderColor(Color.MAGENTA);
//
//                    doc.add(rect);
//                    img.setBorder(Rectangle.BOX);
//                    img.setBorderColor(Resources.COLOR_PHOTO_BORDER);
//                    img.setBorderWidth(0.01f);
//                    LOG.info("Setting image pos {} with size {}", imageLocation, imageSize);
                    doc.add(img);
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
