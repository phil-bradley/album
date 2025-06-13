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
    private final JPanel toolboxPlaceholder = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private FontProvider fontProvider = new FontProvider();
    private JToolBar toolBar;
    private JToggleButton btnBold;
    private JToggleButton btnItalic;
    private JToggleButton btnUnderline;
    private JComboBox<Integer> fontSizeSelector;
    private DisplayView displayView;
    private EditView editView;
    private ViewEditPanel viewEditPanel;
    private FontSelector fontSelector;

    public TextControl() {

        transparent();
        gridbag();

        fontSelector = new FontSelector(fontProvider.getFonts());
        fontSizeSelector = new JComboBox<>();
        fontSizeSelector.setModel(getFontSizeSelectorModel());

        btnBold = new JToggleButton("B");
        btnItalic = new JToggleButton("I");
        btnUnderline = new JToggleButton("U");

        displayView = new DisplayView();
        editView = new EditView();
        viewEditPanel = new ViewEditPanel();

        GridBagCellConstraints gbc = new GridBagCellConstraints().fillHorizontal().weightx(1).anchorNorth();
        toolboxPlaceholder.setPreferredSize(new Dimension(1, 32));
        toolboxPlaceholder.setMinimumSize(toolboxPlaceholder.getPreferredSize());
        toolboxPlaceholder.setSize(toolboxPlaceholder.getPreferredSize());

        add(toolBar, gbc);
        add(toolboxPlaceholder, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        add(viewEditPanel, gbc);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked");

                if (e.getClickCount() == 2) {
                    setToolbarVisible(true);
                    cardLayout.show(viewEditPanel, "edit");
                    editView.field.requestFocus();
                }
            }
        });

        editView.field.addActionListener((ActionEvent e) -> {
            setToolbarVisible(false);
            displayView.label.setText(editView.field.getText());
            cardLayout.show(viewEditPanel, "display");
        });

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

        setBold(true);
        setItalic(true);
        setFontSize(24);

        toolBar.setVisible(false);
        toolboxPlaceholder.setVisible(true);
        toolboxPlaceholder.setOpaque(false);
    }

    private ComboBoxModel<Integer> getFontSizeSelectorModel() {
        List<Integer> sizes = List.of(6, 7, 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 36, 40, 44, 48, 54, 60, 66, 72, 80, 88, 104, 120, 140, 160, 180, 200);
        DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
        model.addAll(sizes);
        return model;
    }

    private void setToolbarVisible(boolean isVisible) {

        if (!isVisible) {
            toolboxPlaceholder.setPreferredSize(toolBar.getSize());
            toolboxPlaceholder.setMinimumSize(toolBar.getSize());
            toolboxPlaceholder.setSize(toolBar.getSize());
        }

        toolboxPlaceholder.setVisible(!isVisible);
        toolBar.setVisible(isVisible);
    }

    private class ViewEditPanel extends JPanel {

        public ViewEditPanel() {
            setOpaque(false);
            setBorder(BorderFactory.createDashedBorder(Color.LIGHT_GRAY));
            setLayout(cardLayout);
            add(editView, "edit");
            add(displayView, "display");

            cardLayout.show(this, "display");
        }

    }

    private void updateFontControls() {
        btnBold.setSelected(isBold);
        btnItalic.setSelected(isItalic);
        btnUnderline.setSelected(isUnderline);
        fontSizeSelector.setSelectedItem((int) fontSize);
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

            fontSizeSelector.setPreferredSize(new Dimension(20, 20));
            fontSizeSelector.setSize(fontSizeSelector.getPreferredSize());

            toolBar = new JToolBar();
            toolBar.setMinimumSize(new Dimension(50, 50));
            toolBar.setFloatable(false);
            toolBar.add(btnBold);
            toolBar.add(btnItalic);
            toolBar.add(btnUnderline);
            toolBar.add(fontSizeSelector);
            toolBar.add(fontSelector);
            toolBar.add(new JPanel());

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

    }

    private void updateFont() {
        displayView.label.setFont(getDisplayFont());
        editView.field.setFont(getDisplayFont());
    }

    private Font getDisplayFont() {

        return fontProvider.getDerivedFont(baseFont, isBold, isItalic, isUnderline, fontSize);
//        int fontStyle = Font.PLAIN;
        //        if (isBold) {
        //            fontStyle = fontStyle | Font.BOLD;
        //        }
        //
        //        if (isItalic) {
        //            fontStyle = fontStyle | Font.ITALIC;
        //        }
        //
        //        Font baseFont = new Font("Serif", fontStyle, (int) fontSize);
        //
        //        try {
        //            Font ttf = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/ie/philb/album/fonts/GreatVibes.ttf"));
        //            baseFont = ttf.deriveFont(fontSize).deriveFont(fontStyle);
        //        } catch (FontFormatException | IOException ex) {
        //        }
        //
        //        if (isUnderline) {
        //            Map<TextAttribute, Integer> attrs = new HashMap<>();
        //            attrs.put(TextAttribute.UNDERLINE, UNDERLINE_ON);
        //            return baseFont.deriveFont(attrs);
        //        }
        //
        //        return baseFont;
    }

    public boolean isBold() {
        return isBold;
    }

    public final void setBold(boolean isBold) {
        this.isBold = isBold;
        updateFontControls();
    }

    public boolean isItalic() {
        return isItalic;
    }

    public final void setItalic(boolean isItalic) {
        this.isItalic = isItalic;
        updateFontControls();

    }

    public boolean isUnderline() {
        return isUnderline;
    }

    public void setUnderline(boolean isUnderline) {
        this.isUnderline = isUnderline;
        updateFontControls();
    }

    public int getFontSize() {
        return fontSize;
    }

    public final void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        updateFontControls();
        updateFont();
    }

//    public String getFontName() {
//        return font.getFontName();
//    }
//
//    public void setFontName(String fontName) {
//        this.fontName = fontName;
//        updateFontControls();
//        updateFont();
//    }
    public static void main(String[] args) {

        FontProvider fontProvider = new FontProvider();
        System.out.println("Got fonts " + fontProvider.getFonts());

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
