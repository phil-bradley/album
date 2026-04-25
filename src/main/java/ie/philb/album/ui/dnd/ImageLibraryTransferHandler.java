/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.dnd;

import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragSourceContext;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

/**
 *
 * @author philb
 */
public class ImageLibraryTransferHandler extends TransferHandler implements DragSourceListener {

    private DragSourceContext dragSourceContext;

    @Override
    public int getSourceActions(JComponent c) {
        return COPY;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        JList<ImageLibraryEntry> list = (JList<ImageLibraryEntry>) c;
        ImageLibraryEntry selected = list.getSelectedValue();

        // We can drag & drop a directory entry, returning null means that
        // the DnD won't be effected
        if (selected.isDirectory()) {
            return null;
        }

        return new ImageFileTransferable(selected.getFile());
    }

    @Override
    public Icon getVisualRepresentation(Transferable t) {
        try {
            List<File> files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);

            if (!files.isEmpty()) {
                File file = files.get(0);

                // Use your existing thumbnail logic if possible
                BufferedImage img = ImageIO.read(file);

                if (img != null) {
                    Image scaled = img.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                    return new ImageIcon(scaled);
                }
            }
        } catch (UnsupportedFlavorException | IOException ignored) {
        }

        return super.getVisualRepresentation(t);
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        // Cleanup after drag operation completes
        dragSourceContext = null;
        super.exportDone(source, data, action);
    }

    @Override
    public void dragEnter(DragSourceDragEvent dsde) {
        System.out.println("Drag entered");
        dragSourceContext = dsde.getDragSourceContext();
        updateDragCursor(dsde);
    }

    @Override
    public void dragOver(DragSourceDragEvent dsde) {
        System.out.println("Drag over");
        updateDragCursor(dsde);
    }

    @Override
    public void dropActionChanged(DragSourceDragEvent dsde) {
        System.out.println("Drop action changed");
        updateDragCursor(dsde);
    }

    @Override
    public void dragExit(DragSourceEvent dse) {
        System.out.println("Drag exit");
        if (dse.getDragSourceContext() != null) {
            dse.getDragSourceContext().setCursor(Cursor.getDefaultCursor());
        }
    }

    @Override
    public void dragDropEnd(DragSourceDropEvent dsde) {
        System.out.println("Dnd end");
        dragSourceContext = null;
    }

    private void updateDragCursor(DragSourceDragEvent dsde) {

        System.out.println("Updating cursor");
        DragSourceContext context = dsde.getDragSourceContext();
        int dropAction = dsde.getDropAction();
        int sourceActions = getSourceActions(null);

        // Check if the drop action is supported by the source
        if ((dropAction & sourceActions) != 0) {
            System.out.println("Drop accepted");
            // Drop is accepted - show copy cursor
            context.setCursor(null); // Use default copy cursor
        } else {
            System.out.println("Drop not accepted");
            // Drop is not accepted - show no-drop cursor
            context.setCursor(java.awt.dnd.DragSource.DefaultCopyNoDrop);
        }
    }
}
