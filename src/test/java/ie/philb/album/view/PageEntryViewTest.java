/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.AppEventBus;
import ie.philb.album.AppSession;
import ie.philb.album.Context;
import ie.philb.album.model.PageCell;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageModel;
import ie.philb.album.model.PageSize;
import ie.philb.album.util.ImageUtils;
import ie.philb.album.util.TestUtils;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context(null, new AppSession(new AppEventBus()));
    }

    @Test
    void givenPageEntryView_whenZoomedIn_expectedModelZoomIn() {

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(context, pageModel);

        PageCell pageCell = new PageCell(new Dimension(1, 1), new Point(0, 0));
        PageEntryModel pageEntryModel = new PageEntryModel(pageCell);

        PageEntryView pageEntryView = new PageEntryView(context, pageView, pageEntryModel);
        pageEntryView.zoomIn();

        assertEquals(Double.valueOf("1.1"), pageEntryModel.getZoomFactor());
    }

    @Test
    void givenPageEntryView_whenZoomedOut_expectedModelZoomOut() {

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageView pageView = new PageView(context, pageModel);

        PageCell pageCell = new PageCell(new Dimension(1, 1), new Point(0, 0));
        PageEntryModel pageEntryModel = new PageEntryModel(pageCell);

        PageEntryView pageEntryView = new PageEntryView(context, pageView, pageEntryModel);
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
        PageView pageView = new PageView(context, pageModel);

        PageCell pageCell = new PageCell(new Dimension(1, 1), new Point(0, 0));
        PageEntryModel pageEntryModel = new PageEntryModel(pageCell);
        pageEntryModel.setImageFile(TestUtils.getTestImageFile());

        PageEntryView pageEntryView = new PageEntryView(context, pageView, pageEntryModel);
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
        PageView pageView = new PageView(context, pageModel);

        PageCell pageCell = new PageCell(new Dimension(1, 1), new Point(0, 0));
        PageEntryModel pageEntryModel = new PageEntryModel(pageCell);
        pageEntryModel.setImageFile(TestUtils.getTestImageFile());

        PageEntryView pageEntryView = new PageEntryView(context, pageView, pageEntryModel);
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
        PageView pageView = new PageView(context, pageModel);

        PageEntryView pageEntryView = new PageEntryView(context, pageView, pageModel.getPageEntries().get(0));
        pageModel.setImage(TestUtils.getTestImageFile(), 0);

        assertNull(context.session().getSelectedPageView());
        assertNull(context.session().getSelectedPageEntryView());

        pageEntryView.mousePressed(TestUtils.createMouseClickEvent(pageEntryView));

        assertEquals(context.session().getSelectedPageView(), pageView);
        assertEquals(context.session().getSelectedPageEntryView(), pageEntryView);
    }

    @Test
    void givenPageEntryViewIsPreviewMode_whenClick_expectedSelected() {

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageEntryView pageEntryView = new PageEntryView(context, new PageView(context, pageModel), pageModel.getPageEntries().get(0));
        pageEntryView.setPreviewMode(true);

        assertNull(context.session().getSelectedPageView());
        assertNull(context.session().getSelectedPageEntryView());

        pageEntryView.mousePressed(TestUtils.createMouseClickEvent(pageEntryView));

        assertNull(context.session().getSelectedPageView());
        assertNull(context.session().getSelectedPageEntryView());
    }

    @Test
    void givenPageEntryView_whenMouseDragged_expectViewOffset() {
        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageEntryView pageEntryView = new PageEntryView(context, new PageView(context, pageModel), pageModel.getPageEntries().get(0));
        pageEntryView.getPageEntryModel().setImageFile(TestUtils.getTestImageFile());

        MouseEvent startEvent = TestUtils.createMouseClickEvent(pageEntryView, new Point(10, 20));
        pageEntryView.mousePressed(startEvent);

        assertEquals(new Point(0, 0), pageEntryView.viewOffset);
        assertEquals(new Point(10, 20), pageEntryView.mouseDragStartPoint);

        MouseEvent dragEvent = TestUtils.createMouseClickEvent(pageEntryView, new Point(60, 80));
        pageEntryView.mouseDragged(dragEvent);

        assertEquals(new Point(50, 60), pageEntryView.viewOffset);
    }

    @Test
    void givenPageEntryViewNoImage_whenMouseDragged_expectNoViewOffset() {

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageEntryView pageEntryView = new PageEntryView(context, new PageView(context, pageModel), pageModel.getPageEntries().get(0));

        MouseEvent startEvent = TestUtils.createMouseClickEvent(pageEntryView, new Point(10, 20));
        pageEntryView.mousePressed(startEvent);

        assertEquals(new Point(0, 0), pageEntryView.viewOffset);
        assertEquals(new Point(0, 0), pageEntryView.mouseDragStartPoint);

        MouseEvent dragEvent = TestUtils.createMouseClickEvent(pageEntryView, new Point(60, 80));
        pageEntryView.mouseDragged(dragEvent);

        assertEquals(new Point(0, 0), pageEntryView.viewOffset);
    }

    @Test
    void givenPageEntryViewPreviewMode_whenMouseDragged_expectNoViewOffset() {

        PageModel pageModel = new PageModel(PageGeometry.square(2), PageSize.A4_Landscape);
        PageEntryView pageEntryView = new PageEntryView(context, new PageView(context, pageModel), pageModel.getPageEntries().get(0));
        pageEntryView.getPageEntryModel().setImageFile(TestUtils.getTestImageFile());
        pageEntryView.setPreviewMode(true);

        MouseEvent startEvent = TestUtils.createMouseClickEvent(pageEntryView, new Point(10, 20));
        pageEntryView.mousePressed(startEvent);

        assertEquals(new Point(0, 0), pageEntryView.viewOffset);
        assertEquals(new Point(0, 0), pageEntryView.mouseDragStartPoint);

        MouseEvent dragEvent = TestUtils.createMouseClickEvent(pageEntryView, new Point(60, 80));
        pageEntryView.mouseDragged(dragEvent);

        assertEquals(new Point(0, 0), pageEntryView.viewOffset);
    }

    @Test
    @Disabled
    void givenPageEntryView_whenMousePressedAndScrolledUp_expectedZoomIn() {

    }

    @Test
    @Disabled
    void givenPageEntryView_whenMousePressedAndScrolledDown_expectedZoomOut() {

    }

    @Test
    @Disabled
    void givenPageEntryView_whenMouseNotPressed_expectNoScrollEffect() {

    }
}
