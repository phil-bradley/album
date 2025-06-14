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
import static ie.philb.album.util.ImageUtils.getImageSize;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    private static final double MILLIS_TO_INCH = 0.0393701d;

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
        Dimension pageSize = new Dimension(millisToUnits(modelPageSize.width), millisToUnits(modelPageSize.height));

        try (Document doc = new Document(getPageSize(albumModel))) {

            logger.info("Creating doc {} with page size {}", file.getAbsolutePath(), doc.getPageSize());

            // Creating the writer implicitly causes the doc to be written to the file
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();

            for (PageModel pageModel : albumModel.getPages()) {

                PageGeometryMapper geometryMapper = new PageGeometryMapper(pageModel, pageSize);
                geometryMapper.setOriginLocation(PageGeometryMapper.OriginLocation.SouthWest);

                for (PageEntryModel pageEntryModel : pageModel.getPageEntries()) {
                    Dimension cellSize = geometryMapper.getCellSizeOnView(pageEntryModel);
                    double cellAspectRatio = ImageUtils.getAspectRatio(cellSize);

                    Dimension imageSize = getImageSize(pageEntryModel.getImage());
                    Dimension boundingBoxSize = ImageUtils.getBoundingBoxWithAspectRatio(imageSize, cellAspectRatio);
                    BufferedImage viewImage = pageEntryModel.getViewImage(boundingBoxSize, geometryMapper);

                    if (viewImage == null) {
                        continue;
                    }

                    Image img = Image.getInstance(viewImage, null);
                    img.scaleToFit(cellSize.width, cellSize.height);

                    Point cellLocation = geometryMapper.getCellLocationOnView(pageEntryModel.getCell());
                    Point imageOffset = ImageUtils.getCenteredCoordinates(getImageSize(viewImage), cellSize);
                    Point imageLocation = new Point(cellLocation.x + imageOffset.x, cellLocation.y + imageOffset.y);

                    //LOG.info("Adding image with size {}x{} at {}x{}", cellSize.width, cellSize.height, imageLocation.x, imageLocation.y);
                    img.setAbsolutePosition(imageLocation.x, imageLocation.y);
                    doc.add(img);
                }

                doc.newPage();
            }

            doc.close();
            writer.close();

        } catch (DocumentException | IOException ex) {

        }

        return file;
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
