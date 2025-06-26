/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common;

import ie.philb.album.ui.font.FontProvider;
import ie.philb.album.ui.font.FontSelector;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author philb
 */
public class TextControl extends AppPanel {

    private boolean isBold = false;
    private boolean isItalic = false;
    private boolean isUnderline = false;
    private int fontSize = 10;
    private Font baseFont = new Font("Serif", Font.PLAIN, (int) fontSize);
    private final CardLayout cardLayout = new CardLayout();
    private EditToolBar toolBar = new EditToolBar();

    private ViewEditPanel viewEditPanel;
    private FontProvider fontProvider = new FontProvider();
    private JLayeredPane layeredPane = new JLayeredPane();

    public TextControl() {

        transparent();
        viewEditPanel = new ViewEditPanel();

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
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked");

                if (e.getClickCount() == 2) {
                    cardLayout.show(viewEditPanel, "edit");
                    viewEditPanel.editView.field.requestFocus();
                    toolBar.setVisible(true);
                }
            }
        });

        viewEditPanel.editView.field.addActionListener((ActionEvent e) -> {
            viewEditPanel.displayView.label.setText(viewEditPanel.editView.field.getText());
            cardLayout.show(viewEditPanel, "display");
            toolBar.setVisible(false);
        });

        setBold(true);
        setItalic(true);
        setFontSize(24);

        toolBar.setVisible(false);
    }

    private ComboBoxModel<Integer> getFontSizeSelectorModel() {
        List<Integer> sizes = List.of(6, 7, 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 36, 40, 44, 48, 54, 60, 66, 72, 80, 88, 104, 120, 140, 160, 180, 200);
        DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
        model.addAll(sizes);
        return model;
    }

    private class EditToolBar extends JToolBar {

        private FontProvider fontProvider = new FontProvider();
        private JComboBox<Integer> fontSizeSelector;
        private FontSelector fontSelector;
        private JToggleButton btnBold;
        private JToggleButton btnItalic;
        private JToggleButton btnUnderline;

        public EditToolBar() {
            //setFloatable(false);

            btnBold = new JToggleButton("B");
            btnItalic = new JToggleButton("I");
            btnUnderline = new JToggleButton("U");

            fontSelector = new FontSelector(fontProvider.getFonts());
            fontSizeSelector = new JComboBox<>();
            fontSizeSelector.setModel(getFontSizeSelectorModel());

            fontSizeSelector.setPreferredSize(new Dimension(20, 20));
            fontSizeSelector.setSize(fontSizeSelector.getPreferredSize());

            add(btnBold);
            add(btnItalic);
            add(btnUnderline);
            add(fontSizeSelector);
            add(fontSelector);

            btnBold.addItemListener((ItemEvent e) -> {
                isBold = (e.getStateChange() == ItemEvent.SELECTED);
                updateFont();
            });

            btnItalic.addItemListener((ItemEvent e) -> {
                isItalic = (e.getStateChange() == ItemEvent.SELECTED);
                updateFont();
            });

            btnUnderline.addItemListener((ItemEvent e) -> {
                isUnderline = (e.getStateChange() == ItemEvent.SELECTED);
                updateFont();
            });

            fontSizeSelector.addActionListener((ActionEvent ae) -> {
                fontSize = (Integer) fontSizeSelector.getSelectedItem();
                updateFont();
            });

            fontSelector.addActionListener((ActionEvent ae) -> {
                baseFont = fontSelector.getSelectedFont();
                System.out.println("Base font is now " + baseFont);
                updateFont();
            });
        }

        private void updateFontControls() {
            btnBold.setSelected(isBold);
            btnItalic.setSelected(isItalic);
            btnUnderline.setSelected(isUnderline);
            fontSizeSelector.setSelectedItem((int) fontSize);
        }
    }

    private class ViewEditPanel extends JPanel {

        private DisplayView displayView;
        private EditView editView;

        public ViewEditPanel() {

            displayView = new DisplayView();
            editView = new EditView();

            setOpaque(false);
            setBorder(BorderFactory.createDashedBorder(Color.LIGHT_GRAY));
            setLayout(cardLayout);
            add(editView, "edit");
            add(displayView, "display");

            cardLayout.show(this, "display");
        }

        private String getText() {
            return displayView.getText();
        }

        private void setText(String text) {
            displayView.setText(text);
            editView.setText(text);
        }

    }

    class EditView extends JPanel {

        private JTextField field;

        public EditView() {
            setLayout(new GridBagLayout());
            initComponents();
            layoutComponents();
            setOpaque(false);
        }

        private void initComponents() {

            field = new JTextField();
            field.setHorizontalAlignment(JTextField.CENTER);
            field.setBorder(null);
            field.setOpaque(false);
            field.setFont(getDisplayFont());
            field.setMargin(new Insets(0, 0, 0, 0));
        }

        private void layoutComponents() {
            GridBagConstraints gbc = new GridBagCellConstraints().fillBoth().weight(1);
            add(field, gbc);
        }

        private void setText(String text) {
            field.setText(text);
        }
    }

    class DisplayView extends JPanel {

        private JLabel label;

        public DisplayView() {
            setLayout(new GridBagLayout());
            initComponents();
            layoutComponents();
            setOpaque(false);

        }

        private void initComponents() {
            label = new JLabel();
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            label.setOpaque(false);
            label.setFont(getDisplayFont());
            label.setBorder(null);
        }

        private void layoutComponents() {
            GridBagConstraints gbc = new GridBagCellConstraints().fillBoth().weight(1);
            add(label, gbc);
        }

        private void setText(String text) {
            label.setText(text);
        }

        private String getText() {
            return label.getText();
        }

    }

    private void updateFont() {
        viewEditPanel.displayView.label.setFont(getDisplayFont());
        viewEditPanel.editView.field.setFont(getDisplayFont());
    }

    private Font getDisplayFont() {
        return fontProvider.getDerivedFont(baseFont, isBold, isItalic, isUnderline, fontSize);
    }

    public boolean isBold() {
        return isBold;
    }

    public final void setBold(boolean isBold) {
        this.isBold = isBold;
        toolBar.updateFontControls();
    }

    public boolean isItalic() {
        return isItalic;
    }

    public final void setItalic(boolean isItalic) {
        this.isItalic = isItalic;
        toolBar.updateFontControls();

    }

    public boolean isUnderline() {
        return isUnderline;
    }

    public void setUnderline(boolean isUnderline) {
        this.isUnderline = isUnderline;
        toolBar.updateFontControls();
    }

    public int getFontSize() {
        return fontSize;
    }

    public final void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        toolBar.updateFontControls();
        updateFont();
    }

    public void setText(String text) {
        this.viewEditPanel.setText(text);
    }

    public String getText() {
        return viewEditPanel.getText();
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(Color.yellow);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setPreferredSize(new Dimension(400, 250));
        frame.setSize(frame.getPreferredSize());

        TextControl ctrl = new TextControl();
        ctrl.setPreferredSize(new Dimension(300, 200));
        ctrl.setSize(ctrl.getPreferredSize());

        frame.add(ctrl);
        frame.setVisible(true);
    }
}
