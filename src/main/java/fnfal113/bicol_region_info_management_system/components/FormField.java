package main.java.fnfal113.bicol_region_info_management_system.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.fnfal113.bicol_region_info_management_system.utils.StringUtils;

public class FormField {
    
    private JPanel panel;
    private JTextField textField;
    private String fieldName;

    public FormField(String fieldName) {
        this.panel = new JPanel(new BorderLayout());
        this.textField = new JTextField();
        this.fieldName = fieldName;

        create();
    }

    private JPanel create() {     
        this.panel.setBackground(null);  
        this.panel.setOpaque(false);

        JLabel label = new JLabel(StringUtils.toPascalCase(this.fieldName));

        label.setBorder(BorderFactory.createEmptyBorder(0, 2, 2, 0));
    
        label.setFont(new Font("Inter Bold", Font.BOLD, 12));

        label.setForeground(Color.WHITE);
        label.setBackground(null);
        label.setOpaque(false);

        this.textField.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE), 
                BorderFactory.createEmptyBorder(4, 4, 4, 4)
            )
        );

        this.textField.setPreferredSize(new Dimension(160, 30));

        this.textField.setForeground(Color.WHITE);
        this.textField.setBackground(null);
        this.textField.setOpaque(false);

        this.panel.add(label, BorderLayout.NORTH);
        this.panel.add(textField, BorderLayout.SOUTH);

        return this.panel;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTextField getTextField() {
        return textField;
    }

}
