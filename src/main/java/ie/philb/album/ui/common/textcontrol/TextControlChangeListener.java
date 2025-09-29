/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

/**
 *
 * @author philb
 */
public interface TextControlChangeListener {

    default void formatUpdated(TextControlModel model) {
    }

    default void textUpdated(TextControlModel model) {
    }

    default void textEditCancelled(TextControlModel model) {
    }

    default void textEditSelected(TextControlModel model) {
    }

//    public boolean isTextEditSelected();
}
