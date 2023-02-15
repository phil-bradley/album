/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action;

/**
 *
 * @author Philip.Bradley
 * @param <T>
 */
public interface Callback<T> {

    void onSuccess(T result);

    void onFailure(Exception exception);
}
