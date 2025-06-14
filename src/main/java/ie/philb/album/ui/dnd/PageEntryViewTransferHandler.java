/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.dnd;

import ie.philb.album.AppContext;
import ie.philb.album.view.PageEntryView;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
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
            String imageFileName = (String) t.getTransferData(DataFlavor.stringFlavor);
            JComponent comp = (JComponent) support.getComponent();

            if (comp instanceof PageEntryView view) {
                File imageFile = new File(imageFileName);
                view.getPageEntryModel().setImageFile(imageFile);
                view.centerImage();

                AppContext.INSTANCE.pageEntrySelected(view.getPageView(), view);
            }

        } catch (UnsupportedFlavorException | IOException ex) {

        }

        return false;
    }
}
