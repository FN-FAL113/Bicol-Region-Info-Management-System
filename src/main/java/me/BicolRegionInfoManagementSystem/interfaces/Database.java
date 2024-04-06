package main.java.me.simpleapp.interfaces;

import java.sql.Connection;

public interface Database {

    void createConnection();

    Connection getConnection();

}
