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
public record DialogValidationState<T>(
        boolean isValid,
        T result,
        String message,
        JComponent invalidField) {

    public static <T> DialogValidationState ok(T result) {
        return new DialogValidationState(true, result, null, null);
    }

    public static DialogValidationState error(String message, JComponent invalidField) {
        return new DialogValidationState(false, null, message, invalidField);
    }
}
