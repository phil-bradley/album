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
import java.awt.Color;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Philip.Bradley
 */
public class CreatePdfAction extends AbstractAction<Void> {

    private final List<PageLayout> pageLayouts = new ArrayList<>();

    public CreatePdfAction(List<PageLayout> layouts) {
        this.pageLayouts.addAll(layouts);
    }

    @Override
    protected Void execute() throws Exception {

        logger.info("Creating doc...");
        Document doc = new Document(PageSize.A4.rotate());
        logger.info("Doc has size " + doc.getPageSize());

        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("hello.pdf"));
        doc.open();
        //doc.add(new Paragraph("Howdy " + new Date().toString()));

        // Unit = inch/72
        // mm to inch = 0.0393701
        // X mm to units = X * 72 * 0.0393701
        for (PageLayout pageLayout : pageLayouts) {
            for (PageEntry pageEntry : pageLayout.getPageEntries()) {
                
                if (pageEntry.getIcon() == null) {
                    continue;
                }
                
//                Image img = Image.getInstance("c:/phil/media/album/test-001.jpg");
                Image img = Image.getInstance(pageEntry.getIcon().getImage(), Color.white);
                
                int width = (int) (pageEntry.getWidth() * 72d * 0.0393701d);
                int height = (int) (pageEntry.getHeight() * 72d * 0.0393701d);

                int x = (int) (pageEntry.getOffsetX() * 72d * 0.0393701d);
                int y = (int) (pageEntry.getOffsetY()* 72d * 0.0393701d);
                
                img.scaleToFit(width, height);
                img.setAbsolutePosition(x, y);
                
                doc.add(img);
                
            }
            
            doc.newPage();
        }

        doc.close();

        return null;

    }

}
