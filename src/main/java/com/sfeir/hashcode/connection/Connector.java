package com.sfeir.hashcode.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by bcornu on 2/13/17.
 */
public class Connector {

    private String url;
    private String login;
    private String password;
    private Connection connection;

    public Connector(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;
    }

    public void test() throws SQLException {
        Connection connection = DriverManager.getConnection(url, login, password);
        if (connection != null) {
            connection.close();
        } else {
            System.out.println("Failed to make connection!");
            throw new SQLException("cannot make connection, unknown reason");
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed())
            connection = DriverManager.getConnection(url, login, password);
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null)
            connection.close();
    }
}
