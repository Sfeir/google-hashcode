package com.sfeir.hashcode.connection;

import com.sfeir.hashcode.model.Cache;
import com.sfeir.hashcode.model.Gain;
import com.sfeir.hashcode.model.Video;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    }

    public void initWorkTable() throws SQLException{
        Connection connection = getConnection();
        String del = "DROP TABLE IF EXISTS video_work";
        String create = "CREATE TABLE video_work ( " +
                "video_id int, " +
                "size int, " +
                "CONSTRAINT video_work_pk PRIMARY KEY (video_id) " +
                ")";
        String index = "CREATE INDEX video_work_index ON video_work (size)";
        String truncate = "TRUNCATE TABLE video_work";
        String init = "INSERT INTO video_work (video_id, size) SELECT id, size FROM video";
        PreparedStatement preparedStatement = connection.prepareStatement(del);
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement(create);
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement(index);
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement(truncate);
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement(init);
        preparedStatement.executeUpdate();
    }

    public List<Video> getAll() throws SQLException{
        Connection connection = getConnection();
        String select = "SELECT * FROM video_work ORDER BY size DESC";
        PreparedStatement preparedStatement = connection.prepareStatement(select);
        ResultSet rs = preparedStatement.executeQuery();

        List<Video> res = new ArrayList<>();
        while(rs.next()){
            int video = rs.getInt("video_id");
            int size = rs.getInt("size");
            res.add(new Video(video, size));
        }
        return res;
    }
}
