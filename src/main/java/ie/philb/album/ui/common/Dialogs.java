/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common;

import ie.philb.album.ui.ApplicationUi;
import javax.swing.JOptionPane;

/**
 *
 * @author philipb
 */
public class Dialogs {

    public static boolean confirm(String msg) {
        return confirm("Confirm", msg);
    }

    public static boolean confirm(String title, String msg) {
        int ret = JOptionPane.showConfirmDialog(ApplicationUi.getInstance(), msg, title, JOptionPane.YES_NO_OPTION);
        return (ret == JOptionPane.YES_OPTION);
    }

    public static void showInfoMessage(String msg) {
        JOptionPane.showMessageDialog(ApplicationUi.getInstance(), msg, msg, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showInfoMessage(String title, String msg) {
        JOptionPane.showMessageDialog(ApplicationUi.getInstance(), msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showErrorMessage(String msg, Exception ex) {
        JOptionPane.showMessageDialog(ApplicationUi.getInstance(), msg, msg, JOptionPane.ERROR_MESSAGE);
    }
}
