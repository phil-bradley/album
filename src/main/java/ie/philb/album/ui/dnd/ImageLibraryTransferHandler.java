/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.dnd;

import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

/**
 *
 * @author philb
 */
public class ImageLibraryTransferHandler extends TransferHandler {

    @Override
    public int getSourceActions(JComponent c) {
        return COPY;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        JList<ImageLibraryEntry> list = (JList<ImageLibraryEntry>) c;
        ImageLibraryEntry selected = list.getSelectedValue();
        return new StringSelection(selected.getFile().getAbsolutePath());
    }

}
