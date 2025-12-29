/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.dnd;

import ie.philb.album.AppContext;
import ie.philb.album.model.PageEntryType;
import ie.philb.album.ui.common.Dialogs;
import ie.philb.album.util.FileUtils;
import ie.philb.album.view.PageEntryView;
import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;
import javax.swing.TransferHandler;

/**
 *
 * @author philb
 */
public class PageEntryViewTransferHandler extends TransferHandler {

    @Override
    public boolean canImport(TransferSupport support) {

        if (!support.isDrop()) {
            return false;
        }

        Component comp = support.getComponent();
        if (!(comp instanceof PageEntryView view)) {
            return false;
        }

        if (view.getPageEntryModel().getPageEntryType() != PageEntryType.Image) {
            return false;
        }

        return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {

        if (!canImport(support)) {
            return false;
        }

        PageEntryView view = (PageEntryView) support.getComponent();

        try {
            File imageFile = getTransferredFile(support);
            view.getPageEntryModel().setImageFile(imageFile);
            view.centerImage();

            AppContext.INSTANCE.pageEntrySelected(view.getPageView(), view);
            return true;
        } catch (Exception ex) {
            Dialogs.showErrorMessage("Drag & drop failed: " + ex.getMessage(), ex);
            return false;
        }
    }

    private File getTransferredFile(TransferSupport transferSupport) throws Exception {

        Transferable t = transferSupport.getTransferable();

        List<File> files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);

        if (files.isEmpty()) {
            throw new Exception("Cannot transfer empty list of files");
        }

        if (files.size() > 1) {
            throw new Exception("Cannot transfer multiple files");
        }

        File file = files.get(0);

        if (!FileUtils.isImage(file)) {
            throw new Exception("Cannot transfer non image file " + file.getName());
        }

        return file;
    }
}
