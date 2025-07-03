/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author philb
 */
public interface DefaultMouseMotionListener extends MouseMotionListener {

    @Override
    default public void mouseDragged(MouseEvent me) {
    }

    @Override
    default public void mouseMoved(MouseEvent me) {

    }
}
