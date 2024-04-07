package main.java.fnfal113.bicol_region_info_management_system.interfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Repository {

    ResultSet getAll(String tableName);

    ResultSet get(String query);

    int getRowCount(String query);

    void update(String query, Object[] queryParameters) throws SQLException;

    void delete(String query);

    Connection getConnection(); 

}
