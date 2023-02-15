/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.ui.action.Callback;
import ie.philb.album.ui.action.CreatePdfAction;
import ie.philb.album.ui.page.PageLayout;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Philip.Bradley
 */
public class CreatePdfCommand extends AbstractCommand {

    @Override
    public void execute() {
        List<PageLayout> pageLayouts = AppContext.INSTANCE.getPageLayouts();

        new CreatePdfAction(pageLayouts).execute(
                new Callback<Void>() {

            @Override
            public void onSuccess(Void result) {
                JOptionPane.showMessageDialog(null, "Done!");
            }

            @Override
            public void onFailure(Exception ex) {
                JOptionPane.showMessageDialog(null, "Error!\n" + ex.getMessage());
            }
        }
        );
    }
}
