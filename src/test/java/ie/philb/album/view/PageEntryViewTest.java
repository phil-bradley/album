/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.AppContext;
import ie.philb.album.model.PageCell;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageModel;
import ie.philb.album.model.PageSize;
import ie.philb.album.util.ImageUtils;
import ie.philb.album.util.TestUtils;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class PageEntryViewTest {

    @BeforeEach
    void setUp() {
        AppContext.INSTANCE.pageEntrySelected(null, null);
    }

    @Test
    void givenPageEntryView_whenZoomedIn_expectedModelZoomIn() {

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);

        PageCell pageCell = new PageCell(new Dimension(1, 1), new Point(0, 0));
        PageEntryModel pageEntryModel = new PageEntryModel(pageCell);

        PageEntryView pageEntryView = new PageEntryView(pageView, pageEntryModel);
        pageEntryView.zoomIn();

        assertEquals(Double.valueOf("1.1"), pageEntryModel.getZoomFactor());
    }

    @Test
    void givenPageEntryView_whenZoomedOut_expectedModelZoomOut() {

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);

        PageCell pageCell = new PageCell(new Dimension(1, 1), new Point(0, 0));
        PageEntryModel pageEntryModel = new PageEntryModel(pageCell);

        PageEntryView pageEntryView = new PageEntryView(pageView, pageEntryModel);
        pageEntryView.zoomOut();

        MatcherAssert.assertThat(pageEntryModel.getZoomFactor(), Matchers.closeTo(0.909, 0.01));
    }

    @Test
    void givenPageEntryView_whenZoomToFit_expectedScaledImageFitsInsideView() throws Exception {

        int VIEW_WIDTH = 100;
        int VIEW_HEIGHT = 100;

        BufferedImage testImage = ImageUtils.readBufferedImage(TestUtils.getTestImageFile());
        Dimension testImageSize = ImageUtils.getImageSize(testImage);
        double imageAspectRatio = ImageUtils.getAspectRatio(testImageSize);
        int expectedHeight = ImageUtils.getHeightFromWidth(VIEW_WIDTH, imageAspectRatio);

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);

        PageCell pageCell = new PageCell(new Dimension(1, 1), new Point(0, 0));
        PageEntryModel pageEntryModel = new PageEntryModel(pageCell);
        pageEntryModel.setImageFile(TestUtils.getTestImageFile());

        PageEntryView pageEntryView = new PageEntryView(pageView, pageEntryModel);
        pageEntryView.size(new Dimension(VIEW_WIDTH, VIEW_HEIGHT));
        pageEntryView.setBounds(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
        pageEntryView.zoomToFit();

        BufferedImage scaledImage = pageEntryView.getViewImage();
        assertEquals(VIEW_WIDTH, scaledImage.getWidth());
        assertEquals(expectedHeight, scaledImage.getHeight());
    }

    @Test
    @Disabled
    void givenPageEntryView_whenZoomToFit_expectedScaledImageIsCentered() throws Exception {

        int VIEW_WIDTH = 100;
        int VIEW_HEIGHT = 100;

        BufferedImage testImage = ImageUtils.readBufferedImage(TestUtils.getTestImageFile());
        Dimension testImageSize = ImageUtils.getImageSize(testImage);
        double imageAspectRatio = ImageUtils.getAspectRatio(testImageSize);
        int expectedHeight = ImageUtils.getHeightFromWidth(VIEW_WIDTH, imageAspectRatio);

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);

        PageCell pageCell = new PageCell(new Dimension(1, 1), new Point(0, 0));
        PageEntryModel pageEntryModel = new PageEntryModel(pageCell);
        pageEntryModel.setImageFile(TestUtils.getTestImageFile());

        PageEntryView pageEntryView = new PageEntryView(pageView, pageEntryModel);
        pageEntryView.size(new Dimension(VIEW_WIDTH, VIEW_HEIGHT));
        pageEntryView.setBounds(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
        pageEntryView.zoomToFit();

        assertTrue(expectedHeight < VIEW_HEIGHT);
        int verticalSpace = (VIEW_HEIGHT - expectedHeight);
        int expectedYOffset = verticalSpace / 2;

        Point offset = pageEntryView.getViewImageOffset();
        assertEquals(0, offset.x);
        assertEquals(expectedYOffset, offset.y);
    }

    @Test
    void givenPageEntryView_whenClick_expectedSelected() {

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);

        PageEntryView pageEntryView = new PageEntryView(pageView, pageModel.getPageEntries().get(0));
        pageModel.setImage(TestUtils.getTestImageFile(), 0);
             
        assertNull(AppContext.INSTANCE.getSelectedPageView());
        assertNull(AppContext.INSTANCE.getSelectedPageEntryView());

        pageEntryView.mousePressed(TestUtils.createMouseClickEvent(pageEntryView));

        assertEquals(AppContext.INSTANCE.getSelectedPageView(), pageView);
        assertEquals(AppContext.INSTANCE.getSelectedPageEntryView(), pageEntryView);
    }

    @Test
    void givenPageEntryViewIsPreviewMode_whenClick_expectedSelected() {

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(pageModel);

        PageCell pageCell = new PageCell(new Dimension(1, 1), new Point(0, 0));
        PageEntryModel pageEntryModel = new PageEntryModel(pageCell);

        PageEntryView pageEntryView = new PageEntryView(pageView, pageEntryModel);
        pageEntryView.setPreviewMode(true);

        assertNull(AppContext.INSTANCE.getSelectedPageView());
        assertNull(AppContext.INSTANCE.getSelectedPageEntryView());

        pageEntryView.mousePressed(TestUtils.createMouseClickEvent(pageEntryView));

        assertNull(AppContext.INSTANCE.getSelectedPageView());
        assertNull(AppContext.INSTANCE.getSelectedPageEntryView());
    }
}
