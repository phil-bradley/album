/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.common.textcontrol;

import com.bric.colorpicker.ColorPickerDialog;
import ie.philb.album.ui.font.FontProvider;
import ie.philb.album.ui.font.FontSelector;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
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
class TextControlEditorToolBar extends JToolBar {

    private FontProvider fontProvider = new FontProvider();
    private JComboBox<Integer> fontSizeSelector;
    private JButton btnColor;
    private FontSelector fontSelector;
    private JToggleButton btnBold;
    private JToggleButton btnItalic;
    private JToggleButton btnUnderline;

    public TextControlEditorToolBar() {
        //setFloatable(false);

        btnBold = new JToggleButton("B");
        btnItalic = new JToggleButton("I");
        btnUnderline = new JToggleButton("U");
        btnColor = new JButton("C");

        fontSelector = new FontSelector(fontProvider.getFonts());
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
            updateFont();
        });

        btnColor.addActionListener((ActionEvent ae) -> {
            ColorPickerDialog dlg = new ColorPickerDialog();
            dlg.setVisible(true);

            Color selectedColor = dlg.getColor();
            if (selectedColor != null) {
                fontColor = selectedColor;
                updateFont();
            }
        });

    }

    protected void updateFontControls(TextContent content) {
        btnBold.setSelected(content.isBold());
        btnItalic.setSelected(content.isItalic());
        btnUnderline.setSelected(content.isUnderline());
        fontSizeSelector.setSelectedItem(content.fontSize());
    }

    private ComboBoxModel<Integer> getFontSizeSelectorModel() {
        List<Integer> sizes = List.of(6, 7, 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 36, 40, 44, 48, 54, 60, 66, 72, 80, 88, 104, 120, 140, 160, 180, 200);
        DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
        model.addAll(sizes);
        return model;
    }

}
