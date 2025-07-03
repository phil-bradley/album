/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author philb
 */
public interface DefaultKeyListener extends KeyListener {

    @Override
    default void keyTyped(KeyEvent ke) {
    }

    @Override
    default void keyPressed(KeyEvent ke) {
    }

    @Override
    default void keyReleased(KeyEvent ke) {
    }
    
}
