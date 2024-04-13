package main.java.fnfal113.bicol_region_info_management_system.utils;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import main.java.fnfal113.bicol_region_info_management_system.App;
import main.java.fnfal113.bicol_region_info_management_system.db.SQLRepository;
import main.java.fnfal113.bicol_region_info_management_system.interfaces.Repository;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class JTableUtils {
    
    public static JTable createTableFromDatabase(String tableName, Repository repository) {
        try {
            JTable table = new JTable(createTableModel(tableName, repository));

            TableRowSorter<TableModel> tableRowSorter = new TableRowSorter<>(table.getModel());

            table.setRowSorter(tableRowSorter);

            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            table.putClientProperty("name", tableName);

            return table;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }        
    }

    public static DefaultTableModel createTableModel(String tableName, Repository repository) {
        try {
            ResultSet resultSet = repository.getAll(tableName);
            
            ResultSetMetaData metaData = resultSet.getMetaData();

            String[] columns = new String[metaData.getColumnCount()];
            
            Object[][] data = new Object[repository.getRowCount("SELECT COUNT(*) FROM " + metaData.getTableName(1))][columns.length];

            // result set cell indexing starts at 1
            for (int i = 0; i < columns.length; i++) {   
                columns[i] = metaData.getColumnName(i + 1);
            }

            while (resultSet.next()) {
                // traverse column data
                for (int col = 0; col < columns.length; col++) {
                    data[resultSet.getRow() - 1][col] = resultSet.getObject(col + 1);
                }
            }

            repository.getConnection().close();

            resultSet.close();

            return new DefaultTableModel(data, columns);
        } catch (SQLException e) {
           e.printStackTrace();

           return null;
        }
    }

    public static TableModelListener createTableModelListener(JTable table, String tableName) {
        return new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                SQLRepository repository = new SQLRepository();

                ArrayList<Object> queryParameters = new ArrayList<>();
                
                queryParameters.add(table.getValueAt(e.getFirstRow(), e.getColumn())); // new value
                queryParameters.add(table.getClientProperty("rowId")); // original row id          

                repository.addOrUpdate("UPDATE " + tableName + 
                    " SET " + table.getColumnName(e.getColumn()) + " = ? WHERE id = ?;", queryParameters);

                refreshTables(App.mainWindow().getDashboard().getTables().values());
            }
            
        };
    }

    public static FocusListener createTableFocusListener(JTable table) {
        return new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                // current focused row id as property, use for updating row ID instead of using #getValueAt which returns new ID value
                table.putClientProperty("rowId", table.getValueAt(table.getSelectedRow(), 0));
            }

            @Override
            public void focusLost(FocusEvent e) {}
            
        };
    }

    public static void refreshTables(Collection<JTable> tables) {
        for (JTable mapTableValue : tables) {
            String tableName = mapTableValue.getClientProperty("name").toString();

            // create new table model and attach listeners to it
            DefaultTableModel model = createTableModel(tableName, new SQLRepository());

            ((TableRowSorter<TableModel>) mapTableValue.getRowSorter()).setModel(model);
             
            mapTableValue.setModel(model);
             
            mapTableValue.getModel().addTableModelListener(createTableModelListener(mapTableValue, tableName)); 
            
            mapTableValue.addFocusListener(createTableFocusListener(mapTableValue));
        }
    }

}
