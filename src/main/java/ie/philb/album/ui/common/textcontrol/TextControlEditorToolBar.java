/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import com.bric.colorpicker.ColorPickerDialog;
import ie.philb.album.ui.common.font.ApplicationFont;
import ie.philb.album.ui.font.FontSelector;
import static ie.philb.album.util.FontUtils.bold;
import static ie.philb.album.util.FontUtils.italic;
import static ie.philb.album.util.FontUtils.underline;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

/**
 *
 * @author philb
 */
class TextControlEditorToolBar extends JToolBar implements TextControlEventListener {

    private List<Integer> fontSizes = List.of(6, 7, 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 28, 32, 36, 40, 44, 48, 54, 60, 66, 72, 80, 88, 104, 120, 140, 160, 180);

    private TextContent content = new TextContent();
    private JComboBox<Integer> fontSizeSelector;
    private final JButton btnColor;
    private FontSelector fontSelector;
    private final JToggleButton btnBold;
    private final JToggleButton btnItalic;
    private final JToggleButton btnUnderline;

    public TextControlEditorToolBar() {

        btnBold = new JToggleButton("B");
        btnBold.setFont(bold(btnBold.getFont()));

        btnItalic = new JToggleButton("I");
        btnItalic.setFont(italic(btnItalic.getFont()));

        btnUnderline = new JToggleButton("U");
        btnUnderline.setFont(underline(btnUnderline.getFont()));

        btnColor = new JButton("A");
        btnColor.setFont(bold(btnColor.getFont()));
        btnColor.setFont(underline(btnColor.getFont()));

        List<ApplicationFont> fonts = Arrays.asList(ApplicationFont.values());
        fontSelector = new FontSelector(fonts);
        fontSelector.setFont(fonts.get(0).getFont());

        fontSizeSelector = new JComboBox<>();
        fontSizeSelector.setModel(getFontSizeSelectorModel());
        fontSizeSelector.setPreferredSize(new Dimension(20, 20));
        fontSizeSelector.setSize(fontSizeSelector.getPreferredSize());

        add(btnBold);
        add(btnItalic);
        add(btnUnderline);
        add(btnColor);
        add(fontSizeSelector);
        add(fontSelector);

        btnBold.addItemListener((ItemEvent e) -> {
            content.setBold(e.getStateChange() == ItemEvent.SELECTED);
            fireContentUpdated();
        });

        btnItalic.addItemListener((ItemEvent e) -> {
            content.setItalic(e.getStateChange() == ItemEvent.SELECTED);
            fireContentUpdated();
        });

        btnUnderline.addItemListener((ItemEvent e) -> {
            content.setUnderline(e.getStateChange() == ItemEvent.SELECTED);
            fireContentUpdated();
        });

        fontSizeSelector.addActionListener((ActionEvent ae) -> {
            content.setFontSize((Integer) fontSizeSelector.getSelectedItem());
            fireContentUpdated();
        });

        fontSelector.addActionListener((ActionEvent ae) -> {
            content.setFontFamily(fontSelector.getSelectedFont().name());
            fontSelector.setFont(fontSelector.getSelectedFont().getFont());
            fireContentUpdated();
        });

        btnColor.addActionListener((ActionEvent ae) -> {
            ColorPickerDialog dlg = new ColorPickerDialog();
            dlg.setVisible(true);

            Color selectedColor = dlg.getColor();
            if (selectedColor != null) {
                content.setFontColor(selectedColor);
                fireContentUpdated();
            }
        });

        TextControlEventBus.INSTANCE.addListener(this);
    }

    private void updateFontControls() {
        btnBold.setSelected(content.isBold());
        btnItalic.setSelected(content.isItalic());
        btnUnderline.setSelected(content.isUnderline());

//        int idx = fontSizes.indexOf(content.getFontSize());
        fontSizeSelector.setSelectedItem(content.getFontSize());

        ApplicationFont applicationFont = ApplicationFont.byFamilyName(content.getFontFamily());
        fontSelector.setSelectedItem(applicationFont);
        fontSelector.setFont(applicationFont.getFont());

    }

    private ComboBoxModel<Integer> getFontSizeSelectorModel() {
        DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
        model.addAll(fontSizes);
        return model;
    }

    @Override
    public void formatUpdated(TextContent content) {
        this.content = content;
        this.fontSelector.setFont(ApplicationFont.byFamilyName(content.getFontFamily()).getFont());
        this.btnColor.setForeground(content.getFontColor());
        updateFontControls();
    }

    @Override
    public void contentUpdated(TextContent content) {
    }

    private void fireContentUpdated() {
        TextControlEventBus.INSTANCE.formatUpdated(content);
    }
}
