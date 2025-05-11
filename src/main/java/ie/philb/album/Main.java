/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package ie.philb.album;

import com.formdev.flatlaf.FlatLightLaf;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageModel;
import ie.philb.album.ui.ApplicationUi;
import ie.philb.album.ui.page.PageSpecification;
import ie.philb.album.view.PageViewLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philip.Bradley
 */
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String args[]) {

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            LOG.info("Failed to set LookAndFeel", ex);
        }

        initModel();

        java.awt.EventQueue.invokeLater(() -> {
            new ApplicationUi().setVisible(true);
        });
    }

    private static void initModel() {
        AlbumModel albumModel = AppContext.INSTANCE.getAlbumModel();

        PageModel page1 = new PageModel(new PageViewLayout(PageSpecification.A4Landscape, 2, 3));
        albumModel.addPage(page1);

        PageModel page2 = new PageModel(new PageViewLayout(PageSpecification.A4Landscape, 3, 3));
        albumModel.addPage(page2);

        PageModel page3 = new PageModel(new PageViewLayout(PageSpecification.A4Landscape, 2, 4));
        albumModel.addPage(page3);
    }

}
