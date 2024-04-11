package main.java.fnfal113.bicol_region_info_management_system.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.fnfal113.bicol_region_info_management_system.interfaces.Repository;

public class SQLRepository implements Repository {

    private Connection connection;

    @Override
    public ResultSet getAll(String tableName) {
        try {
            this.connection = new SQLDatabase().getConnection();

            Statement stmt = connection.createStatement();

            ResultSet resultSet = stmt.executeQuery("SELECT * FROM " + tableName);

            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(null, e.getMessage(), "Info", 1);

            return null;
        }
    }

    @Override
    public ResultSet get(String query) {
        try {
            this.connection = new SQLDatabase().getConnection();

            Statement stmt = connection.createStatement();

            ResultSet resultSet = stmt.executeQuery(query);

            this.connection.close();

            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(null, e.getMessage(), "Info", 1);

            return null;
        }
    }

    @Override
    public int getRowCount(String query) {
        try {
            this.connection = new SQLDatabase().getConnection();

            Statement stmt = connection.createStatement();

            ResultSet resultSet = stmt.executeQuery(query);

            resultSet.next();

            int rowCount = resultSet.getInt(1);

            this.connection.close();

            return rowCount;
        } catch (SQLException e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(null, e.getMessage(), "Info", 1);

            return -1;
        }
    }

    @Override
    public ResultSet getColumnNames(String tableName) {
        try {
            Connection con = new SQLDatabase().getConnection();

            PreparedStatement stmt = con.prepareStatement("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ? ORDER by ordinal_position;");

            stmt.setString(1, tableName);

            ResultSet resultSet = stmt.executeQuery();

            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(null, e.getMessage(), "Info", 1);

            return null;
        }
    }

    @Override
    public void addOrUpdate(String query, ArrayList<Object> queryParameters) {
        try {
            this.connection = new SQLDatabase().getConnection();

            PreparedStatement stmt = this.connection.prepareStatement(query);

            for (int i = 0; i < queryParameters.size(); i++) {
                stmt.setObject(i + 1, queryParameters.get(i).toString().length() > 0 ? queryParameters.get(i).toString() : null);            
            }

            stmt.executeUpdate();

            this.connection.close();
   
            JOptionPane.showMessageDialog(null, "Database updated successfully!", "Info", 1);
        } catch (SQLException e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(null, e.getMessage(), "Info", 0);
        }
    }

    @Override
    public void delete(String query) {
    }

    public Connection getConnection() {
        return this.connection;
    }
}
