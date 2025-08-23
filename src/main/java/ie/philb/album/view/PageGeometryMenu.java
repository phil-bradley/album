/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.model.PageGeometry;
import ie.philb.album.model.PageGeometryOption;
import ie.philb.album.ui.command.SetGeometryCommand;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

/**
 *
 * @author philb
 */
public class PageGeometryMenu extends JPopupMenu {

    private PageGeometry selectedGeometry = null;
    private List<JButton> buttons = new ArrayList<>();

    public PageGeometryMenu() {

        JPanel pageLayoutItemPanel = new JPanel();
        pageLayoutItemPanel.setLayout(new BoxLayout(pageLayoutItemPanel, BoxLayout.Y_AXIS));

        for (PageGeometryOption geometryOption : PageGeometryOption.values()) {
            JButton btn = new JButton(geometryOption.icon());
            btn.setToolTipText(geometryOption.description());
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

            btn.addActionListener((ActionEvent ae) -> {
                new SetGeometryCommand(geometryOption.geometry()).execute();
                setSelectedGeometry(geometryOption.geometry());
                setVisible(false);
            });

            buttons.add(btn);
            pageLayoutItemPanel.add(btn);
        }

        JScrollPane pageLayoutScrollPane = new JScrollPane(pageLayoutItemPanel);
        pageLayoutScrollPane.setBorder(null);
        pageLayoutScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pageLayoutScrollPane.setPreferredSize(new Dimension(100, 400));

        add(pageLayoutScrollPane);
    }

    public void setSelectedGeometry(PageGeometry geometry) {
        this.selectedGeometry = geometry;

        int i = 0;
        for (PageGeometryOption geometryOption : PageGeometryOption.values()) {

            JButton btn = buttons.get(i);

            if (geometryOption.geometry().equals(selectedGeometry)) {
                btn.setBorder(BorderFactory.createLineBorder(UIManager.getColor("List.selectionBackground"), 2));
            } else {
                btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
            }

            i++;
        }
    }
}
