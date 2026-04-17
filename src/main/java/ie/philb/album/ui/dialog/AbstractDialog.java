/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.dialog;

import java.awt.GridBagLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;

/**
 *
 * @author philb
 */
public abstract class AbstractDialog extends JDialog {

    protected abstract JComponent getView();

    public AbstractDialog() {
        setLayout(new GridBagLayout());
        setName(getClass().getSimpleName());
    }
}
