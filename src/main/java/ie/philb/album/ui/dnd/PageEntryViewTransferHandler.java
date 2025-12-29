/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.dnd;

import ie.philb.album.AppContext;
import ie.philb.album.model.PageEntryType;
import ie.philb.album.ui.common.Dialogs;
import static ie.philb.album.ui.dnd.ImageFileTransferable.LOCAL_FILE_LIST_FLAVOR;
import ie.philb.album.util.FileUtils;
import ie.philb.album.view.PageEntryView;
import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 *
 * @author philb
 */
public class PageEntryViewTransferHandler extends TransferHandler {

    @Override
    public boolean canImport(JComponent comp, DataFlavor[] flavors) {
        
        List<DataFlavor> supportedFlavors = Arrays.asList(ImageFileTransferable.SUPPORTED_FLAVORS);
        
        for (DataFlavor flavor : flavors) {
            if (supportedFlavors.contains(flavor)) {
                System.out.println("Can import flavours");
                return true;
            }
        }
        
        System.out.println("Doh!!!!");
        return false;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        try {

            if (!support.isDrop()) {
                return false;
            }

            Component comp = support.getComponent();

            if (!(comp instanceof PageEntryView)) {
                return false;
            }

            System.out.println("Got comp " + comp);

            PageEntryView view = (PageEntryView) comp;

            System.out.println("Got tx component " + comp);

            if (view.getPageEntryModel().getPageEntryType() != PageEntryType.Image) {
                System.out.println("It's not image type though: " + view.getPageEntryModel().getPageEntryType());
                return false;
            }

            if (!isDataFlavorSupported(support)) {
                System.out.println("Data flavour not supported: " + Arrays.toString(support.getDataFlavors()));
                return false;
            }

            int action = support.getDropAction();

            if ((action & COPY_OR_MOVE) == 0) {
                System.out.println("Not a copy or move, returning false");
                return false;
            }

            support.setDropAction(COPY);
            support.setShowDropLocation(true);

            return true;
        } catch (Exception ex) {
            System.out.println("Got exception " + ex);
            return false;
        }
    }

    private boolean isDataFlavorSupported(TransferSupport support) {

        for (DataFlavor flavor : ImageFileTransferable.SUPPORTED_FLAVORS) {
            if (support.isDataFlavorSupported(flavor)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean importData(TransferSupport support) {

        if (!canImport(support)) {
            System.out.println("Cannot import");
            return false;
        }

        try {
            Component comp = support.getComponent();

            if (!(comp instanceof PageEntryView)) {
                System.out.println("Cannot import to comp " + comp);
                return false;
            }

            PageEntryView view = (PageEntryView) comp;
            File imageFile = getTransferredFile(support);

            view.getPageEntryModel().setImageFile(imageFile);
            view.centerImage();

            AppContext.INSTANCE.pageEntrySelected(view.getPageView(), view);

            System.out.println("Imported, returning true");
            return true;
        } catch (Exception ex) {
            Dialogs.showErrorMessage("Drag & drop failed: " + ex.getMessage(), ex);
            return false;
        }
    }

    private File getTransferredFile(TransferSupport transferSupport) throws Exception {

        Transferable t = transferSupport.getTransferable();

        DataFlavor flavor
                = t.isDataFlavorSupported(ImageFileTransferable.LOCAL_FILE_LIST_FLAVOR)
                ? ImageFileTransferable.LOCAL_FILE_LIST_FLAVOR
                : DataFlavor.javaFileListFlavor;

        List<File> files = (List<File>) t.getTransferData(flavor);

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
