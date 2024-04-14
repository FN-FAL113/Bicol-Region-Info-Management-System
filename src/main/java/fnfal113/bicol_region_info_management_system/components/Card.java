package main.java.fnfal113.bicol_region_info_management_system.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import main.java.fnfal113.bicol_region_info_management_system.App;

public class Card {
    
    private JPanel panel;
    
    private String text;
    
    private String iconFileName;
    
    private String backgroundColor;
    
    private int width;

    private int height;
    
    public Card(String text, String iconFileName, String backgroundColor, int width, int height) {
        this.panel = new JPanel(new BorderLayout());
        this.text = text;
        this.iconFileName = iconFileName;
        this.backgroundColor = backgroundColor;
        this.width = width;
        this.height = height;

        init();
    }

    // make this extendable if needed
    public void init() {
        this.panel.setBackground(Color.decode(backgroundColor));

        this.panel.setPreferredSize(new Dimension(this.width, this.height));

        this.panel.putClientProperty(FlatClientProperties.STYLE, "arc: 10");

        JLabel label = new JLabel(this.text);

        ImageIcon icon = new ImageIcon(
            new ImageIcon(App.class.getResource("assets/" + iconFileName + ".png")).getImage().getScaledInstance(32, -1, Image.SCALE_SMOOTH)
        );

        label.setIcon(icon);

        label.setIconTextGap(8);

        label.setFont(new Font("Inter Bold", Font.BOLD, 16));

        label.setForeground(Color.WHITE);

        label.setHorizontalAlignment(JLabel.CENTER);

        label.setVerticalAlignment(JLabel.CENTER);

        label.setIconTextGap(8);
        
        this.panel.add(label, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return this.panel;
    }

}
