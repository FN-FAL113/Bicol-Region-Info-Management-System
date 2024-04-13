package main.java.fnfal113.bicol_region_info_management_system.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import main.java.fnfal113.bicol_region_info_management_system.App;

public class Widget {
    
    private JPanel panel;
    private String text;
    private String iconFileName;
    private String backgroundColor;
    
    public Widget(String text, String iconFileName, String backgroundColor) {
        this.panel = new JPanel(new BorderLayout());
        this.text = text;
        this.iconFileName = iconFileName;
        this.backgroundColor = backgroundColor;

        create();
    }

    // make this extendable if needed
    public JPanel create() {
        this.panel.setBackground(Color.decode(backgroundColor));

        this.panel.setPreferredSize(new Dimension(160, 52));

        this.panel.putClientProperty(FlatClientProperties.STYLE, "arc: 10");

        JLabel label = new JLabel(this.text);

        label.setHorizontalAlignment(JLabel.CENTER);

        label.setVerticalAlignment(JLabel.CENTER);
        
        label.setFont(new Font("Inter Regular", Font.PLAIN, 14));
        
        ImageIcon icon = new ImageIcon(
            new ImageIcon(App.class.getResource("assets/" + iconFileName + ".png")).getImage().getScaledInstance(32, -1, Image.SCALE_SMOOTH)
        );

        label.setIcon(icon);

        label.setIconTextGap(8);
        
        this.panel.add(label, BorderLayout.CENTER);
        
        return this.panel;
    }

    public JPanel getPanel() {
        return this.panel;
    }

}
