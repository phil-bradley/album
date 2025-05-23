/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common;

import ie.philb.album.ui.ApplicationUi;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
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

    private PdfViewPanel displayPanel;
    private JToolBar toolbar;
    private JButton btnNext;
    private JButton btnPrevious;
    private JButton btnPrint;

    public PdfViewDialog() {

        super(ApplicationUi.getInstance(), "Preview");
        setModal(true);

        this.btnPrevious = new JButton();
        btnPrevious.setIcon(new ImageIcon(this.getClass().getResource("/ie/philb/album/icons/arrow-left.png")));

        this.btnNext = new JButton();
        btnNext.setIcon(new ImageIcon(this.getClass().getResource("/ie/philb/album/icons/arrow-right.png")));

        this.btnPrint = new JButton();
        btnPrint.setIcon(new ImageIcon(this.getClass().getResource("/ie/philb/album/icons/print.png")));

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
            displayPanel.nextPage();
        });

        btnPrevious.addActionListener((ActionEvent e) -> {
            displayPanel.previousPage();
        });

        btnPrint.addActionListener((ActionEvent e) -> {
            try {
                displayPanel.printDocument();
            } catch (PrinterException ex) {
                String msg = "Print error: " + ex.getMessage();
                Dialogs.showErrorMessage(msg);
            }
        });

        int panelWidth = 800;
        int panelHeight = (int) (panelWidth / A4_RATIO);

        setPreferredSize(new Dimension(panelWidth + (MARGIN * 2), panelHeight + (MARGIN * 2)));
        setSize(getPreferredSize());
    }

    public void setFile(File pdfFile) throws IOException {
        displayPanel.showPdf(pdfFile);
    }

    public void setDocument(PDDocument doc) {
        displayPanel.showPdf(doc);
    }

}
