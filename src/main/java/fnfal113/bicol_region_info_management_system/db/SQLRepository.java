package main.java.fnfal113.bicolregioninfomanagementsystem.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.java.fnfal113.bicolregioninfomanagementsystem.interfaces.Repository;

public class SQLRepository implements Repository {

    @Override
    public ResultSet getAll(String tableName) {
        try {
            Connection con = new SQLDatabase().getConnection();

            Statement stmt = con.createStatement();

            return stmt.executeQuery("SELECT * FROM " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public ResultSet get(String query) {
        try {
            Connection con = new SQLDatabase().getConnection();

            Statement stmt = con.createStatement();

            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public int getRowCount(String query) {
        try {
            Connection con = new SQLDatabase().getConnection();

            Statement stmt = con.createStatement();

            ResultSet resultSet = stmt.executeQuery(query);

            resultSet.next();

            int rowCount = resultSet.getInt(1);

            return rowCount;
        } catch (SQLException e) {
            e.printStackTrace();

            return -1;
        }
    }

    @Override
    public void update(String query, Object[] queryParameters) {
        try {
            Connection con = new SQLDatabase().getConnection();

            PreparedStatement stmt = con.prepareStatement(query);

            for (int i = 0; i < queryParameters.length; i++) {
                stmt.setObject(i + 1, queryParameters[i]);                
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(String query) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
