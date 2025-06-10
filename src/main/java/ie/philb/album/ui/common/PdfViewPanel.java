/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author philb
 */
public class PdfViewPanel extends AppPanel {

    private static final Logger LOG = LoggerFactory.getLogger(PdfViewPanel.class);

    private static final long serialVersionUID = 1L;
    private int currentPage = 0;
    private PDDocument document;
    private PDFRenderer renderer;
    private int numberPages = 0;
    private float scale = 1;
    private final ImagePanel imagePanel;

    public PdfViewPanel() {
        setBackground(Color.darkGray);
        setOpaque(true);

        this.imagePanel = new ImagePanel(null);

        GridBagCellConstraints gbc = new GridBagCellConstraints(0, 0).weight(1).anchorNorth().fillBoth().inset(0);
        add(imagePanel, gbc);
    }

    public void showPdf(File file) throws IOException {
        document = Loader.loadPDF(file);
        showPdf(document);
    }

    public void showPdf(PDDocument document) {
        this.document = document;
        this.renderer = new PDFRenderer(document);
        this.numberPages = document.getNumberOfPages();

        showPdfPage(0);
    }

    public void showPdfPage(int pageNumber) {
        BufferedImage pageImage;
        try {
            pageImage = renderer.renderImage(pageNumber);
            imagePanel.setIcon(new ImageIcon(pageImage));
            repaint();
        } catch (IOException ex) {

        }
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void nextPage() {
        if (currentPage < numberPages - 1) {
            currentPage++;
            showPdfPage(currentPage);
        }
    }

    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            showPdfPage(currentPage);
        }
    }

    public void printDocument() throws PrinterException {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));
        job.print();
    }
}
