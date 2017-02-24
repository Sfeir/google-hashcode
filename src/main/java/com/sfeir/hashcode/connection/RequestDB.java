package com.sfeir.hashcode.connection;

import com.sfeir.hashcode.model.Request;

import java.sql.*;

/**
 * Created by bcornu on 2/24/17.
 */
public class RequestDB {

    private String creation = "CREATE TABLE request (" +
            "endpoint_id int," +
            "video_id int," +
            "nb int," +
            "CONSTRAINT request_pk PRIMARY KEY (endpoint_id, video_id)" +
            ")";
    private String insertion = "INSERT INTO request (endpoint_id, video_id, nb) VALUES (?, ?, ?)";
    private String add = "UPDATE request SET nb = nb + ? WHERE endpoint_id = ? AND video_id = ?";
    private String select = "SELECT * FROM request WHERE endpoint_id = ? AND video_id = ?";
    private String suppression = "DROP TABLE IF EXISTS request";
    private Connector connector;

    public RequestDB(Connector connector) {
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

    public void insert(int endpoint_id, int video_id, int nb) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertion);
        preparedStatement.setInt(1, endpoint_id);
        preparedStatement.setInt(2, video_id);
        preparedStatement.setInt(3, nb);
        preparedStatement.executeUpdate();
    }

    public void addRequest(int endpoint_id, int video_id, int nb) throws SQLException{
        if(!existsRequest(endpoint_id, video_id)){
            insert(endpoint_id, video_id, nb);
        }else{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(add);
            preparedStatement.setInt(1, nb);
            preparedStatement.setInt(2, endpoint_id);
            preparedStatement.setInt(3, video_id);
            preparedStatement.executeUpdate();
        }
    }

    private boolean existsRequest(int endpoint_id, int video_id) throws SQLException{
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(select);
        preparedStatement.setInt(1, endpoint_id);
        preparedStatement.setInt(2, video_id);
        ResultSet rs = preparedStatement.executeQuery();
        return rs.next();
    }
}
