/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common;

import ie.philb.album.AppContext;
import ie.philb.album.ApplicationListener;
import ie.philb.album.ui.config.UiConfigListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.UUID;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philip.Bradley
 */
public class AppPanel extends JPanel implements UiConfigListener, DefaultMouseListener, DefaultMouseMotionListener, ApplicationListener, DefaultMouseWheelListener {

    private static final long serialVersionUID = 1L;
    protected static final Logger LOG = LoggerFactory.getLogger(AppPanel.class);
    private final UUID panelId = UUID.randomUUID();
    protected static final Logger logger = LoggerFactory.getLogger(AppPanel.class);

    public AppPanel() {
        super();

        setLayout(new GridBagLayout());
        opaque(false);
        setName(getClass().getSimpleName());

        AppContext.INSTANCE.addListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        removeMouseWheelListener(this);
        removeMouseMotionListener(this);
        removeMouseListener(this);
        AppContext.INSTANCE.removeListener(this);
    }

    public AppPanel foreground(Color fg) {
        this.setForeground(fg);
        return this;
    }

    public AppPanel background(Color bg) {
        this.setBackground(bg);
        this.setOpaque(true);
        return this;
    }

    public AppPanel opaque() {
        this.setOpaque(true);
        return this;
    }

    public AppPanel transparent() {
        this.setOpaque(false);
        return this;
    }

    public final AppPanel opaque(boolean b) {
        this.setOpaque(b);
        return this;
    }

    public AppPanel border(Color c, int width) {
        this.setBorder(new LineBorder(c, width));
        return this;
    }

    public AppPanel border(Color c) {
        return border(c, 1);
    }

    public AppPanel lockSize(Dimension d) {
        setPreferredSize(d);
        setMinimumSize(d);
        setMaximumSize(d);
        setSize(d);
        return this;
    }

    public AppPanel size(int width, int height) {
        return size(new Dimension(width, height));
    }
    
    public AppPanel size(Dimension d) {
        this.setSize(d);
        this.setPreferredSize(d);
        return this;
    }

    public AppPanel filler(Color background) {
        AppPanel ap = new AppPanel();
        ap.setOpaque(true);
        ap.setBackground(background);
        return ap;
    }

    public AppPanel filler() {
        AppPanel ap = new AppPanel();
        ap.setOpaque(false);
        return ap;
    }

    public AppPanel filler(Dimension size) {
        AppPanel ap = new AppPanel();
        ap.setOpaque(false);
        ap.setPreferredSize(size);
        ap.setMinimumSize(size);
        ap.setSize(size);
        return ap;
    }

    public AppPanel filler(Dimension size, Color color) {
        return filler(size).background(color);
    }

}
