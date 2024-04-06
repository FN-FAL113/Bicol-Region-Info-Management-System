package main.java.me.simpleapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import main.java.me.simpleapp.interfaces.Database;

public class SQLDatabase implements Database {

    private Connection connection;

    public SQLDatabase() {
        createConnection();
    }

    @Override
    public void createConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:4306/bicol_region", "root", "admin");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

}
