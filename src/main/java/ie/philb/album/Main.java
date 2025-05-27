/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package ie.philb.album;

import com.formdev.flatlaf.FlatLightLaf;
import ie.philb.album.model.AlbumModel;
import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageModel;
import ie.philb.album.ui.ApplicationUi;
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
            ApplicationUi.getInstance().setVisible(true);
        });
    }

    private static void initModel() {
        AlbumModel albumModel = AppContext.INSTANCE.getAlbumModel();

        PageModel page1 = new PageModel(PageGeometry.rectangle(2, 3), albumModel.getPageSize());
//        page1.setImage(new File("/home/philb/Pictures/1.jpeg"), 0);
//        page1.setImage(new File("/home/philb/Pictures/2.jpg"), 1);
//        page1.setImage(new File("/home/philb/Pictures/4.jpeg"), 3);
        albumModel.addPage(page1);

        PageModel page2 = new PageModel(PageGeometry.rectangle(2, 3), albumModel.getPageSize());
//        page2.setImage(new File("/home/philb/Pictures/7.jpg"), 2);
        albumModel.addPage(page2);
//
//        PageModel page3 = new PageModel(new PageViewLayout(PageSpecification.A4Landscape, 2, 4));
//        albumModel.addPage(page3);
    }

}
