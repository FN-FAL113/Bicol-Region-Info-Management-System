package main.java.fnfal113.bicol_region_info_management_system.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

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
        this.panel = new JPanel();
        this.textField = new JTextField();
        this.fieldName = fieldName;

        create();
    }

    private JPanel create() {
        this.panel.setLayout(new BorderLayout());
        
        this.panel.setBackground(null);
        
        JLabel label = new JLabel(StringUtils.toPascalCase(this.fieldName));

        label.setBorder(BorderFactory.createEmptyBorder(0, 2, 2, 0));

        this.textField.setBackground(null);

        this.textField.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK), 
                BorderFactory.createEmptyBorder(4, 4, 4, 4)
            )
        );

        this.textField.setPreferredSize(new Dimension(160, 30));

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
