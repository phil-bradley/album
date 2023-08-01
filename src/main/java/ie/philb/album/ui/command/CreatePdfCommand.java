/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.command;

import ie.philb.album.AppContext;
import ie.philb.album.ui.action.Callback;
import ie.philb.album.ui.action.CreatePdfAction;
import ie.philb.album.ui.page.Page;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Philip.Bradley
 */
public class CreatePdfCommand extends AbstractCommand {

    @Override
    public void execute() {
        List<Page> pages = AppContext.INSTANCE.getAlbum().getPages();

        new CreatePdfAction(pages).execute(
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
