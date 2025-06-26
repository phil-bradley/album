/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.font;

import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author philb
 */
public class FontSelector extends JComboBox<Font> {

    private List<Font> fonts = new ArrayList<>();

    public FontSelector(List<Font> fonts) {
        super();
        this.fonts.addAll(fonts);
        setModel(createModel());
        setRenderer(new FontSelectorCellRenderer());
    }

    private ComboBoxModel<Font> createModel() {
        DefaultComboBoxModel<Font> model = new DefaultComboBoxModel<>();

        for (Font font : fonts) {
            model.addElement(font);
        }

        return model;
    }

    public Font getSelectedFont() {
        Font selected = (Font) getSelectedItem();
        return selected;
    }

    class FontSelectorCellRenderer extends JLabel implements ListCellRenderer<Font> {

        // private final PageView pageView = new PageView(new PageModel(PageGeometry.square(1), PageSize.A4_Landscape));
        @Override
        public Component getListCellRendererComponent(JList<? extends Font> list, Font value, int index, boolean isSelected, boolean cellHasFocus) {

            setText(value.getFamily());
            setFont(value);

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            return this;
        }
    }
}
