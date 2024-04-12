package main.java.fnfal113.bicol_region_info_management_system;

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
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.fnfal113.bicol_region_info_management_system.components.FormField;
import main.java.fnfal113.bicol_region_info_management_system.components.Widget;
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
        this.panel.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));

        this.panel.add(createAddForms());
    }

    private JPanel createAddForms() {
        JPanel addFormPanel = new JPanel(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);

        for (String tableName : this.tableNames) {
            // center form header
            gbc.gridx = 1; 
            gbc.weightx = 0;
            gbc.gridy = gbc.gridy + 1;
            
            // widget
            addFormPanel.add(new Widget(tableName, tableName, "#8596F4").getPanel(), gbc);

            gbc.gridx = 0;
            gbc.weightx = 1; // distribute form text fields horizontal space
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridy = gbc.gridy + 1;

            try {
                ResultSet tableColumnsResultSet = new SQLRepository().getColumnNames(tableName.toLowerCase());

                // traverse table columns
                while (tableColumnsResultSet.next()) {
                    String columnName = tableColumnsResultSet.getString(1);

                    if(columnName.equals("id")) continue;

                    FormField formField = new FormField(columnName);

                    getFormFields().put(tableName + "." + columnName, formField.getTextField());

                    addFormPanel.add(formField.getPanel(), gbc);

                    if(gbc.gridx == 2) { // limit to 3 text field per grid bag row
                        gbc.gridx = 0;
                        gbc.gridy = gbc.gridy + 1;
                    } else {
                        gbc.gridx = gbc.gridx + 1;
                    }
                }

                gbc.gridx = 1;
                gbc.gridy = gbc.gridy + 1;
                gbc.fill = GridBagConstraints.NONE;
                
                addFormPanel.add(createAddFormButton(tableName), gbc);

                gbc.gridx = 0;
                gbc.gridy = gbc.gridy + 1;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return addFormPanel;
    }

    public JButton createAddFormButton(String tableName) {
        JButton addFormButton = new JButton();

        addFormButton.setText("Add Data");

        addFormButton.setFont(new Font("Inter Bold", Font.BOLD, 12));

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

                        queryParameters.add(getFormFields().get(tableName + "." + columnName).getText()); 
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

    public Map<String, JTextField> getFormFields() {
        return formFields;
    }

}
