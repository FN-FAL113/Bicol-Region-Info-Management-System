package main.java.fnfal113.bicolregioninfomanagementsystem.interfaces;

import java.sql.Connection;

public interface Database {

    void createConnection();

    Connection getConnection();

}
