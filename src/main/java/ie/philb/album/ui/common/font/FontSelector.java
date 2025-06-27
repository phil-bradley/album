/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.ui.font;

import ie.philb.album.ui.common.font.ApplicationFont;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
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
public class FontSelector extends JComboBox<ApplicationFont> {

    private Collection<ApplicationFont> fonts = new ArrayList<>();

    public FontSelector(Collection<ApplicationFont> fonts) {
        super();
        this.fonts.addAll(fonts);
        setModel(createModel());
        setRenderer(new FontSelectorCellRenderer());
    }

    private ComboBoxModel<ApplicationFont> createModel() {
        DefaultComboBoxModel<ApplicationFont> model = new DefaultComboBoxModel<>();

        for (ApplicationFont font : fonts) {
            model.addElement(font);
        }

        return model;
    }

    public ApplicationFont getSelectedFont() {
        ApplicationFont selected = (ApplicationFont) getSelectedItem();
        return selected;
    }

    class FontSelectorCellRenderer extends JLabel implements ListCellRenderer<ApplicationFont> {

        // private final PageView pageView = new PageView(new PageModel(PageGeometry.square(1), PageSize.A4_Landscape));
        @Override
        public Component getListCellRendererComponent(JList<? extends ApplicationFont> list, ApplicationFont value, int index, boolean isSelected, boolean cellHasFocus) {

            setText(value.name());
            setFont(value.getFont());

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
