package main.java.fnfal113.bicol_region_info_management_system.utils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class ButtonFactory {
    
    public static JButton createButton(String label) {
        JButton btn = new JButton();
        
        btn.setText(label);
        btn.setFont(new Font("", Font.BOLD, 14));

        return btn;
    }

    public static JButton createButton(String label, Color bgColor) {
        JButton btn = new JButton();
        
        btn.setText(label);
        btn.setBackground(bgColor);
        btn.setFont(new Font("", Font.BOLD, 14));

        return btn;
    }
    
}
