package main.java.fnfal113.bicolregioninfomanagementsystem;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import main.java.fnfal113.bicolregioninfomanagementsystem.handlers.ButtonHandler;
import main.java.fnfal113.bicolregioninfomanagementsystem.utils.ButtonFactory;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;

public class SideBar {

    private JFrame window;
    private JPanel panel;
    
    public SideBar(JFrame window) {
        this.window = window;

        createPanel();
    }

    private void createPanel() {
        // side bar panel
        this.panel = new JPanel(new BorderLayout());

        this.panel.setPreferredSize(new Dimension(150, this.window.getHeight()));
        this.panel.setBackground(Color.GRAY);

        // buttons panel
        JPanel btnPanel = new JPanel(new GridBagLayout());

        btnPanel.setBackground(Color.GRAY);   

        MatteBorder matteBorder = new MatteBorder(0, 2, 0, 0, Color.black);
        Border emptyBorder = BorderFactory.createEmptyBorder(5,5,5,5);

        JButton dashBoardBtn = ButtonFactory.createButton("Dashboard");
        dashBoardBtn.setBackground(null);
        dashBoardBtn.setBorder(BorderFactory.createCompoundBorder(matteBorder, emptyBorder));

        JButton projectsBtn = ButtonFactory.createButton("Manage Data");
        projectsBtn.setBackground(null);
        projectsBtn.setBorder(BorderFactory.createCompoundBorder(matteBorder, emptyBorder));
        
        JButton aboutBtn = ButtonFactory.createButton("About");
        aboutBtn.setBackground(null);
        aboutBtn.setBorder(BorderFactory.createCompoundBorder(matteBorder, emptyBorder));

        dashBoardBtn.addMouseListener(createButtonClickHandler(dashBoardBtn, "dashboard"));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 0, 10, 0);

        gbc.anchor = GridBagConstraints.WEST;

        btnPanel.add(dashBoardBtn, gbc);
        
        gbc.gridy = 1;
        btnPanel.add(projectsBtn, gbc);
        
        gbc.gridy = 2;
        btnPanel.add(aboutBtn, gbc);

        this.panel.add(btnPanel, BorderLayout.CENTER);
    }

    private ButtonHandler createButtonClickHandler(JButton button, String panelName) {
        return new ButtonHandler() {
            @Override
            public void mouseClicked(MouseEvent e) {                
                button.setBorder(
                    BorderFactory.createCompoundBorder(new MatteBorder(0, 4, 0, 0, Color.black), BorderFactory.createEmptyBorder(5,5,5,5))
                );
                
                if(panelName == "dashboard"){
                    App.getWindow().getDashboard().getPanel().setVisible(true);
                } else if(panelName == "manage data") {
                    App.getWindow().getDashboard().getPanel().setVisible(true);
                }
            }
        };
    }

    public JPanel getPanel() {
        return this.panel;
    }
}
