package com.sfeir.hashcode.connection;

import com.sfeir.hashcode.model.Cache;

import java.sql.*;

/**
 * Created by bcornu on 2/24/17.
 */
public class CacheDB {

    private String creation = "CREATE TABLE cache (" +
            "id int," +
            "size int," +
            "CONSTRAINT cache_pk PRIMARY KEY (id)" +
            ")";
    private String insertion = "INSERT INTO cache (id, size) VALUES (?, ?)";
    private String suppression = "DROP TABLE IF EXISTS cache";
    private Connector connector;

    public CacheDB(Connector connector) {
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

    public void insert(Cache c) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertion);
        preparedStatement.setInt(1, c.getId());
        preparedStatement.setInt(2, c.getSize());
        preparedStatement.executeUpdate();
    }

    public int getCacheSize()  throws SQLException {
        String request = "SELECT size FROM cache LIMIT 1";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(request);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            return rs.getInt("size");
        }
        return -1;
    }
}
