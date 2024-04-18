package main.java.fnfal113.bicol_region_info_management_system;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.fnfal113.bicol_region_info_management_system.components.Form;

public class ManageData {
    
    private JFrame window;
    
    private JPanel panel;

    private Map<String, JTextField> tableFieldsMap = new HashMap<>();

    public ManageData(JFrame window) {
        this.window = window;
        this.panel = new JPanel(new GridLayout(1, 0));

        initializePanel();
    }

    private void initializePanel() {
        this.panel.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));

        this.panel.add(createForms());
    }

    private JPanel createForms() {
        JPanel formsPanel = new JPanel(new GridLayout(3, 1));
        
        for (String tableName : Dashboard.tableNames) {
            formsPanel.add(new Form(tableName, tableName));
        }

        return formsPanel;
    }

    public JFrame getWindow() {
        return window;
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public Map<String, JTextField> getTableFieldsMap() {
        return tableFieldsMap;
    }

}
