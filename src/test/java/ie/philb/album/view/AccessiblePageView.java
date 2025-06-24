/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.model.PageModel;
import java.util.List;

/**
 * Subclass of PageView used for testing to expose internal state
 * @author philb
 */
public class AccessiblePageView extends PageView {
    
    public AccessiblePageView(PageModel model) {
        super(model);
    }
    
    public List<PageEntryView> getPageEntryViews() {
        return super.pageEntryViews;
    }
    
}
