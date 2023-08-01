/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import ie.philb.album.ui.common.Resources;
import ie.philb.album.ui.page.Page;
import ie.philb.album.ui.page.PagePanelEntry;
import ie.philb.album.util.ImageUtils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philip.Bradley
 */
public class CreatePdfAction extends AbstractAction<Void> {

    private final List<Page> pages = new ArrayList<>();

    private static final double MILLIS_TO_INCH = 0.0393701d;

    public CreatePdfAction(List<Page> pages) {
        this.pages.addAll(pages);
    }

    @Override
    protected Void execute() throws Exception {

        logger.info("Creating doc...");
        try (Document doc = new Document(PageSize.A4.rotate())) {
            logger.info("Doc has size " + doc.getPageSize());

            // Creating the writer implicitly causes the doc to be written to the file
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("hello.pdf"));
            doc.open();

            for (Page page : pages) {
                for (PagePanelEntry pagePanelEntry : page.getPagePanelEntries()) {

                    java.awt.Image croppedImage = pagePanelEntry.imagePanel.getCroppedImage();

                    if (croppedImage == null) {
                        croppedImage = ImageUtils.createImage(new Dimension(20, 20), Color.white);
                    }

                    Image img = Image.getInstance(croppedImage, null);

                    int widthUnits = millisToUnits(pagePanelEntry.pageEntry.getWidth());
                    int heightUnits = millisToUnits(pagePanelEntry.pageEntry.getHeight());

                    int xOffsetUnits = millisToUnits(pagePanelEntry.pageEntry.getOffsetX());
                    int yOffsetUnits = millisToUnits(pagePanelEntry.pageEntry.getOffsetY());

                    img.scaleToFit(widthUnits, heightUnits);
                    img.setAbsolutePosition(xOffsetUnits, yOffsetUnits);

                    img.setBorder(Rectangle.BOX);
                    img.setBorderColor(Resources.COLOR_PHOTO_BORDER);
                    img.setBorderWidth(0.01f);

                    doc.add(img);

                }

                doc.newPage();
            }
        }

        return null;

    }

    private int millisToUnits(int millis) {
        // Unit = inch/72
        // mm to inch = 0.0393701
        // X mm to units = X * 72 * 0.0393701

        return (int) (millis * 72d * MILLIS_TO_INCH);
    }

}
