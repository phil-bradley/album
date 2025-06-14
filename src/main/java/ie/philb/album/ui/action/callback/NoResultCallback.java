/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action.callback;

/**
 *
 * @author philb
 * @param <T>
 */
public interface NoResultCallback<T> extends Callback<T> {

    @Override
    default void onSuccess(T result) {
        // No result, don't care
    }

}
