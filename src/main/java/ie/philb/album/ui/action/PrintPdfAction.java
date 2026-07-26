/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action;

import ie.philb.album.AppSession;
import java.awt.print.PrinterJob;
import java.io.File;
import javax.print.PrintService;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

/**
 *
 * @author philb
 */
public class PrintPdfAction extends AbstractAction<Void> {

    private final File file;

    public PrintPdfAction(AppSession session, File file) {
        super(session);
        this.file = file;
    }

    @Override
    protected Void doAction() throws Exception {

        PDDocument document = Loader.loadPDF(file);

        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService printService = null;

        if (job.printDialog()) {
            printService = job.getPrintService();
        }

        if (printService == null) {
            throw new Exception("Printer not found");
        }

        job.setPrintService(printService);

        job.setPageable(new PDFPageable(document));
        job.print();

        return null;
    }

}
