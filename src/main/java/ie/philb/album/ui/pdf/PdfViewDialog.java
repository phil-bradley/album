/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.pdf;

import ie.philb.album.ui.ApplicationUi;
import ie.philb.album.ui.command.PrintAlbumCommand;
import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.resources.Icons;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JToolBar;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author philb
 */
public class PdfViewDialog extends JDialog {

    private static final Logger LOG = LoggerFactory.getLogger(PdfViewDialog.class);

    private static final long serialVersionUID = 1L;
    private static final double A4_RATIO = 1.4142;
    private static final int MARGIN = 0;

    private final PdfViewPanel displayPanel;
    private final JToolBar toolbar;
    private final JButton btnNext;
    private final JButton btnPrevious;
    private final JButton btnPrint;

    public PdfViewDialog() {

        super(ApplicationUi.getInstance(), "Preview");
        setModal(true);

        this.btnPrevious = new JButton();
        btnPrevious.setIcon(Icons.Regular.ARROW_LEFT);
        btnPrevious.setEnabled(false);

        this.btnNext = new JButton();
        btnNext.setIcon(Icons.Regular.ARROW_RIGHT);

        this.btnPrint = new JButton();
        btnPrint.setIcon(Icons.Regular.PRINT);

        this.toolbar = new JToolBar();
        this.toolbar.setFloatable(false);
        this.toolbar.add(btnPrevious);
        this.toolbar.add(btnNext);
        this.toolbar.add(btnPrint);

        this.displayPanel = new PdfViewPanel();

        setLayout(new GridBagLayout());

        GridBagCellConstraints gbc = new GridBagCellConstraints(0, 0).weight(0).fillHorizontal().anchorNorth();
        add(toolbar, gbc);

        gbc.xy(0, 1).fillBoth().weight(1).inset(MARGIN);
        add(displayPanel, gbc);

        btnNext.addActionListener((ActionEvent e) -> {
            nextPage();
        });

        btnPrevious.addActionListener((ActionEvent e) -> {
            previousPage();
        });

        btnPrint.addActionListener((ActionEvent e) -> {
            new PrintAlbumCommand().execute();
        });

        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    nextPage();
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    previousPage();
                }
            }
        });

        int panelWidth = 1000;
        int panelHeight = (int) (panelWidth / A4_RATIO);

        setPreferredSize(new Dimension(panelWidth + (MARGIN * 2), panelHeight + (MARGIN * 2)));
        setSize(getPreferredSize());
        
        addComponentListener(new ResizeListener());
    }

    public void setFile(File pdfFile) throws IOException {
        displayPanel.showPdf(pdfFile);
        updatePageButtons();
    }

    public void setDocument(PDDocument doc) {
        displayPanel.showPdf(doc);
        updatePageButtons();
    }

    private void nextPage() {
        displayPanel.nextPage();
        updatePageButtons();
    }

    private void previousPage() {
        displayPanel.previousPage();
        updatePageButtons();
    }

    private void updatePageButtons() {
        int currentPage = displayPanel.getCurrentPage();
        btnNext.setEnabled(currentPage < displayPanel.getPageCount() - 1);
        btnPrevious.setEnabled(displayPanel.getCurrentPage() > 0);
//        displayPanel.revalidate();
//        revalidate();
    }

    class ResizeListener extends ComponentAdapter {

        @Override
        public void componentResized(ComponentEvent e) {
            displayPanel.showPdfPage(displayPanel.getCurrentPage());
        }
        
        @Override
        public void componentShown(ComponentEvent e) {
            displayPanel.showPdfPage(0);
        }
    }
}
