package main.java.fnfal113.bicol_region_info_management_system.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import main.java.fnfal113.bicol_region_info_management_system.interfaces.Database;

public class SQLDatabase implements Database {

    private Connection connection;

    public SQLDatabase() {
        createConnection();
    }

    @Override
    public void createConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:4306/bicol_region", "root", "admin");
        } catch (SQLException e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(null, e.getMessage(), "Info", 1);
        }
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

}
