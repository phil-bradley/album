/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.common;

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
    void validateSetters() {
        GridBagCellConstraints gbc = new GridBagCellConstraints();

        gbc.x(23);
        assertEquals(23, gbc.gridx);

        gbc.y(4);
        assertEquals(4, gbc.gridy);

        gbc.xy(6, 8);
        assertEquals(6, gbc.gridx);
        assertEquals(8, gbc.gridy);

        gbc.setWeight(0.2);
        assertEquals(0.2, gbc.weightx);
        assertEquals(0.2, gbc.weighty);

        gbc.weight(0.3, 0.4);
        assertEquals(0.3, gbc.weightx);
        assertEquals(0.4, gbc.weighty);

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
}
