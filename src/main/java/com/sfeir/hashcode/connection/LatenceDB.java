package com.sfeir.hashcode.connection;

import com.sfeir.hashcode.model.Cache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by bcornu on 2/24/17.
 */
public class LatenceDB {

    private String creation = "CREATE TABLE latence (" +
            "endpoint_id int," +
            "cache_id int," +
            "latency int," +
            "CONSTRAINT latence_pk PRIMARY KEY (endpoint_id, cache_id)" +
            ")";
    private String insertion = "INSERT INTO latence (endpoint_id, cache_id, latency) VALUES (?, ?, ?)";
    private String suppression = "DROP TABLE IF EXISTS latence";
    private Connector connector;

    public LatenceDB(Connector connector) {
        this.connector = connector;
    }

    private Connection getConnection() throws SQLException {
        if (connector == null)
            throw new RuntimeException("you must setConnector before this");
        return connector.getConnection();
    }

    public void deleteTable() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.execute(suppression);
        connector.closeConnection();
    }

    public void createTable() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.execute(creation);
        connector.closeConnection();
    }

    public void insert(int endpoint_id, int cache_id, int latency) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertion);
        preparedStatement.setInt(1, endpoint_id);
        preparedStatement.setInt(2, cache_id);
        preparedStatement.setInt(3, latency);
        preparedStatement.executeUpdate();
    }
}
