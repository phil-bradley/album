/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action.callback;

import ie.philb.album.ui.common.Dialogs;
import java.awt.Frame;

/**
 *
 * @author philb
 * @param <T>
 */
public class DefaultNoResultCallback<T> implements NoResultCallback<T> {

    private final Frame owner;

    public DefaultNoResultCallback() {
        this(null);
    }
    
    public DefaultNoResultCallback(Frame owner) {
        this.owner = owner;
    }

    @Override
    public void onFailure(Exception ex) {
        Dialogs.showErrorMessage(owner, "Error", ex);
    }

}
