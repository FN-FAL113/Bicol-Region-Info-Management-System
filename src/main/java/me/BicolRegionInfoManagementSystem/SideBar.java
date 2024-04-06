package main.java.me.simpleapp;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;

import main.java.me.simpleapp.handlers.ButtonHandler;
import main.java.me.simpleapp.utils.ButtonFactory;

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

        JButton dashBoardBtn = ButtonFactory.createButton("Dashboard");
        dashBoardBtn.setBackground(null);
        dashBoardBtn.setBorder(BorderFactory.createCompoundBorder(matteBorder, BorderFactory.createEmptyBorder(5,5,5,5)));

        JButton projectsBtn = ButtonFactory.createButton("Manage Data");
        projectsBtn.setBackground(null);
        projectsBtn.setBorder(BorderFactory.createCompoundBorder(matteBorder, BorderFactory.createEmptyBorder(5,5,5,5)));
        
        JButton aboutBtn = ButtonFactory.createButton("About");
        aboutBtn.setBackground(null);
        aboutBtn.setBorder(BorderFactory.createCompoundBorder(matteBorder, BorderFactory.createEmptyBorder(5,5,5,5)));

        dashBoardBtn.addMouseListener(new ButtonHandler() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(dashBoardBtn, "button clicked", "Info", 1);
            }
        });

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

    public JPanel getPanel() {
        return this.panel;
    }
}
