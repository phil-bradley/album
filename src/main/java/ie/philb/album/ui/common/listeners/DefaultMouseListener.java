/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author philb
 */
public interface DefaultMouseListener extends MouseListener {

    @Override
    default public void mouseClicked(MouseEvent me) {
    }

    @Override
    default public void mousePressed(MouseEvent me) {
    }

    @Override
    default public void mouseReleased(MouseEvent me) {
    }

    @Override
    default public void mouseEntered(MouseEvent me) {
    }

    @Override
    default public void mouseExited(MouseEvent me) {
    }
}
