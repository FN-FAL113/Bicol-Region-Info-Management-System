package main.java.fnfal113.bicol_region_info_management_system.interfaces;

import java.sql.Connection;

public interface Database {

    void createConnection();

    Connection getConnection();

}
