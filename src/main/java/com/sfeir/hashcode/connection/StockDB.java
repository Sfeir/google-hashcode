package com.sfeir.hashcode.connection;

import java.sql.*;

/**
 * Created by bcornu on 2/24/17.
 */
public class StockDB {

    private String creation = "CREATE TABLE stock (" +
            "cache_id int," +
            "video_id int," +
            "CONSTRAINT stock_pk PRIMARY KEY (cache_id, video_id)" +
            ")";
    private String insertion = "INSERT INTO stock (cache_id, video_id) VALUES (?, ?)";
    private String suppression = "DROP TABLE IF EXISTS stock";
    private Connector connector;

    public StockDB(Connector connector) {
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

    public void insert(int cacheId, int videoId) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertion);
        preparedStatement.setInt(1, cacheId);
        preparedStatement.setInt(2, videoId);
        preparedStatement.executeUpdate();
    }

    public int getUsedPlace(int cacheId) throws SQLException{
        String request = "SELECT SUM(v.size) as \"remain\" FROM stock s LEFT OUTER JOIN video v ON s.video_id = v.id WHERE s.cache_id = ?";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(request);
        preparedStatement.setLong(1, cacheId);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()){
            return rs.getInt("remain");
        }
        return -1;
    }
}
