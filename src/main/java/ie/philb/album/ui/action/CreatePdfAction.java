/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import ie.philb.album.ui.page.PageEntry;
import ie.philb.album.ui.page.PageLayout;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philip.Bradley
 */
public class CreatePdfAction extends AbstractAction<Void> {

    private final List<PageLayout> pageLayouts = new ArrayList<>();

    private static final double MILLIS_TO_INCH = 0.0393701d;

    public CreatePdfAction(List<PageLayout> layouts) {
        this.pageLayouts.addAll(layouts);
    }

    @Override
    protected Void execute() throws Exception {

        logger.info("Creating doc...");
        try (Document doc = new Document(PageSize.A4.rotate())) {
            logger.info("Doc has size " + doc.getPageSize());

            // Creating the writer implicitly causes the doc to be written to the file
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("hello.pdf"));
            doc.open();

            for (PageLayout pageLayout : pageLayouts) {
                for (PageEntry pageEntry : pageLayout.getPageEntries()) {

                    if (pageEntry.getIcon() == null) {
                        continue;
                    }

                    Image img = Image.getInstance(pageEntry.getIcon().getImage(), null);

                    int widthUnits = millisToUnits(pageEntry.getWidth());
                    int heightUnits = millisToUnits(pageEntry.getHeight());

                    int xOffsetUnits = millisToUnits(pageEntry.getOffsetX());
                    int yOffsetUnits = millisToUnits(pageEntry.getOffsetY());

                    img.scaleToFit(widthUnits, heightUnits);
                    //img.scaleAbsolute(widthUnits, heightUnits);
                    img.setAbsolutePosition(xOffsetUnits, yOffsetUnits);

                    doc.add(img);

                }

                doc.newPage();
            }
            
            writer.close();
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
