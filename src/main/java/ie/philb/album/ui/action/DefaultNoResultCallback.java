/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action;

import ie.philb.album.ui.common.Dialogs;

/**
 *
 * @author philb
 * @param <T>
 */
public class DefaultNoResultCallback<T> implements NoResultCallback<T> {

    @Override
    public void onFailure(Exception ex) {
        Dialogs.showErrorMessage("Error", ex);
    }

}
