/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.album.view;

import ie.philb.album.AppContext;
import ie.philb.album.ui.common.AppPanel;
import java.awt.CardLayout;

/**
 *
 * @author philb
 */
public class WelcomeWithAlbumViewCardPanel extends AppPanel {

    private enum Card {
        Welcome,
        Album
    };

    private final CardLayout cardLayout = new CardLayout();
    private WelcomePanel welcomePanel;
    private AlbumContainerWithOverviewPanel albumContainerWithOverviewPanel;

    public WelcomeWithAlbumViewCardPanel() {
        initComponents();
    }

    private void initComponents() {
        welcomePanel = new WelcomePanel();
        albumContainerWithOverviewPanel = new AlbumContainerWithOverviewPanel();
        layoutComponents();
    }

    private void layoutComponents() {
        setLayout(cardLayout);
        add(Card.Welcome.name(), welcomePanel);
        add(Card.Album.name(), albumContainerWithOverviewPanel);
        showPanel(Card.Welcome);
    }

    private void showPanel(Card card) {
        cardLayout.show(this, card.name());
    }

    @Override
    public void albumUpdated() {
        showPanel(Card.Album);
    }
}
