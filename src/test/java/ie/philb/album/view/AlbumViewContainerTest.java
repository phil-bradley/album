/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.AppContext;
import ie.philb.album.model.PageEntryModel;
import ie.philb.album.model.PageModel;
import ie.philb.album.ui.common.filters.BrightnessFilter;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author philb
 */
@ExtendWith(MockitoExtension.class)
public class AlbumViewContainerTest {

    @Mock
    private AppContext appContext;

    @Mock
    private PageView pageView;

    @Mock
    private PageEntryView pageEntryView;

    @Mock
    private PageEntryModel pageEntryModel;

    @Mock
    private PageModel pageModel;

    private AlbumViewContainer container;

    @BeforeEach
    void setUp() throws Exception {
        // Replace the static AppContext.INSTANCE with our mock
        FieldUtils.writeStaticField(AppContext.class, "INSTANCE", appContext, true);

        // Mock default context responses
        when(appContext.getSelectedPageView()).thenReturn(pageView);
        when(appContext.getSelectedPageEntryView()).thenReturn(pageEntryView);
        when(pageView.getPageModel()).thenReturn(pageModel);
        when(pageEntryView.getPageEntryModel()).thenReturn(pageEntryModel);
        when(pageEntryModel.getBrightnessAdjustment()).thenReturn(BrightnessFilter.DEFAULT_BRIGHTNESS);

        // Create instance
        container = new AlbumViewContainer();
    }

    @Test
    void adjustBrightness_shouldSetBrightnessOnSelectedEntry() throws Exception {

        when(appContext.getSelectedPageEntryView()).thenReturn(pageEntryView);
        when(pageEntryView.getPageEntryModel()).thenReturn(pageEntryModel);

        // use reflection to call private method directly
        var method = AlbumViewContainer.class.getDeclaredMethod("adjustBrightness");
        method.setAccessible(true);
        method.invoke(container);

        verify(pageEntryModel, atLeastOnce()).setBrightnessAdjustment(anyInt());
    }
}
