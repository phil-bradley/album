/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common;

import ie.philb.album.ui.common.GridBagCellConstraints;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
class GridBagCellConstraintsTest {

    @Test
    void givenNoArgCtor_expectDefaults() {

        GridBagCellConstraints gbc = new GridBagCellConstraints();
        assertEquals(0, gbc.gridx);
        assertEquals(0, gbc.gridy);
        assertEquals(GridBagConstraints.NORTH, gbc.anchor);
        assertEquals(GridBagConstraints.NONE, gbc.fill);
        assertEquals(new Insets(0, 0, 0, 0), gbc.insets);
        assertEquals(0, gbc.weightx);
        assertEquals(0, gbc.weighty);
    }

    @Test
    void validateWeightSetters() {
        GridBagCellConstraints gbc = new GridBagCellConstraints();

        gbc.setWeight(0.2);
        assertEquals(0.2, gbc.weightx);
        assertEquals(0.2, gbc.weighty);

        gbc.weight(0.3, 0.4);
        assertEquals(0.3, gbc.weightx);
        assertEquals(0.4, gbc.weighty);

        gbc.weightx(0.6);
        assertEquals(0.6, gbc.weightx);

        gbc.weighty(0.5);
        assertEquals(0.5, gbc.weighty);
    }

    @Test
    void validateWeightSetterOverflow() {
        GridBagCellConstraints gbc = new GridBagCellConstraints();

        gbc.setWeight(1.2);
        assertEquals(1, gbc.weightx);
        assertEquals(1, gbc.weighty);

        gbc.weight(1.3, 1.4);
        assertEquals(1, gbc.weightx);
        assertEquals(1, gbc.weighty);
    }

    @Test
    void validateWeightSetterUnderflow() {
        GridBagCellConstraints gbc = new GridBagCellConstraints();

        gbc.setWeight(-1.2);
        assertEquals(0, gbc.weightx);
        assertEquals(0, gbc.weighty);

        gbc.weight(-1.3, -1.4);
        assertEquals(0, gbc.weightx);
        assertEquals(0, gbc.weighty);
    }

    @Test
    void validateAnchorSetters() {

        GridBagCellConstraints gbc = new GridBagCellConstraints();

        gbc.center();
        assertEquals(GridBagConstraints.CENTER, gbc.anchor);

        gbc.anchorNorth();
        assertEquals(GridBagConstraints.NORTH, gbc.anchor);

        gbc.anchorNorthEast();
        assertEquals(GridBagConstraints.NORTHEAST, gbc.anchor);

        gbc.anchorEast();
        assertEquals(GridBagConstraints.EAST, gbc.anchor);

        gbc.anchorSouthEast();
        assertEquals(GridBagConstraints.SOUTHEAST, gbc.anchor);

        gbc.anchorSouth();
        assertEquals(GridBagConstraints.SOUTH, gbc.anchor);

        gbc.anchorSouthWest();
        assertEquals(GridBagConstraints.SOUTHWEST, gbc.anchor);

        gbc.anchorWest();
        assertEquals(GridBagConstraints.WEST, gbc.anchor);

        gbc.anchorNorthWest();
        assertEquals(GridBagConstraints.NORTHWEST, gbc.anchor);

        gbc.anchorCenter();
        assertEquals(GridBagConstraints.CENTER, gbc.anchor);
    }

    @Test
    void validateFillSetters() {
        GridBagCellConstraints gbc = new GridBagCellConstraints();

        gbc.fillHorizontal();
        assertEquals(GridBagConstraints.HORIZONTAL, gbc.fill);

        gbc.fillVertical();
        assertEquals(GridBagConstraints.VERTICAL, gbc.fill);

        gbc.fillBoth();
        assertEquals(GridBagConstraints.BOTH, gbc.fill);

        gbc.fillNone();
        assertEquals(GridBagConstraints.NONE, gbc.fill);

        gbc.weight(0);

        gbc.fillHorizontal(1);
        assertEquals(GridBagConstraints.HORIZONTAL, gbc.fill);
        assertEquals(1, gbc.weightx);
        assertEquals(0, gbc.weighty);

        gbc.weight(0);

        gbc.fillVertical(1);
        assertEquals(GridBagConstraints.VERTICAL, gbc.fill);
        assertEquals(1, gbc.weighty);
        assertEquals(0, gbc.weightx);

        gbc.weight(0);

        gbc.fillBoth(1);
        assertEquals(GridBagConstraints.BOTH, gbc.fill);
        assertEquals(1, gbc.weightx);
        assertEquals(1, gbc.weighty);
    }

    @Test
    void validateGridSetters() {
        GridBagCellConstraints gbc = new GridBagCellConstraints();

        gbc.x(23);
        assertEquals(23, gbc.gridx);

        gbc.y(4);
        assertEquals(4, gbc.gridy);

        gbc.xy(6, 8);
        assertEquals(6, gbc.gridx);
        assertEquals(8, gbc.gridy);

        gbc.gridx(2);
        assertEquals(2, gbc.gridx);

        gbc.gridy(9);
        assertEquals(9, gbc.gridy);

        gbc.grid(12, 13);
        assertEquals(12, gbc.gridx);
        assertEquals(13, gbc.gridy);

        gbc.incx();
        assertEquals(13, gbc.gridx);

        gbc.incy();
        assertEquals(14, gbc.gridy);
    }

    @Test
    void validatePaddingSetters() {
        GridBagCellConstraints gbc = new GridBagCellConstraints();

        gbc.pad(10);
        assertEquals(10, gbc.ipadx);
        assertEquals(10, gbc.ipady);

        gbc.pad(11, 12);
        assertEquals(11, gbc.ipadx);
        assertEquals(12, gbc.ipady);

        gbc.padx(13);
        assertEquals(13, gbc.ipadx);
        assertEquals(12, gbc.ipady);

        gbc.pady(14);
        assertEquals(13, gbc.ipadx);
        assertEquals(14, gbc.ipady);
    }

    @Test
    void validateInsetSetters() {
        GridBagCellConstraints gbc = new GridBagCellConstraints();

        assertEquals(new Insets(0, 0, 0, 0), gbc.insets);

        gbc.inset(10);
        assertEquals(new Insets(10, 10, 10, 10), gbc.insets);

        gbc.insetTop(11);
        assertEquals(new Insets(11, 10, 10, 10), gbc.insets);

        gbc.insetLeft(12);
        assertEquals(new Insets(11, 12, 10, 10), gbc.insets);

        gbc.insetBottom(13);
        assertEquals(new Insets(11, 12, 13, 10), gbc.insets);

        gbc.insetRight(14);
        assertEquals(new Insets(11, 12, 13, 14), gbc.insets);

        gbc.insetVertical(15);
        assertEquals(new Insets(15, 12, 15, 14), gbc.insets);

        gbc.insetHorizontal(16);
        assertEquals(new Insets(15, 16, 15, 16), gbc.insets);

        gbc.inset(17, 18);
        assertEquals(new Insets(18, 17, 18, 17), gbc.insets);

        gbc.inset(19, 20, 21, 22);
        assertEquals(new Insets(19, 20, 21, 22), gbc.insets);
    }

    @Test
    void validateGridSizeSetters() {

        GridBagCellConstraints gbc = new GridBagCellConstraints();
        assertEquals(1, gbc.gridwidth);
        assertEquals(1, gbc.gridheight);

        gbc.width(10);
        assertEquals(10, gbc.gridwidth);
        assertEquals(1, gbc.gridheight);

        gbc.height(20);
        assertEquals(10, gbc.gridwidth);
        assertEquals(20, gbc.gridheight);
    }
}
