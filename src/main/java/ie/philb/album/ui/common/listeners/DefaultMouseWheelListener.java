/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ie.philb.album.ui.common.listeners;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 *
 * @author philb
 */
public interface DefaultMouseWheelListener extends MouseWheelListener {

    @Override
    default public void mouseWheelMoved(MouseWheelEvent mwe) {
    }
}
