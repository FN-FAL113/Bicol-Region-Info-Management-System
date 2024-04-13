package main.java.fnfal113.bicol_region_info_management_system;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.fnfal113.bicol_region_info_management_system.handlers.ButtonHandler;
import main.java.fnfal113.bicol_region_info_management_system.utils.ButtonFactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;

public class SideBar {

    private JFrame window;
    private JPanel panel;
    
    public SideBar(JFrame window) {
        this.window = window;

        createPanel();
    }

    public void createPanel() {
        // side bar panel
        this.panel = new JPanel(new BorderLayout());

        this.panel.setPreferredSize(new Dimension(200, getWindow().getHeight()));

        this.panel.setBackground(Color.decode("#31337E")); 

        this.panel.setBorder(BorderFactory.createEmptyBorder(16, 0, 16, 0));

        // buttons panel
        JPanel buttonsPanel = new JPanel(new GridBagLayout());

        buttonsPanel.setBackground(null);

        JButton dashBoardBtn = ButtonFactory.createButton("Dashboard", Color.WHITE, null);
        
        dashBoardBtn.setBackground(null);

        dashBoardBtn.setBorder(null);

        attachButtonIcon(dashBoardBtn, "dashboard");

        dashBoardBtn.addMouseListener(createButtonClickHandler(dashBoardBtn, "dashboard"));

        JButton manageDataBtn = ButtonFactory.createButton("Manage Data", Color.WHITE, null);
        
        manageDataBtn.setBackground(null);

        manageDataBtn.setBorder(null);

        attachButtonIcon(manageDataBtn, "manage-data");

        manageDataBtn.addMouseListener(createButtonClickHandler(dashBoardBtn, "manage data"));
        
        JButton aboutBtn = ButtonFactory.createButton("About", Color.WHITE, null);
        
        aboutBtn.setBackground(null);

        aboutBtn.setBorder(null);

        attachButtonIcon(aboutBtn, "about");

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(6, 0, 0, 0);
        gbc.anchor = GridBagConstraints.WEST;

        buttonsPanel.add(dashBoardBtn, gbc);
        
        gbc.insets = new Insets(24, 0, 0, 0);
        gbc.gridy = 1;
        buttonsPanel.add(manageDataBtn, gbc);

        gbc.insets = new Insets(24, 0, 0, 0);
        gbc.gridy = 2;

        buttonsPanel.add(aboutBtn, gbc);

        this.panel.add(buttonsPanel, BorderLayout.NORTH);
    }

    private void attachButtonIcon(JButton button, String fileName) {
        ImageIcon icon = new ImageIcon(
            new ImageIcon(App.class.getResource("assets/" + fileName + ".png")).getImage().getScaledInstance(28, -1, Image.SCALE_SMOOTH)
        );

        button.setIcon(icon);

        button.setIconTextGap(8);
    }

    private ButtonHandler createButtonClickHandler(JButton button, String panelName) {
        return new ButtonHandler() {
            @Override
            public void mouseClicked(MouseEvent e) {    
                if(panelName == "dashboard") {
                    App.mainWindow().getJsp().setViewportView( App.mainWindow().getDashboard().getPanel());
                } else if(panelName == "manage data") {
                    App.mainWindow().getJsp().setViewportView(App.mainWindow().getManageData().getPanel());
                }
            }
        };
    }

    public JFrame getWindow() {
        return window;
    }

    public JPanel getPanel() {
        return this.panel;
    }
}
