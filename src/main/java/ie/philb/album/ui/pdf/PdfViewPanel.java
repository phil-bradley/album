/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.pdf;

import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.util.ImageUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
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
    private int pageCount = 0;
    private float scale = 1;
    private BufferedImage pageImage = null;

    public PdfViewPanel() {
        setBackground(Color.darkGray);
        setOpaque(true);
    }

    public void showPdf(File file) throws IOException {
        showPdf(Loader.loadPDF(file));
    }

    public void showPdf(PDDocument document) {
        this.document = document;
        this.renderer = new PDFRenderer(document);
        this.pageCount = document.getNumberOfPages();

        showPdfPage(0);
    }

    public void showPdfPage(int pageNumber) {
        try {
            pageImage = ImageUtils.scaleImageToFit(renderer.renderImage(pageNumber), getSize());
            revalidate();
            repaint();
        } catch (IOException ex) {

        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (pageImage == null) {
            return;
        }

        // Centre image if it's less tall or less wide than the available space
        int x = (getSize().width - pageImage.getWidth()) / 2;
        int y = (getSize().height - pageImage.getHeight()) / 2;

        g.drawImage(pageImage, x, y, null);
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void nextPage() {
        if (currentPage < pageCount - 1) {
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

    public int getPageCount() {
        return pageCount;
    }
    
    public int getCurrentPage() {
        return currentPage;
    }
}
