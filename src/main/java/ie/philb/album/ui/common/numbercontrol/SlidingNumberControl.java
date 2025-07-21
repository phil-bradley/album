/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.numbercontrol;

import ie.philb.album.ui.common.AppPanel;
import ie.philb.album.ui.resources.Icons;
import ie.philb.album.ui.common.font.fields.IntField;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author philb
 */
public class SlidingNumberControl extends AppPanel implements SlidingNumberControlListener {

    private final List<SlidingNumberControlListener> listeners = new ArrayList<>();

    private JSlider slider;
    private IntField field;
    private JButton btnReset;
    private final SlidingNumberControlModel model;

    public SlidingNumberControl(SlidingNumberControlModel model) {
        this.model = model;

        initControls();
        layoutControls();
        this.model.addListener(this);
    }

    public void addListener(SlidingNumberControlListener l) {
        this.listeners.add(l);
    }

    public void removeListeners(SlidingNumberControlListener l) {
        this.listeners.remove(l);
    }

    public void setValue(int value) {
        model.setValue(value);
    }

    public int getValue() {
        return model.getValue();
    }

    private void layoutControls() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(Box.createRigidArea(new Dimension(4, 4)));
        add(field);
        add(slider);
        add(btnReset);
    }

    private void initControls() {

        field = new IntField(model.getMinValue(), model.getMaxValue());
        field.setPreferredSize(new Dimension(40, 30));
        field.setMaximumSize(field.getPreferredSize());
        field.addActionListener((ActionEvent ae) -> {
            setValue(field.getValue());
        });
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                setValue(field.getValue());
            }
        });

        slider = new JSlider(model.getMinValue(), model.getMaxValue(), model.getDefaultValue());
        slider.setExtent(model.getStepValue());
        slider.setPaintTicks(true);
        slider.setPaintTrack(true);
        slider.setPaintLabels(true);

        if (model.getDefaultValue() != 0) {
            Hashtable t = new Hashtable<Integer, JLabel>();
            t.put(model.getDefaultValue(), new JLabel("Default: " + model.getDefaultValue()));
            slider.setLabelTable(t);
        }

        slider.addChangeListener((ChangeEvent ce) -> {
            setValue(slider.getValue());
        });

        btnReset = new JButton(Icons.Small.RESET);
        btnReset.addActionListener((ActionEvent ae) -> {
            model.resetValue();
        });
        
        
        field.setValue(model.getDefaultValue());
        slider.setValue(model.getDefaultValue());
    }

    @Override
    public void valueUpdated(int newValue) {

        boolean updated = false;

        if (field.getValue() != newValue) {
            field.setValue(newValue);
            updated=true;
        }

        if (slider.getValue() != newValue) {
            slider.setValue(newValue);
            updated=true;
        }
        
        if (updated) {
            fireValueUpdated();
        }
    }

    private void fireValueUpdated() {
        for (SlidingNumberControlListener listener : listeners) {
            listener.valueUpdated(model.getValue());
        }
    }
}
