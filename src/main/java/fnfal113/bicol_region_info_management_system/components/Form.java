package main.java.fnfal113.bicol_region_info_management_system.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import main.java.fnfal113.bicol_region_info_management_system.App;
import main.java.fnfal113.bicol_region_info_management_system.db.SQLRepository;
import main.java.fnfal113.bicol_region_info_management_system.handlers.ButtonHandler;
import main.java.fnfal113.bicol_region_info_management_system.utils.JTableUtils;

public class Form extends JLayeredPane {
    
    private String tableName;
    private String bgImageFileName;

    public Form(String tableName, String bgImageFIleName) {
        this.tableName = tableName;
        this.bgImageFileName = bgImageFIleName;

        init();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            BufferedImage buffed = ImageIO.read(App.class.getResourceAsStream("assets/" + this.bgImageFileName + ".png"));

            g.drawImage(buffed, this.getSize().width - 52, 14, this);
        } catch (IOException e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(this, e.getMessage(), "Info", 1);
        }
    }

    public void init() {
        this.setLayout(new GridBagLayout());
        this.putClientProperty(FlatClientProperties.STYLE, "arc: 19");
        this.setBackground(Color.decode("#8596F4"));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        
        // center form header
        gbc.gridx = 1; 
        gbc.weightx = 0;
        gbc.gridy = gbc.gridy + 1;

        JLabel formHeader = new JLabel(tableName);

        formHeader.setFont(new Font("Inter Bold", Font.BOLD, 16));
        
        this.add(formHeader, gbc);

        gbc.gridx = 0;
        gbc.weightx = 1; // distribute form text fields horizontal space
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = gbc.gridy + 1;

        try {
            ResultSet tableColumnsResultSet = new SQLRepository().getColumnNames(tableName.toLowerCase());

            // traverse table columns
            while (tableColumnsResultSet.next()) {
                String columnName = tableColumnsResultSet.getString(1);

                FormField formField = new FormField(columnName.equals("id") ? columnName + " (optional)" : columnName);

                App.mainWindow().getManageData().getTableFieldsMap().put(tableName + "." + columnName, formField.getTextField());

                this.add(formField.getPanel(), gbc);

                // limit three text field per row
                if(gbc.gridx == 2) { 
                    gbc.gridx = 0;
                    gbc.gridy = gbc.gridy + 1;
                } else {
                    gbc.gridx = gbc.gridx + 1;
                }
            }

            gbc.gridx = 1;
            gbc.gridy = gbc.gridy + 1;
            gbc.fill = GridBagConstraints.NONE;
            
            this.add(createFormButtons(tableName), gbc);

            gbc.gridx = 0;
            gbc.gridy = gbc.gridy + 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JPanel createFormButtons(String tableName) {
        JPanel formButtonsPanel = new JPanel();

        JButton addDataButton = new JButton();

        addDataButton.setText("Add");

        addDataButton.setFont(new Font("Inter Bold", Font.BOLD, 12));

        addDataButton.setMargin(new Insets(6, 6, 6, 6));

        addDataButton.addMouseListener(new ButtonHandler() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    SQLRepository repository = new SQLRepository();

                    ResultSet resultSet = repository.getColumnNames(tableName.toLowerCase());
                    
                    ArrayList<String> queryBindings = new ArrayList<>();
                    ArrayList<Object> queryParameters = new ArrayList<>();

                    // traverse table columns
                    while (resultSet.next()) {
                        String columnName = resultSet.getString(1);
                        
                        queryBindings.add(columnName + " = ?") ;

                        queryParameters.add(App.mainWindow().getManageData().getTableFieldsMap().get(tableName + "." + columnName).getText()); 
                    }

                    repository.addOrUpdate("INSERT INTO " + tableName + " SET " + String.join(",", queryBindings) + ";", queryParameters);

                    JTableUtils.refreshTables(App.mainWindow().getDashboard().getTables().values());
                } catch (SQLException ex) {
                    ex.printStackTrace();

                    JOptionPane.showMessageDialog(addDataButton, ex.getMessage(), "Info", 1);
                }
            }
        });

        formButtonsPanel.add(addDataButton);
        
        JButton deleteDataButton = new JButton();

        deleteDataButton.setText("Delete");

        deleteDataButton.setToolTipText("Delete a table record by ID");

        deleteDataButton.setFont(new Font("Inter Bold", Font.BOLD, 12));

        deleteDataButton.setMargin(new Insets(6, 6, 6, 6));

        deleteDataButton.addMouseListener(new ButtonHandler() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SQLRepository repository = new SQLRepository();
               
                String id = App.mainWindow().getManageData().getTableFieldsMap().get(tableName + "." + "id").getText();

                if(id.isEmpty()) {
                    JOptionPane.showMessageDialog(addDataButton, "ID field cannot be empty", "Info", 1);

                    return;
                }

                repository.delete("DELETE FROM " + tableName + " WHERE id = ?;", id);

                JTableUtils.refreshTables(App.mainWindow().getDashboard().getTables().values());
            }
        });

        formButtonsPanel.add(deleteDataButton);
        
        return formButtonsPanel;
    }


}
