/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package ie.philb.album;

import com.formdev.flatlaf.FlatLightLaf;
import ie.philb.album.ui.ApplicationUi;
import ie.philb.album.ui.command.NewAlbumCommand;
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

        new NewAlbumCommand().execute();

        java.awt.EventQueue.invokeLater(() -> {
            ApplicationUi.getInstance().setVisible(true);
        });
    }
}
