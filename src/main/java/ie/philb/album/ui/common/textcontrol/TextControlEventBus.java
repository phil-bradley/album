/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author philb
 */
public enum TextControlEventBus implements TextControlEventListener {

    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(TextControlEventBus.class);

    private final List<TextControlEventListener> listeners = new ArrayList<>();

    public void addListener(TextControlEventListener l) {
        this.listeners.add(l);
    }

    public void removeListener(TextControlEventListener l) {
        this.listeners.remove(l);
    }

    @Override
    public void formatUpdated(TextContent content) {
        listeners().forEach(l -> l.formatUpdated(content));
    }

    @Override
    public void contentUpdated(TextContent content) {
        listeners().forEach(l -> l.contentUpdated(content));
    }

    // Creates a copy of the collection so it can be safely iterated over
    private List<TextControlEventListener> listeners() {
        return new ArrayList<>(listeners);
    }
}
