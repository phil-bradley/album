/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 *
 * @author philb
 */
public class PdfViewPanel extends AppPanel {

    private static final long serialVersionUID = 1L;
    private int currentPage = 0;
    private PDDocument document;
    private PDFRenderer renderer;
    private int numberPages = 0;
    private float scale = 1;
    private ImagePanel imagePanel;

    public PdfViewPanel() {
        setBackground(Color.gray);
        setOpaque(true);

        this.imagePanel = new ImagePanel();

        //this.imagePanel.setUpscaleAllowed(true);
        this.imagePanel.setPreferredSize(new Dimension(800, 600));
        this.imagePanel.setSize(this.imagePanel.getPreferredSize());
        setSize(this.imagePanel.getPreferredSize());

        GridBagCellConstraints gbc = new GridBagCellConstraints(0, 0).weight(1).anchorNorth().fillVertical();

        add(imagePanel, gbc);
    }

    void showPdf(File file) throws IOException {
        PDDocument doc = Loader.loadPDF(file);
        showPdf(doc);
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
            pageImage = renderer.renderImage(pageNumber, this.scale);
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

}
