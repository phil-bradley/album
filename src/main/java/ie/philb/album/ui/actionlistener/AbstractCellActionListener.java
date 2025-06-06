/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.actionlistener;

import ie.philb.album.AppContext;
import ie.philb.album.ApplicationListener;
import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import ie.philb.album.view.PageEntryView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author philb
 */
public abstract class AbstractCellActionListener implements ActionListener, ApplicationListener {

    protected PageEntryView selectedPageEntryView;

    public AbstractCellActionListener() {
        AppContext.INSTANCE.addListener(this);
    }

    @Override
    public void imageEntrySelected(PageEntryView view) {
        this.selectedPageEntryView = view;

    }

    @Override
    public void libraryImageSelected(ImageLibraryEntry entry) {
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (selectedPageEntryView == null) {
            return;
        }

        doAction();
    }

    protected abstract void doAction();
}
