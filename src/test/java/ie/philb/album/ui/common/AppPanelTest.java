/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common;

import ie.philb.album.AppContext;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.border.LineBorder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
public class AppPanelTest {

    private static final AppContext APP_CONTEXT = AppContext.INSTANCE;

    @Test
    void givenAppPanel_expectGridBagLayout_andIsOpaque() {

        AppPanel appPanel = new AppPanel();

        assertInstanceOf(GridBagLayout.class, appPanel.getLayout());
        assertFalse(appPanel.isOpaque());
    }

    @Test
    void givenAppPanel_verifyForegroundColor() {
        AppPanel appPanel = new AppPanel();
        appPanel.foreground(Color.ORANGE);
        assertEquals(Color.ORANGE, appPanel.getForeground());
    }

    @Test
    void givenAppPanel_verifyBackgroundColor() {
        AppPanel appPanel = new AppPanel();
        appPanel.background(Color.CYAN);
        assertEquals(Color.CYAN, appPanel.getBackground());
    }

    @Test
    void givenAppPanel_verifyOpaque() {

        AppPanel appPanel = new AppPanel();
        assertFalse(appPanel.isOpaque());

        appPanel.opaque();
        assertTrue(appPanel.isOpaque());

        appPanel.opaque(false);
        assertFalse(appPanel.isOpaque());
    }

    @Test
    void givenAppPanel_verifyTransparent() {

        AppPanel appPanel = new AppPanel();
        assertFalse(appPanel.isOpaque());

        appPanel.opaque();
        assertTrue(appPanel.isOpaque());

        appPanel.transparent();
        assertFalse(appPanel.isOpaque());
    }

    @Test
    void givenAppPanel_verifyBorder() {

        AppPanel appPanel = new AppPanel();
        assertNull(appPanel.getBorder());

        appPanel.border(Color.red);
        assertNotNull(appPanel.getBorder());
        assertInstanceOf(LineBorder.class, appPanel.getBorder());

        LineBorder border = (LineBorder) appPanel.getBorder();
        assertEquals(1, border.getThickness());
        assertEquals(Color.red, border.getLineColor());

        appPanel.border(Color.MAGENTA, 3);
        border = (LineBorder) appPanel.getBorder();
        assertEquals(3, border.getThickness());
        assertEquals(Color.MAGENTA, border.getLineColor());
    }

    @Test
    void givenPanel_verifySizeSetsSize_andPreferredSize() {
        AppPanel appPanel = new AppPanel();
        appPanel.setSize(new Dimension(1, 1));
        appPanel.setMinimumSize(new Dimension(2, 2));
        appPanel.setMaximumSize(new Dimension(3, 3));
        appPanel.setPreferredSize(new Dimension(4, 4));

        Dimension size = new Dimension(123, 456);
        appPanel.size(size);

        assertEquals(size, appPanel.getPreferredSize());
        assertEquals(size, appPanel.getSize());
        assertEquals(new Dimension(2, 2), appPanel.getMinimumSize());
        assertEquals(new Dimension(3, 3), appPanel.getMaximumSize());
    }

    @Test
    void givenPanel_verifyLockSizeSetsSize_andPreferredSize_andMinimumSize_andMaximumSize() {
        AppPanel appPanel = new AppPanel();

        Dimension size = new Dimension(123, 456);
        appPanel.lockSize(size);

        assertEquals(size, appPanel.getPreferredSize());
        assertEquals(size, appPanel.getSize());
        assertEquals(size, appPanel.getMinimumSize());
        assertEquals(size, appPanel.getMaximumSize());
    }

    @Test
    void givenPanelFiller_expectTransparentPanel() {
        AppPanel appPanel = new AppPanel();
        AppPanel fillerPanel = appPanel.filler();
        assertFalse(fillerPanel.isOpaque());
        assertEquals(new Dimension(0, 0), fillerPanel.getSize());
    }

    @Test
    void givenPanelFillerWithColor_expectOpaquePanel() {
        AppPanel appPanel = new AppPanel();
        AppPanel fillerPanel = appPanel.filler(Color.YELLOW);
        assertTrue(fillerPanel.isOpaque());
        assertEquals(Color.YELLOW, fillerPanel.getBackground());
        assertEquals(new Dimension(0, 0), fillerPanel.getSize());
    }

    @Test
    void givenPanelFillerWithSize_expectTransparentPanel() {
        AppPanel appPanel = new AppPanel();

        Dimension size = new Dimension(432, 562);
        AppPanel fillerPanel = appPanel.filler(size);

        assertFalse(fillerPanel.isOpaque());
        assertEquals(size, fillerPanel.getSize());
        assertEquals(size, fillerPanel.getPreferredSize());
        assertEquals(size, fillerPanel.getMinimumSize());
    }

    @Test
    void givenPanelFillerWithSizeAndColor_expectOpaquePanel() {
        AppPanel appPanel = new AppPanel();

        Dimension size = new Dimension(432, 562);
        AppPanel fillerPanel = appPanel.filler(size, Color.DARK_GRAY);

        assertTrue(fillerPanel.isOpaque());
        assertEquals(Color.DARK_GRAY, fillerPanel.getBackground());
        assertEquals(size, fillerPanel.getSize());
        assertEquals(size, fillerPanel.getPreferredSize());
        assertEquals(size, fillerPanel.getMinimumSize());
    }

    @Test
    void givenPanel_expectRegisteredAsApplicationListener() {
        AppPanel appPanel = new AppPanel();
        assertTrue(APP_CONTEXT.listeners().contains(appPanel));
    }

    @Test
    void givenPanelRemoved_expectUnregisteredAsApplicationListener() {
        AppPanel appPanel = new AppPanel();
        assertTrue(APP_CONTEXT.listeners().contains(appPanel));

        appPanel.removeNotify();
        assertFalse(APP_CONTEXT.listeners().contains(appPanel));
    }
}
