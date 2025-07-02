/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import ie.philb.album.ui.common.GridBagCellConstraints;
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
public class TextControl extends JPanel implements TextControlChangeListener {

    private Dimension physicalSize = new Dimension(0, 0);
    private boolean previewMode = false;
    private TextControlEditorToolBar toolBar;
    private TextControlViewEditPanel viewEditPanel;
    private JLayeredPane layeredPane = new JLayeredPane();
    private final TextControlModel model;

    public TextControl(TextControlModel model) {

        this.model = model;

        viewEditPanel = new TextControlViewEditPanel(model);
        toolBar = new TextControlEditorToolBar(model);

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

                if (previewMode) {
                    return;
                }

                if (e.getClickCount() == 2) {
                    setEditEnabled(true);
                }
            }
        });

        toolBar.setVisible(false);

        model.addChangeListener(this);
    }

    public boolean isPreviewMode() {
        return previewMode;
    }

    public void setPreviewMode(boolean previewMode) {
        this.previewMode = previewMode;
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
    public void textUpdated(TextControlModel model) {
        setEditEnabled(false);
    }

    public void setEditEnabled(boolean isEnabled) {
        viewEditPanel.setEditor(isEnabled);
        toolBar.setVisible(isEnabled);
    }

    @Override
    public void requestFocus() {
        viewEditPanel.requestFocus();
    }

    @Override
    public void textEditCancelled(TextControlModel model) {
        setEditEnabled(false);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(Color.yellow);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setPreferredSize(new Dimension(400, 250));
        frame.setSize(frame.getPreferredSize());

        TextControlModel model = new TextControlModel();
        model.setText("Hello There!");
        model.setFontColor(Color.MAGENTA);
        model.setBold(true);

        TextControl ctrl = new TextControl(model);

        ctrl.setPreferredSize(new Dimension(300, 200));
        ctrl.setSize(ctrl.getPreferredSize());

        frame.add(ctrl);
        frame.setVisible(true);
    }

}
