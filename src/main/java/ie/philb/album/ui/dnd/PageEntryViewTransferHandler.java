/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.dnd;

import ie.philb.album.view.PageEntryView;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 *
 * @author philb
 */
public class PageEntryViewTransferHandler extends TransferHandler {

    @Override
    public boolean canImport(TransferSupport support) {
        return support.isDataFlavorSupported(DataFlavor.stringFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        try {
            Transferable t = support.getTransferable();
            String data = (String) t.getTransferData(DataFlavor.stringFlavor);
            JComponent comp = (JComponent) support.getComponent();

            if (comp instanceof PageEntryView view) {
                view.getPageEntryModel().setImageIcon(new ImageIcon(data));
                view.setSelected(true);
            }

        } catch (UnsupportedFlavorException | IOException ex) {

        }

        return false;
    }
}
