/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common;

import ie.philb.album.ui.ApplicationUi;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 *
 * @author philb
 */
public class PdfViewDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private PdfViewPanel displayPanel;
    private JScrollPane jScrollPane;
    private JToolBar toolbar;
    private JButton btnNext;
    private JButton btnPrevious;
    private JButton btnPrint;

    public PdfViewDialog() {

        super(ApplicationUi.getInstance());
//        setLocationRelativeTo(ApplicationUi.getInstance());

        setModal(true);

        this.btnPrevious = new JButton("<<");
        this.btnNext = new JButton(">>");
        this.btnPrint = new JButton("Print");

        this.toolbar = new JToolBar();
        this.toolbar.setFloatable(false);
        this.toolbar.add(btnPrevious);
        this.toolbar.add(btnNext);
        this.toolbar.add(btnPrint);

        this.displayPanel = new PdfViewPanel();
        this.displayPanel.setScale(1.2f);
        this.jScrollPane = new JScrollPane(this.displayPanel);

        setLayout(new GridBagLayout());

        GridBagCellConstraints gbc = new GridBagCellConstraints(0, 0).weight(0).fillHorizontal().anchorNorth();
        add(toolbar, gbc);

        gbc.xy(0, 1).fillBoth().weight(1);
        add(jScrollPane, gbc);

        btnNext.addActionListener((ActionEvent e) -> {
            displayPanel.nextPage();
        });

        btnPrevious.addActionListener((ActionEvent e) -> {
            displayPanel.previousPage();
        });

        btnPrint.addActionListener((ActionEvent e) -> {
            //?
        });

        setPreferredSize(new Dimension(displayPanel.getPreferredSize().width + 50, displayPanel.getPreferredSize().height + 80));
        setSize(getPreferredSize());
    }

    public void setFile(File pdfFile) throws IOException {
        displayPanel.showPdf(pdfFile);
    }

    public void setDocument(PDDocument doc) {
        displayPanel.showPdf(doc);
    }

}
