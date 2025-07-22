/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.AppContext;
import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageModel;
import ie.philb.album.model.PageSize;
import ie.philb.album.ui.resources.Colors;
import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import ie.philb.album.util.TestUtils;
import static ie.philb.album.util.TestUtils.assertClose;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.IOException;
import javax.swing.border.LineBorder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class PageViewTest {

    @BeforeEach
    void setUp() {
        AppContext.INSTANCE.pageSelected(null);
    }

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
        assertEquals(Colors.COLOR_PHOTO_BORDER_SELECTED, border.getLineColor());
    }

    @Test
    void givenPageView_whenDeselected_expectNoBorderShown() {

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);

        pageView.pageSelected(pageView);
        LineBorder selectedBorder = (LineBorder) pageView.getBorder();
        assertEquals(Colors.COLOR_PHOTO_BORDER_SELECTED, selectedBorder.getLineColor());

        pageView.pageSelected(null);
        LineBorder unSelectedBorder = (LineBorder) pageView.getBorder();
        assertEquals(Colors.COLOR_PHOTO_BORDER, unSelectedBorder.getLineColor());
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
    void givenPageView_whenEntrySelected_expectImageLibrarySelectionApplied() throws IOException {

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);
        pageView.pageEntrySelected(pageView, pageView.pageEntryViews.get(2));

        ImageLibraryEntry imageLibraryEntry = new ImageLibraryEntry(TestUtils.getTestImageFile());
        pageView.libraryImageSelected(imageLibraryEntry);

        assertEquals(TestUtils.getTestImageFile().getAbsolutePath(), pageModel.getPageEntries().get(2).getImageFile().getAbsolutePath());
    }

    @Test
    void givenPageHasNoSize_andPositionEntriesInvoked_expectEntriesHaveNoSize() {

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);
        pageView.positionEntries();

        Dimension zeroDimension = new Dimension(0, 0);
        Rectangle zeroBounds = new Rectangle(0, 0, 0, 0);

        for (PageEntryView pageEntryView : pageView.pageEntryViews) {
            assertEquals(zeroDimension, pageEntryView.getSize());
            assertEquals(zeroBounds, pageEntryView.getBounds());
        }

    }

    @Test
    void givenPageHasCell_andSizeSet_andPositionEntriesInvoked_expectEntrySizeIsPageSize() {

        PageModel pageModel = new PageModel(PageGeometry.square(1), PageSize.A4_Landscape).withMargin(0);
        PageView pageView = new PageView(pageModel);
        pageView.setWidth(1200);
        pageView.positionEntries();

        PageEntryView pageEntryView = pageView.pageEntryViews.get(0);
        Dimension viewSize = pageView.getSize();
        Rectangle bounds = new Rectangle(0, 0, viewSize.width, viewSize.height);

        assertClose(viewSize, pageEntryView.getSize(), 2);
        assertClose(bounds, pageEntryView.getBounds(), 2);
    }

    @Test
    void givenPage2x2_andSizeSet_andPositionEntriesInvoked_expectEntrySizesSet() {

        int margin = 12;

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape).withMargin(margin);
        PageView pageView = new PageView(pageModel);
        pageView.setWidth(842);  // 1 - 1 mapping from viewsize to point size of page
        pageView.positionEntries();

        PageEntryView pageEntryView = pageView.pageEntryViews.get(3);

        int expectedWidth = (PageSize.A4_Landscape.width() - 3 * margin) / 2;
        int expectedHeight = (PageSize.A4_Landscape.height() - 3 * margin) / 2;
        int expectedX = (margin * 2) + expectedWidth;
        int expectedY = (margin * 2) + expectedHeight;

        Dimension entrySize = new Dimension(expectedWidth, expectedHeight);
        Rectangle entryPosition = new Rectangle(expectedX, expectedY, expectedWidth, expectedHeight);

        assertClose(entrySize, pageEntryView.getSize(), 2);
        assertClose(entryPosition, pageEntryView.getBounds(), 2);
    }

    @Test
    void givenPageView_whenClicked_expectedPageSelected() {

        PageModel pageModel = new PageModel(PageGeometry.square(1), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);

        assertNull(AppContext.INSTANCE.getSelectedPageView());

        pageView.mousePressed(TestUtils.createMouseClickEvent(pageView));
        assertNotNull(AppContext.INSTANCE.getSelectedPageView());
        assertEquals(AppContext.INSTANCE.getSelectedPageView(), pageView);
    }

    @Test
    void givenPageViewIsPreview_whenClicked_expectedPageNotSelected() {

        PageModel pageModel = new PageModel(PageGeometry.square(1), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);
        pageView.setPreviewMode(true);

        assertNull(AppContext.INSTANCE.getSelectedPageView());

        pageView.mousePressed(TestUtils.createMouseClickEvent(pageView));
        assertNull(AppContext.INSTANCE.getSelectedPageView());
    }
}
