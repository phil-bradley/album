/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import ie.philb.album.ui.common.GridBagCellConstraints;
import ie.philb.album.ui.common.font.Fonts;
import ie.philb.album.ui.font.FontProvider;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author philb
 */
public class TextControl extends JPanel {

    private final List<TextControlEventListener> listeners = new ArrayList<>();
    private TextContent content = new TextContent();

    private FontProvider fontProvider = new FontProvider();
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
//                updateFont();
            }
        });

        viewEditPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked");

                if (e.getClickCount() == 2) {
                    viewEditPanel.setEditor(true);
                    toolBar.setVisible(true);
                }
            }
        });

//        viewEditPanel.addActionListener((ActionEvent e) -> {
//            toolBar.setVisible(false);
//        });

//        setBold(true);
//        setItalic(true);
//        setFontSize(24);
        toolBar.setVisible(false);
    }

    public void setTextContent(TextContent content) {
        TextControlEventBus.INSTANCE.updated(content);
    }
//    @Override
//    public void updateContent(TextContent content) {
//        this.toolBar.setTextContent(content);
//        this.viewEditPanel.setTextContent(content);
//    }
//
//    @Override
//    public void updateContent(TextContent content) {
//        this.content = content;
//        updateFontControls();
//    }
//
//    @Override
//    public Collection<TextControlEventListener> getTextContentListeners() {
//        return listeners;
//    }
//
//    @Override
//    public TextContent getTextContent() {
//        return content;
//    }

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

//    private void setViewFont(Font font, Color fontColor) {
//        viewEditPanel.setViewFont(font, fontColor);
//    }
//
//    private void updateFont() {
//        setViewFont(getDisplayFont(), fontColor);
//    }
//
//    private Font getDisplayFont() {
//        int scaledFontSize = (int) (fontSize * getPhysicalToViewScalingFactor());
//        return fontProvider.getDerivedFont(baseFont, isBold, isItalic, isUnderline, scaledFontSize);
//    }
//
//    public boolean isBold() {
//        return isBold;
//    }
//
//    public final void setBold(boolean isBold) {
//        this.isBold = isBold;
//        toolBar.updateFontControls(getTextContent());
//    }
//
//    public boolean isItalic() {
//        return isItalic;
//    }
//
//    public final void setItalic(boolean isItalic) {
//        this.isItalic = isItalic;
//        toolBar.updateFontControls(getTextContent());
//    }
//
//    public boolean isUnderline() {
//        return isUnderline;
//    }
//
//    public void setUnderline(boolean isUnderline) {
//        this.isUnderline = isUnderline;
//        toolBar.updateFontControls(getTextContent());
//    }
//
//    public int getFontSize() {
//        return fontSize;
//    }
//
//    public final void setFontSize(int fontSize) {
//        this.fontSize = fontSize;
//        toolBar.updateFontControls(getTextContent());
//        updateFont();
//    }
//
//    public void setText(String text) {
//        this.viewEditPanel.setText(text);
//    }
//
//    public String getText() {
//        return viewEditPanel.getText();
//    }
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(Color.yellow);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setPreferredSize(new Dimension(400, 250));
        frame.setSize(frame.getPreferredSize());

        TextControl ctrl = new TextControl();
        ctrl.setTextContent(new TextContent("Hello there", true, true, true, Fonts.Caveat.name(), 12, Color.PINK));
        ctrl.setPreferredSize(new Dimension(300, 200));
        ctrl.setSize(ctrl.getPreferredSize());

        frame.add(ctrl);
        frame.setVisible(true);
    }
}
