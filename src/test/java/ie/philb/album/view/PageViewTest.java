/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageModel;
import ie.philb.album.model.PageSize;
import ie.philb.album.ui.common.Resources;
import javax.swing.border.LineBorder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class PageViewTest {

    @Test
    void givenPageView_expectEntryViewForEachModelView() {
        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        AccessiblePageView pageView = new AccessiblePageView(pageModel);

        assertEquals(pageModel.getPageEntries().size(), pageView.getPageEntryViews().size());
    }

    @Test
    void givenPageView_whenSelected_expectBorderShown() {
        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);
        assertNull(pageView.getBorder());

        pageView.pageSelected(pageView);
        assertNotNull(pageView.getBorder());

        LineBorder border = (LineBorder) pageView.getBorder();
        assertEquals(Resources.COLOR_PHOTO_BORDER_SELECTED, border.getLineColor());
    }

    @Test
    void givenPageView_whenDeselected_expectNoBorderShown() {

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);

        pageView.pageSelected(pageView);
        LineBorder selectedBorder = (LineBorder) pageView.getBorder();
        assertEquals(Resources.COLOR_PHOTO_BORDER_SELECTED, selectedBorder.getLineColor());

        pageView.pageSelected(null);
        LineBorder unSelectedBorder = (LineBorder) pageView.getBorder();
        assertEquals(Resources.COLOR_PHOTO_BORDER, unSelectedBorder.getLineColor());
    }

    @Test
    void givenPageViewPreviewMode_whenSelected_expectNoBorderShown() {
        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);
        pageView.setPreviewMode(true);
        assertNull(pageView.getBorder());

        pageView.pageSelected(pageView);
        assertNull(pageView.getBorder());
    }
}
