/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageGeometryOption;
import java.awt.Component;
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
public class PageGeometrySelector extends JComboBox<PageGeometryOption> {

    public PageGeometrySelector() {
        super();
        setModel(createModel());
        setRenderer(new PageGeometrySelectorCellRenderer());
    }

    private ComboBoxModel<PageGeometryOption> createModel() {
        DefaultComboBoxModel<PageGeometryOption> model = new DefaultComboBoxModel<>();
        for (PageGeometryOption pgo : PageGeometryOption.values()) {
            model.addElement(pgo);
        }

        return model;
    }

    public PageGeometryOption getSelectedGeometryOption() {
        PageGeometryOption selected = (PageGeometryOption) getSelectedItem();
        return selected;
    }

    public void setSelectedGeometry(PageGeometry geometry) {
        ComboBoxModel<PageGeometryOption> model = getModel();

        for (int i = 0; i < model.getSize(); i++) {
            PageGeometryOption option = model.getElementAt(i);

            if (option.geometry().equals(geometry)) {
                setSelectedIndex(i);
            }
        }
    }

    class PageGeometrySelectorCellRenderer extends JLabel implements ListCellRenderer<PageGeometryOption> {

        // private final PageView pageView = new PageView(new PageModel(PageGeometry.square(1), PageSize.A4_Landscape));
        @Override
        public Component getListCellRendererComponent(JList<? extends PageGeometryOption> list, PageGeometryOption value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(value.description());

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
