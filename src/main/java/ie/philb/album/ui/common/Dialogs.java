/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author philipb
 */
public class Dialogs {

    public static boolean confirm(Component owner, String msg) {
        return confirm(owner, "Confirm", msg);
    }

    public static boolean confirm(Component owner, String title, String msg) {
        int ret = JOptionPane.showConfirmDialog(owner, msg, title, JOptionPane.YES_NO_OPTION);
        return (ret == JOptionPane.YES_OPTION);
    }

    public static void showInfoMessage(Component owner, String msg) {
        JOptionPane.showMessageDialog(owner, msg, msg, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showInfoMessage(Component owner, String title, String msg) {
        JOptionPane.showMessageDialog(owner, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showErrorMessage(Component owner, String msg, Exception ex) {
        JOptionPane.showMessageDialog(owner, msg, msg, JOptionPane.ERROR_MESSAGE);
    }
}
