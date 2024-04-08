package main.java.fnfal113.bicol_region_info_management_system.utils;

import java.sql.ResultSet;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.java.fnfal113.bicol_region_info_management_system.interfaces.Repository;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class JTableUtils {
    
    public static JTable createTableFromTableName(String tableName, Repository repository) {
        try {
            JTable table = new JTable(generateTableModel(tableName, repository));

            table.putClientProperty("name", tableName);

            return table;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }        
    }

    public static DefaultTableModel generateTableModel(String tableName, Repository repository) {
        try {
            ResultSet resultSet = repository.getAll(tableName);
            ResultSetMetaData metaData = resultSet.getMetaData();

            String[] columns = new String[metaData.getColumnCount()];
            Object[][] data = new Object[repository.getRowCount("SELECT COUNT(*) FROM " + metaData.getTableName(1))][columns.length];

            for (int i = 0; i < columns.length; i++) {
                // result set cell indexing starts at 1
                columns[i] = metaData.getColumnName(i + 1);
            }

            int row = 0;
            while (resultSet.next()) {
                // traverse columns data
                for (int col = 0; col < columns.length; col++) {
                    data[row][col] = resultSet.getObject(col + 1);
                }

                row++;
            }

            repository.getConnection().close();
            resultSet.close();

            return new DefaultTableModel(data, columns);
        } catch (SQLException e) {
           e.printStackTrace();

           return null;
        }
    }
    
    

}
