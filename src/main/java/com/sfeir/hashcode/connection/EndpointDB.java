package com.sfeir.hashcode.connection;

import com.sfeir.hashcode.model.Cache;
import com.sfeir.hashcode.model.Endpoint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by bcornu on 2/24/17.
 */
public class EndpointDB {

    private String creation = "CREATE TABLE endpoint (" +
            "id int," +
            "default_latency int," +
            "CONSTRAINT endpoint_pk PRIMARY KEY (id)" +
            ");";
    private String insertion = "INSERT INTO endpoint (id, default_latency) VALUES (?, ?);";
    private String suppression = "DROP TABLE IF EXISTS endpoint;";
    private Connector connector;

    public EndpointDB(Connector connector) {
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

    public void insert(Endpoint e) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertion);
        preparedStatement.setInt(1, e.getId());
        preparedStatement.setInt(2, e.getDatacenterLatency());
        preparedStatement.executeUpdate();
    }
}
