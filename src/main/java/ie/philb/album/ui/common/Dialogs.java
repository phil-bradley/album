/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common;

import javax.swing.JOptionPane;

/**
 *
 * @author philipb
 */
public class Dialogs {
 
    public static boolean confirm(String msg) {
        return confirm(msg, "Confirm");
    }
    
    public static boolean confirm(String msg, String title) {
        int ret = JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.YES_OPTION);        
        return (ret == JOptionPane.YES_OPTION);
    }
}
