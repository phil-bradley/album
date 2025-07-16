/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.numbercontrol;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author philb
 */
public class SlidingNumberControlModel {

    private final List<SlidingNumberControlListener> listeners = new ArrayList<>();
    private final int minValue;
    private final int maxValue;
    private final int stepValue;
    private final int defaultValue;
    private int currentValue = -1;

    public SlidingNumberControlModel(int minValue, int maxValue) {
        this(minValue, maxValue, 1, minValue);
    }

    public SlidingNumberControlModel(int minValue, int maxValue, int stepValue, int defaultValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.stepValue = stepValue;
        this.defaultValue = defaultValue;

        setValue(defaultValue);
        fireValueUpdated();
    }

    public void addListener(SlidingNumberControlListener l) {
        this.listeners.add(l);
    }

    public void removeListeners(SlidingNumberControlListener l) {
        this.listeners.remove(l);
    }

    public final void setValue(int value) {
        validateValue(value);

        if (currentValue != value) {
            this.currentValue = value;
            fireValueUpdated();
        }
    }

    public int getValue() {
        return currentValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getStepValue() {
        return stepValue;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public void resetValue() {
        setValue(defaultValue);
    }

    private void validateValue(int value) {

        if (value < minValue) {
            throw new IllegalArgumentException("Value " + value + " < minimum value: " + minValue);
        }

        if (value > maxValue) {
            throw new IllegalArgumentException("Value " + value + " > maximum value: " + maxValue);
        }
    }

    private void fireValueUpdated() {
        for (SlidingNumberControlListener listener : listeners) {
            listener.valueUpdated(currentValue);
        }
    }
}
