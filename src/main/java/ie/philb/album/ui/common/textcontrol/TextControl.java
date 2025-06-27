/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.common.font.ApplicationFont;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author philb
 */
public class TextControl extends JPanel implements TextControlEventListener {

    private Dimension physicalSize = new Dimension(0, 0);

    private TextControlEditorToolBar toolBar = new TextControlEditorToolBar();
    private ViewEditPanel viewEditPanel = new ViewEditPanel();
    private JLayeredPane layeredPane = new JLayeredPane();

    public TextControl() {

        setLayout(new GridBagLayout());
        setOpaque(false);

        layeredPane.add(viewEditPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(toolBar, JLayeredPane.POPUP_LAYER);
        toolBar.setBounds(0, 0, 300, 30);

        GridBagCellConstraints gbc = new GridBagCellConstraints().fillBoth().weight(1).anchorNorth();
        add(layeredPane, gbc);

        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = layeredPane.getSize();
                viewEditPanel.setBounds(0, 0, size.width, size.height);
                toolBar.setBounds(0, 0, size.width, 30);
                viewEditPanel.setFontScalingFactor(getPhysicalToViewScalingFactor());
            }
        });

        viewEditPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (getWidth() < 100) {
                    return;
                }

                if (e.getClickCount() == 2) {
                    setEditEnabled(true);
                }
            }
        });

        toolBar.setVisible(false);
    }

    public void setTextContent(TextContent content) {
        TextControlEventBus.INSTANCE.formatUpdated(content);
        TextControlEventBus.INSTANCE.contentUpdated(content);
    }

    public Dimension getPhysicalSize() {
        return physicalSize;
    }

    public void setPhysicalSize(Dimension physicalSize) {
        this.physicalSize = physicalSize;
    }

    private double getPhysicalToViewScalingFactor() {

        double viewWidth = getSize().width;

        if (viewWidth == 0) {
            return 0;
        }

        double physicalWidth = physicalSize.width;

        // No scaling set, assume 1:1
        if (physicalWidth == 0) {
            return 1;
        }

        return viewWidth / physicalWidth;
    }

    @Override
    public void contentUpdated(TextContent content) {
        setEditEnabled(false);
    }

    @Override
    public void formatUpdated(TextContent content) {
    }

    private void setEditEnabled(boolean isEnabled) {
        viewEditPanel.setEditor(isEnabled);
        toolBar.setVisible(isEnabled);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(Color.yellow);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setPreferredSize(new Dimension(400, 250));
        frame.setSize(frame.getPreferredSize());

        TextControl ctrl = new TextControl();
        ctrl.setTextContent(new TextContent("Hello there", true, true, true, ApplicationFont.Caveat.name(), 18, Color.PINK));
        ctrl.setPreferredSize(new Dimension(300, 200));
        ctrl.setSize(ctrl.getPreferredSize());

        frame.add(ctrl);
        frame.setVisible(true);
    }

}
