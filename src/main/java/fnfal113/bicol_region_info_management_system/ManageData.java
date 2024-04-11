package main.java.fnfal113.bicol_region_info_management_system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.fnfal113.bicol_region_info_management_system.db.SQLRepository;
import main.java.fnfal113.bicol_region_info_management_system.handlers.ButtonHandler;

public class ManageData {
    
    private JFrame window;
    
    private JPanel panel;

    private Map<String, JTextField> formFields = new HashMap<>();

    private String[] tableNames = {"Provinces", "Municipalities", "Barangays"};


    public ManageData(JFrame window) {
        this.window = window;
        this.panel = new JPanel(new GridLayout(1, 0));

        initializePanel();
    }

    private void initializePanel() {
        this.panel.add(createAddForms());
    }

    private JPanel createAddForms() {
        JPanel addFormPanel = new JPanel(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1;
        gbc.insets = new Insets(4, 4, 4, 4);

        for (String tableName : this.tableNames) {
            JLabel formLabel = new JLabel();

            formLabel.setText("Add " + tableName);

            formLabel.setFont(new Font("Inter Bold", Font.BOLD, 16));

            formLabel.setBackground(Color.decode("#4979D1"));

            // center form header
            gbc.gridx = 1; 
            gbc.gridy = gbc.gridy + 1;

            addFormPanel.add(formLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = gbc.gridy + 1;

            try {
                ResultSet resultSet = new SQLRepository().getColumnNames(tableName.toLowerCase());

                // traverse table columns
                while (resultSet.next()) {
                    if(resultSet.getString(1).equals("id")) continue;

                    addFormPanel.add(createFormTextField(tableName, resultSet.getString(1)), gbc);

                    if(gbc.gridx == 2) { // limit to 3 text field per grid bag row
                        gbc.gridx = 0;
                        gbc.gridy = gbc.gridy + 1;
                    } else {
                        gbc.gridx = gbc.gridx + 1;
                    }
                }

                gbc.gridx = 1;
                gbc.gridy = gbc.gridy + 1;

                addFormPanel.add(createAddFormButton(tableName), gbc);

                gbc.gridx = 0;
                gbc.gridy = gbc.gridy + 1;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return addFormPanel;
    }

    private JPanel createFormTextField(String tableName, String fieldName) {
        JPanel fieldPanel = new JPanel(new BorderLayout());
        
        fieldPanel.setBackground(null);
        
        JLabel label = new JLabel();

        label.setText(fieldName);

        label.setBorder(BorderFactory.createEmptyBorder(4, 2, 4, 0));

        JTextField textField = new JTextField();

        textField.setBackground(null);

        textField.setBorder(
            BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK), BorderFactory.createEmptyBorder(4, 4, 4, 4))
        );

        textField.setPreferredSize(new Dimension(180, 30));

        fieldPanel.add(label, BorderLayout.NORTH);
        fieldPanel.add(textField, BorderLayout.SOUTH);

        this.formFields.put(tableName + "." + fieldName, textField);

        return fieldPanel;
    }

    public JButton createAddFormButton(String tableName) {
        JButton addFormButton = new JButton();

        addFormButton.setText("Add ");

        addFormButton.setFont(new Font(null, Font.BOLD, 12));

        addFormButton.setMargin(new Insets(6, 6, 6, 6));

        addFormButton.addMouseListener(new ButtonHandler() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    SQLRepository repository = new SQLRepository();

                    ResultSet resultSet = repository.getColumnNames(tableName.toLowerCase());
                    
                    ArrayList<String> queryBindings = new ArrayList<>();
                    ArrayList<Object> queryParameters = new ArrayList<>();

                    // traverse table columns
                    while (resultSet.next()) {
                        if(resultSet.getString(1).equals("id")) continue;

                        String columnName = resultSet.getString(1);
                        
                        queryBindings.add(columnName + " = ?") ;

                        queryParameters.add(formFields.get(tableName + "." + columnName).getText()); 
                    }

                    repository.addOrUpdate("INSERT INTO " + tableName + " SET " + String.join(",", queryBindings) + ";", queryParameters);
                    
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        return addFormButton;
    }

    public JFrame getWindow() {
        return window;
    }

    public JPanel getPanel() {
        return this.panel;
    }

}
