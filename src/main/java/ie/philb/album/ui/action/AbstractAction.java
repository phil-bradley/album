/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philip.Bradley
 * @param <T>
 */
public abstract class AbstractAction<T> {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractAction.class);

    private Callback<T> callback;

    protected void success(T result) {
        if (callback != null) {
            callback.onSuccess(result);
        }
    }

    protected void failure(Exception exception) {
        logger.error("Action Exception: ", exception);

        if (callback != null) {
            callback.onFailure(exception);
        }
    }

    protected abstract T doAction() throws Exception;

    public void execute() {
        this.execute(new DefaultNoResultCallback());
    }

    public void execute(Callback<T> callback) {
        this.callback = callback;
        try {
            success(doAction());
        } catch (Exception e) {
            logger.error("Action exception", e);
            failure(e);
        }
    }
}
