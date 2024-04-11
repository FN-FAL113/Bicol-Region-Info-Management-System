package main.java.fnfal113.bicol_region_info_management_system.interfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public interface Repository {

    ResultSet getAll(String tableName);

    ResultSet get(String query);

    int getRowCount(String query);

    ResultSet getColumnNames(String tableName);

    void addOrUpdate(String query, ArrayList<Object> queryParameters);

    void delete(String query);

    Connection getConnection(); 

}
