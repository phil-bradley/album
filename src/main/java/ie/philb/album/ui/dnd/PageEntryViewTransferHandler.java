/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.dnd;

import ie.philb.album.AppContext;
import ie.philb.album.model.PageEntryType;
import ie.philb.album.util.FileUtils;
import ie.philb.album.util.ImageUtils;
import ie.philb.album.view.PageEntryView;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 *
 * @author philb
 */
public class PageEntryViewTransferHandler extends TransferHandler {

    @Override
    public boolean canImport(TransferSupport support) {
        JComponent comp = (JComponent) support.getComponent();

        if (comp instanceof PageEntryView view) {
            if (view.getPageEntryModel().getPageEntryType() != PageEntryType.Image) {
                return false;
            }
        }

        return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {

        if (!canImport(support)) {
            return false;
        }

        Transferable t = support.getTransferable();
        JComponent comp = (JComponent) support.getComponent();

        if (!(comp instanceof PageEntryView)) {
            return false;
        }

        PageEntryView view = (PageEntryView) comp;

        try {
            List<File> files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);

            if (files.isEmpty()) {
                return false;
            }

            File imageFile = files.get(files.size() - 1);

            if (!FileUtils.isImage(imageFile)) {
                return false;
            }

            view.getPageEntryModel().setImageFile(imageFile);
            view.centerImage();

            AppContext.INSTANCE.pageEntrySelected(view.getPageView(), view);

        } catch (UnsupportedFlavorException | IOException ex) {

        }

        return false;
    }
}
