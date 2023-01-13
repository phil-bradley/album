/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package ie.philb.album;

import ie.philb.album.ui.common.LFManager;
import ie.philb.album.ui.MainFrame;

/**
 *
 * @author Philip.Bradley
 */
public class Main {

    public static void main(String args[]) {

        LFManager.setSystemLookAndFeel();

        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
