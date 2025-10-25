/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;

/**
 *
 * @author Philip.Bradley
 */
public abstract class AbstractCommand {

    private final AppContext appContext;

    public AbstractCommand(AppContext appContext) {
        this.appContext = appContext;
    }

    protected AppContext getAppContext() {
        return appContext;
    }
    
    public abstract void execute();
}
