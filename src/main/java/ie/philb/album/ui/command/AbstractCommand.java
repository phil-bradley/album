/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.Context;
import ie.philb.album.ui.action.AbstractAction;
import ie.philb.album.ui.action.callback.DefaultNoResultCallback;

/**
 *
 * @author Philip.Bradley
 */
public abstract class AbstractCommand {

    protected final Context context; 
    
    public AbstractCommand(Context context) {
        this.context = context;
    }
    
    public abstract void execute();
    
    protected void executeAction(AbstractAction t) {
        t.execute(new DefaultNoResultCallback(context.ui()));
    }
}
