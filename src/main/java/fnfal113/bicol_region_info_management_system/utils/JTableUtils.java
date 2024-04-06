package main.java.fnfal113.bicolregioninfomanagementsystem.utils;

import java.sql.ResultSet;

import javax.swing.JTable;

import main.java.fnfal113.bicolregioninfomanagementsystem.interfaces.Repository;

import java.sql.ResultSetMetaData;

public class JTableUtils {
    
    public static JTable createTableFromResultSet(String tableName, Repository repository) {
        try {
            ResultSet resultSet = repository.getAll(tableName.toLowerCase());
            ResultSetMetaData metaData = resultSet.getMetaData();

            String[] columns = new String[metaData.getColumnCount()];
            Object[][] data = new Object[repository.getRowCount("SELECT COUNT(*) FROM " + metaData.getTableName(1))][columns.length];

            for (int i = 0; i < columns.length; i++) {
                columns[i] = metaData.getColumnName(i + 1);
            }
  
            int row = 0;
            while (resultSet.next()) {
                for (int col = 0; col < columns.length; col++) {
                    data[row][col] = resultSet.getObject(col + 1);
                }

                row++;
            }

            JTable table = new JTable(data, columns);

            return table;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }        
    }
    

}
