package main.java.fnfal113.bicolregioninfomanagementsystem.interfaces;

import java.sql.ResultSet;

public interface Repository {

    ResultSet getAll(String tableName);

    ResultSet get(String query);

    int getRowCount(String query);

    void update(String query, Object[] queryParameters);

    void delete(String query);

}
