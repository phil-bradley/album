/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.foldernavigator;

import java.io.File;

/**
 *
 * @author philb
 */
public interface FolderNavigationListener {

    default void locationUpdated(File file) {
    }
}
