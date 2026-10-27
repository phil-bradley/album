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
    private TextControlPane textControlPane;
    private JLayeredPane layeredPane = new JLayeredPane();
    private final TextControlModel model;

    public TextControl(TextControlModel model) {

        this.model = model;

        textControlPane = new TextControlPane(model);
        toolBar = new TextControlEditorToolBar(model);

        setLayout(new GridBagLayout());
        setOpaque(false);

        layeredPane.add(textControlPane, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(toolBar, JLayeredPane.POPUP_LAYER);
        toolBar.setBounds(0, 0, 300, 30);

        GridBagCellConstraints gbc = new GridBagCellConstraints().fillBoth().weight(1).anchorNorth();
        add(layeredPane, gbc);

        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = layeredPane.getSize();
                textControlPane.setBounds(0, 0, size.width, size.height);
                toolBar.setBounds(0, 0, size.width, 30);
                revalidate();
            }
        });

        textControlPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
        });

        toolBar.setVisible(false);

        textControlPane.addListener(new TextControlPaneListener() {

            @Override
            public void editingCancelled() {
                setEditEnabled(false);
            }

            @Override
            public void editingCompleted() {
                setEditEnabled(false);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
        });

        model.addChangeListener(this);
    }

    private void handleMouseClick(MouseEvent e) {
        System.out.println("Mouse Clicked");
        if (previewMode) {
            return;
        }

        if (e.getClickCount() == 1) {
            setEditEnabled(false);
        }

        if (e.getClickCount() == 2) {
            System.out.println("Seting editor enabled");
            setEditEnabled(true);
        }
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

    public void setEditEnabled(boolean isEnabled) {
        textControlPane.setEditorEnabled(isEnabled);
        toolBar.setVisible(isEnabled);

        if (isEnabled) {
            model.textEditSelected();
        }
    }

    @Override
    public void requestFocus() {
        textControlPane.requestFocus();
    }

    @Override
    public void textEditCancelled(TextControlModel model) {
        setEditEnabled(false);
    }

    public void setPhysicalToViewScalingFactor(double physicalToViewScalingFactor) {
        textControlPane.setFontScalingFactor(physicalToViewScalingFactor, model);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(Color.yellow);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setPreferredSize(new Dimension(600, 400));
        frame.setSize(frame.getPreferredSize());

        TextControlModel model = new TextControlModel();
        model.setText("Hello There!");
        model.setFontColor(Color.MAGENTA);
        model.setBold(true);

        TextControl ctrl = new TextControl(model);

        ctrl.setPreferredSize(new Dimension(500, 300));
        ctrl.setSize(ctrl.getPreferredSize());

        frame.add(ctrl);
        frame.setVisible(true);
    }

}
