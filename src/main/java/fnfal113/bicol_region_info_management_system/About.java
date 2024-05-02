package main.java.fnfal113.bicol_region_info_management_system;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.java.fnfal113.bicol_region_info_management_system.components.DrawableCard;
import main.java.fnfal113.bicol_region_info_management_system.handlers.ButtonHandler;

public class About {

    private final JFrame window;

    private final JPanel panel;

    public About(JFrame window) {
        this.window = window;
        this.panel = new JPanel(new BorderLayout());

        initializePanel();
    }

    public void initializePanel() {
        this.panel.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));

        DrawableCard card = new DrawableCard("DICT", "#6874E8", 300, -1, -250, 250);

        card.setBorder(BorderFactory.createEmptyBorder(16, 8, 16, 8));

        card.addLabel("<html><span style='font-size: 18px;'>About</span><br/><br/><br/>" +
            "<p style='line-height: 1.5;'>Developed by Jeff Hubert N. Orbeta (Intern) for DICT Region V.<br/>" +
            "Contributors are hereby recognized through their participation <br/>on improving this small project</p>" +
            "</html>", BorderLayout.NORTH
        );
        
        JButton sourceButton = new JButton("Github Source Code");

        sourceButton.addMouseListener(new ButtonHandler() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;

                if(desktop != null) {
                    try {
                        desktop.browse(URI.create("https://github.com/FN-FAL113/Bicol-Region-Info-Management-System"));
                    } catch (IOException ex) {
                        ex.printStackTrace();

                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Info", 0);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No browsers detected! Please install a web browser to use this functionality", "Info", 0);
                }
            }
        });

        card.add(sourceButton, BorderLayout.SOUTH);

        this.panel.add(card);

    }

    public JFrame getWindow() {
        return this.window;
    }

    public JPanel getPanel() {
        return this.panel;
    }

}
