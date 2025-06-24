/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageModel;
import ie.philb.album.model.PageSize;
import ie.philb.album.ui.common.Resources;
import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import ie.philb.album.util.TestUtils;
import javax.swing.border.LineBorder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class PageViewTest {

    @Test
    void givenPageView_expectEntryViewForEachModelView() {
        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);

        assertEquals(pageModel.getPageEntries().size(), pageView.pageEntryViews.size());
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

    @Test
    void givenPageView_whenPageEntrySelected_expectSelectedPageEntryViewUpdated() {

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);

        PageEntryView toSelect = pageView.pageEntryViews.get(2);

        pageView.pageEntrySelected(pageView, toSelect);
        assertTrue(pageView.isSelected);
        assertEquals(toSelect, pageView.selectedPageEntryView);
        assertTrue(pageView.pageEntryViews.get(2).isSelected());
    }

    @Test
    void givenPageView_whenPNullageEntrySelected_expectPageAndPageEntryDeselected() {

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);

        pageView.pageEntrySelected(pageView, null);
        assertTrue(pageView.isSelected);

        for (PageEntryView pageEntryView : pageView.pageEntryViews) {
            assertFalse(pageEntryView.isSelected());
        }
    }

    @Test
    void givenPageView_whenWidthSet_expectAspectRatioPreserved() {

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);

        pageView.setWidth(100);
        int expectedHeight = PageSize.A4_Landscape.heigthFromWidth(100);
        assertEquals(expectedHeight, pageView.getHeight());
    }

    @Test
    void givenPageView_whenHeightSet_expectAspectRatioPreserved() {

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);

        pageView.setHeight(100);
        int expectedWidth = PageSize.A4_Landscape.widthFromHeight(100);
        assertEquals(expectedWidth, pageView.getWidth());
    }

    @Test
    void givenPageView_whenEntrySelected_expectImageLibrarySelectionApplied() {

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);
        pageView.pageEntrySelected(pageView, pageView.pageEntryViews.get(2));

        ImageLibraryEntry imageLibraryEntry = new ImageLibraryEntry(TestUtils.getTestImageFile());
        pageView.libraryImageSelected(imageLibraryEntry);

        assertEquals(TestUtils.getTestImageFile().getAbsolutePath(), pageModel.getPageEntries().get(2).getImageFile().getAbsolutePath());
    }
}
