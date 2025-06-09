/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common;

import ie.philb.album.AppContext;
import ie.philb.album.ApplicationListener;
import ie.philb.album.ui.config.UiConfig;
import ie.philb.album.ui.config.UiConfigListener;
import ie.philb.album.ui.imagelibrary.ImageLibraryEntry;
import ie.philb.album.view.PageEntryView;
import ie.philb.album.view.PageView;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.UUID;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Philip.Bradley
 */
public class AppPanel extends JPanel implements UiConfigListener, MouseListener, MouseMotionListener, ApplicationListener {

    private static final long serialVersionUID = 1L;
    protected static final Logger LOG = LoggerFactory.getLogger(AppPanel.class);
    private final UUID panelId = UUID.randomUUID();
    protected static final Logger logger = LoggerFactory.getLogger(AppPanel.class);

    public AppPanel() {
        super();

        gridbag();
        opaque(false);
        setName(getClass().getSimpleName());

        AppContext.INSTANCE.addListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        removeMouseMotionListener(this);
        removeMouseListener(this);
        AppContext.INSTANCE.removeListener(this);
    }

    public AppPanel layoutGridBag() {
        setLayout(new GridBagLayout());
        return this;
    }

    public AppPanel(LayoutManager lm) {
        super(lm);
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

    public final AppPanel gridbag() {
        this.setLayout(new GridBagLayout());
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

    public AppPanel border(Border border) {
        setBorder(border);
        return this;
    }

    public AppPanel lockSize(Dimension d) {
        setPreferredSize(d);
        setMinimumSize(d);
        setMaximumSize(d);
        setSize(d);
        return this;
    }

    public AppPanel size(Dimension d) {
        this.setSize(d);
        this.setPreferredSize(d);
        return this;
    }

    @Override
    public void setPreferredSize(Dimension dimension) {

        //logger.info("Set preferred size {}", dimension);
        Dimension preferredSize = getPreferredSize();

        if (preferredSize.getWidth() != dimension.getWidth() || preferredSize.getHeight() != dimension.getHeight()) {
            super.setPreferredSize(dimension);
        }
    }

    public AppPanel preferredSize(Dimension d) {
        this.setPreferredSize(d);
        return this;
    }

    public AppPanel preferredSize(int width, int height) {
        return preferredSize(new Dimension(width, height));
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
        return ap;
    }

    public AppPanel filler(Dimension size, Color color) {
        AppPanel filler = filler(size);
        filler.background(color);
        return filler;
    }

    @Override
    public void uiConfigUpdated(UiConfig config) {
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void pageEntrySelected(PageView pageView, PageEntryView view) {
    }

    @Override
    public void pageSelected(PageView view) {
    }

    @Override
    public void libraryImageSelected(ImageLibraryEntry entry) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }

    @Override
    public void browseLocationUpdated(File file) {
    }

    @Override
    public void albumUpdated() {
    }
}
