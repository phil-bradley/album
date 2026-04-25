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
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * Handles both drop target and visual feedback for drag & drop operations.
 * Integrates with the low-level DropTarget API to properly communicate
 * acceptance/rejection back to the drag source.
 *
 * @author philb
 */
public class PageEntryViewTransferHandler extends TransferHandler implements DropTargetListener {

    @Override
    public boolean canImport(TransferSupport support) {
        
        if (!support.isDrop()) {
            return false;
        }

        if (!support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            return false;
        }

        JComponent comp = (JComponent) support.getComponent();

        if (comp instanceof PageEntryView view) {
            if (view.getPageEntryModel().getPageEntryType() != PageEntryType.Image) {
                return false;
            }
        }

        support.setShowDropLocation(true);
        support.setDropAction(COPY);
        return true;

    }

    @Override
    public boolean importData(TransferSupport support) {

        if (!canImport(support)) {
            return false;
        }

        try {
            PageEntryView view = (PageEntryView) support.getComponent();

            File imageFile = getTransferredFile(support);

            view.getPageEntryModel().setImageFile(imageFile);
            view.centerImage();

            AppContext.INSTANCE.pageEntrySelected(view.getPageView(), view);
        } catch (Exception ex) {
            Dialogs.showErrorMessage("Drag & drop failed: " + ex.getMessage(), ex);
        }

        return true;
    }

    /**
     * DropTargetListener implementation to communicate drop acceptance to the drag source.
     * This ensures the cursor updates properly while dragging.
     */
    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        System.out.println("DROP TARGET dragEnter called");
        if (canDropHere(dtde)) {
            System.out.println("Accepting drag - calling acceptDrag");
            dtde.acceptDrag(DnDConstants.ACTION_COPY);
        } else {
            System.out.println("Rejecting drag - calling rejectDrag");
            dtde.rejectDrag();
        }
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
        System.out.println("DROP TARGET dragOver called");
        if (canDropHere(dtde)) {
            dtde.acceptDrag(DnDConstants.ACTION_COPY);
        } else {
            dtde.rejectDrag();
        }
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
        System.out.println("DROP TARGET dropActionChanged called");
        if (canDropHere(dtde)) {
            dtde.acceptDrag(DnDConstants.ACTION_COPY);
        } else {
            dtde.rejectDrag();
        }
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
        System.out.println("DROP TARGET dragExit called");
        // Cursor will be reset by the drag source
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        System.out.println("DROP TARGET drop called");
        if (!canDropHere(dtde)) {
            dtde.rejectDrop();
            return;
        }

        dtde.acceptDrop(DnDConstants.ACTION_COPY);

        try {
            PageEntryView view = (PageEntryView) dtde.getDropTargetContext().getComponent();
            File imageFile = getTransferredFile(dtde);

            view.getPageEntryModel().setImageFile(imageFile);
            view.centerImage();

            AppContext.INSTANCE.pageEntrySelected(view.getPageView(), view);
            dtde.dropComplete(true);
        } catch (Exception ex) {
            Dialogs.showErrorMessage("Drag & drop failed: " + ex.getMessage(), ex);
            dtde.dropComplete(false);
        }
    }

    /**
     * Check if the drop target can accept the transferred data
     */
    private boolean canDropHere(DropTargetDragEvent dtde) {
        if (!dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            return false;
        }

        java.awt.Component comp = dtde.getDropTargetContext().getComponent();
        if (comp instanceof PageEntryView view) {
            return view.getPageEntryModel().getPageEntryType() == PageEntryType.Image;
        }

        return false;
    }

    /**
     * Check if the drop target can accept the transferred data (overload for drop event)
     */
    private boolean canDropHere(DropTargetDropEvent dtde) {
        if (!dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            return false;
        }

        java.awt.Component comp = dtde.getDropTargetContext().getComponent();
        if (comp instanceof PageEntryView view) {
            return view.getPageEntryModel().getPageEntryType() == PageEntryType.Image;
        }

        return false;
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

    private File getTransferredFile(DropTargetDropEvent dtde) throws Exception {

        Transferable t = dtde.getTransferable();
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
