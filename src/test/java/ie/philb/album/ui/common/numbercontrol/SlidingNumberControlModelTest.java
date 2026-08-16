/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ie.philb.album.ui.common.numbercontrol;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 *
 * @author philb
 */
class SlidingNumberControlModelTest {

    @Test
    public void givenModel_expectValuesInitialised() {

        int minValue = 20;
        int maxValue = 100;

        SlidingNumberControlModel model = new SlidingNumberControlModel(minValue, maxValue);
        assertEquals(minValue, model.getMinValue());
        assertEquals(maxValue, model.getMaxValue());
        assertEquals(minValue, model.getValue());
        assertEquals(minValue, model.getDefaultValue());
        assertEquals(1, model.getStepValue());
    }

    @Test
    public void givenModel_whenValueUpdated_expectListenerInvoked() {

        int minValue = 0;
        int maxValue = 100;

        SlidingNumberControlModel model = new SlidingNumberControlModel(minValue, maxValue);
        TestListener listener = new TestListener();
        model.addListener(listener);
        model.setValue(17);

        assertEquals(17, listener.updatedValue);
    }

    @Test
    public void givenModel_whenListenerRemoved_expectListenerNotInvoked() {

        int minValue = 0;
        int maxValue = 100;

        SlidingNumberControlModel model = new SlidingNumberControlModel(minValue, maxValue);
        TestListener listener = new TestListener();
        model.addListener(listener);
        model.setValue(17);

        assertEquals(17, listener.updatedValue);

        // Remove listener, set the value and expect this will 
        // not be applied to the listener
        model.removeListeners(listener);
        model.setValue(13);

        // Still has original value
        assertEquals(17, listener.updatedValue);
    }

    @Test
    public void givenModelWithMinMaxValue_whenValueSet_expectException() {

        int minValue = 10;
        int maxValue = 100;

        SlidingNumberControlModel model = new SlidingNumberControlModel(minValue, maxValue);
        assertThrows(IllegalArgumentException.class, () -> {
            model.setValue(3);

        });

        assertThrows(IllegalArgumentException.class, () -> {
            model.setValue(101);

        });

        assertDoesNotThrow(() -> {
            model.setValue(100);
        });

        assertDoesNotThrow(() -> {
            model.setValue(10);
        });
    }

    class TestListener implements SlidingNumberControlListener {

        int updatedValue;

        @Override
        public void valueUpdated(int value) {
            this.updatedValue = value;
        }

    }
}
