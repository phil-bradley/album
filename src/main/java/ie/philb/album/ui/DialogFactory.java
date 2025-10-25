/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui;

import ie.philb.album.AppContext;
import ie.philb.album.ui.about.AboutDialog;
import ie.philb.album.ui.about.LicenseDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author philb
 */
public class DialogFactory {

    private final AppContext appContext;

    public DialogFactory(AppContext appContext) {
        this.appContext = appContext;
    }

    public AboutDialog createAboutDialog() {
        AboutDialog aboutDialog = new AboutDialog(appContext);
        return aboutDialog;
    }

    public LicenseDialog createLicenseDialog() {
        LicenseDialog dlg = new LicenseDialog(appContext);
        return dlg;
    }

    public boolean confirm(String msg) {
        return confirm("Confirm", msg);
    }

    public boolean confirm(String title, String msg) {
        int ret = JOptionPane.showConfirmDialog(appContext.ui(), msg, title, JOptionPane.YES_NO_OPTION);
        return (ret == JOptionPane.YES_OPTION);
    }

    public void showInfoMessage(String msg) {
        JOptionPane.showMessageDialog(appContext.ui(), msg, msg, JOptionPane.INFORMATION_MESSAGE);
    }

    public void showInfoMessage(String title, String msg) {
        JOptionPane.showMessageDialog(appContext.ui(), msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String msg, Exception ex) {
        JOptionPane.showMessageDialog(appContext.ui(), msg, msg, JOptionPane.ERROR_MESSAGE);
    }
}
