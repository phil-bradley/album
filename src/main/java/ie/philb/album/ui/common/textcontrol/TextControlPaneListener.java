/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import java.awt.event.MouseEvent;

/**
 *
 * @author philb
 */
public interface TextControlPaneListener {

    default void editingCancelled() {
    }

    default void editingCompleted() {
    }

    default void mouseClicked(MouseEvent e){        
    }
}
