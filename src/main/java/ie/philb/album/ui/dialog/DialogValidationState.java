/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.dialog;

import javax.swing.JComponent;

/**
 *
 * @author philb
 */
public record DialogValidationState(
        boolean isValid,
        String message,
        JComponent invalidField) {

    public static DialogValidationState ok() {
        return new DialogValidationState(true, null, null);
    }
}
