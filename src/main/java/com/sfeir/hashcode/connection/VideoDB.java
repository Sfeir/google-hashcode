package com.sfeir.hashcode.connection;

import com.sfeir.hashcode.model.Cache;
import com.sfeir.hashcode.model.Video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by bcornu on 2/24/17.
 */
public class VideoDB {


    private String creation = "CREATE TABLE video (" +
            "id int," +
            "size int," +
            "CONSTRAINT video_pk PRIMARY KEY (id)" +
            ");";
    private String insertion = "INSERT INTO video (id, size) VALUES (?, ?);";
    private String suppression = "DROP TABLE IF EXISTS video;";
    private Connector connector;

    public VideoDB(Connector connector) {
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

    public void insert(Video v) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertion);
        preparedStatement.setInt(1, v.getId());
        preparedStatement.setInt(2, v.getSize());
        preparedStatement.executeUpdate();
        connector.closeConnection();
    }
}
