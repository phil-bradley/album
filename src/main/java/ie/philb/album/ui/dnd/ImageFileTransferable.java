/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.dnd;

import ie.philb.album.util.FileUtils;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author philb
 */
public class ImageFileTransferable implements Transferable {

    private final File file;

    public ImageFileTransferable(File file) {
        validateFile(file);
        this.file = file;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{DataFlavor.javaFileListFlavor};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return DataFlavor.javaFileListFlavor.equals(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {

        if (!isDataFlavorSupported(flavor)) {
            throw new UnsupportedFlavorException(flavor);
        }

        return List.of(file);
    }

    private void validateFile(File file) {

        if (file == null) {
            throw new RuntimeException("Cannot create transferable will null value");
        }

        if (!file.exists()) {
            throw new RuntimeException("Cannot create transferable will non-existent file: " + file.getAbsolutePath());
        }

        if (!file.isFile()) {
            throw new RuntimeException("Cannot create transferable will non-regular file: " + file.getAbsolutePath());
        }

        if (!FileUtils.isImage(file)) {
            throw new RuntimeException("Cannot create transferable will non-image file: " + file.getAbsolutePath());
        }               
    }

}
