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
class TextControlEditorToolBar extends JToolBar implements TextControlChangeListener {

    private final List<Integer> fontSizes = List.of(6, 7, 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 28, 32, 36, 40, 44, 48, 54, 60, 66, 72, 80, 88, 104, 120, 140, 160, 180);

    private TextControlModel model;
    private JComboBox<Integer> fontSizeSelector;
    private final JButton btnColor;
    private FontSelector fontSelector;
    private final JToggleButton btnBold;
    private final JToggleButton btnItalic;
    private final JToggleButton btnUnderline;

    public TextControlEditorToolBar(TextControlModel model) {

        this.model = model;

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
        fontSelector.setFont(fonts.get(0).getFont(btnBold.isSelected(), btnItalic.isSelected()));

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
            boolean isBold = (e.getStateChange() == ItemEvent.SELECTED);
            if (model.isBold() != isBold) {
                model.setBold(isBold);
            }
        });

        btnItalic.addItemListener((ItemEvent e) -> {
            boolean isItalic = (e.getStateChange() == ItemEvent.SELECTED);
            if (model.isItalic() != isItalic) {
                model.setItalic(isItalic);
            }
        });

        btnUnderline.addItemListener((ItemEvent e) -> {
            boolean isUnderline = (e.getStateChange() == ItemEvent.SELECTED);
            if (model.isUnderline() != isUnderline) {
                model.setUnderline(isUnderline);
            }
        });

        fontSizeSelector.addActionListener((ActionEvent ae) -> {
            int selectedSize = (Integer) fontSizeSelector.getSelectedItem();
            if (model.getFontSize() != selectedSize) {
                model.setFontSize(selectedSize);
            }
        });

        fontSelector.addActionListener((ActionEvent ae) -> {
            if (!fontSelector.getSelectedFont().name().equals(model.getFontFamily())) {
                model.setFontFamily(fontSelector.getSelectedFont().name());
                model.setBold(false);
                model.setItalic(false);
                updateFontControls();
                fontSelector.setFont(fontSelector.getSelectedFont().getFont(false, false));
            }

        });

        btnColor.addActionListener((ActionEvent ae) -> {
            ColorPickerDialog dlg = new ColorPickerDialog();
            dlg.setVisible(true);

            Color selectedColor = dlg.getColor();

            if (selectedColor != null) {
                model.setFontColor(selectedColor);
            }
        });

        updateFontControls();
        model.addChangeListener(this);
    }

    private void updateFontControls() {
        btnBold.setSelected(model.isBold());
        btnItalic.setSelected(model.isItalic());
        btnUnderline.setSelected(model.isUnderline());

        fontSizeSelector.setSelectedItem(model.getFontSize());

        ApplicationFont applicationFont = ApplicationFont.byFamilyName(model.getFontFamily());
        fontSelector.setSelectedItem(applicationFont);
        fontSelector.setFont(applicationFont.getFont(model.isBold(), model.isItalic()));

        this.fontSelector.setFont(ApplicationFont.byFamilyName(model.getFontFamily()).getFont(model.isBold(), model.isItalic()));
        this.btnColor.setForeground(model.getFontColor());

        if (applicationFont.hasBold()) {
            btnBold.setEnabled(true);
            btnBold.setToolTipText("Bold");
        } else {
            btnBold.setEnabled(false);
            btnBold.setToolTipText("Bold font not available");
        }

        if (applicationFont.hasItalic()) {
            btnItalic.setEnabled(true);
            btnItalic.setToolTipText("Italic");
        } else {
            btnItalic.setEnabled(false);
            btnItalic.setToolTipText("Italic font not available");
        }
    }

    private ComboBoxModel<Integer> getFontSizeSelectorModel() {
        DefaultComboBoxModel<Integer> fontSizeModel = new DefaultComboBoxModel<>();
        fontSizeModel.addAll(fontSizes);
        return fontSizeModel;
    }

    @Override
    public void formatUpdated(TextControlModel model) {
        updateFontControls();
    }
}
