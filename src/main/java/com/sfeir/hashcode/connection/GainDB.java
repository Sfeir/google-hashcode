package com.sfeir.hashcode.connection;

import com.sfeir.hashcode.model.Cache;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bcornu on 2/24/17.
 */
public class GainDB {

    private String creation = "CREATE TABLE gains ( " +
            "cache_id int, " +
            "video_id int, " +
            "gain BIGINT, " +
            "size int, " +
            "r_gain int, " +
            "CONSTRAINT gain_pk PRIMARY KEY (cache_id, video_id) " +
            ")";
    private String init = "INSERT INTO gains (cache_id, video_id, gain)   " +
            "(  " +
            "SELECT l.cache_id as \"cid\", r.video_id as \"vid\", sum((e.default_latency - l.latency)*r.nb) as \"gain\"  " +
            "FROM latence l  " +
            "INNER JOIN request_work r ON l.endpoint_id = r.endpoint_id  " +
            "INNER JOIN endpoint e ON e.id = r.endpoint_id  " +
            "GROUP BY \"cid\", \"vid\"  " +
            "ORDER BY \"gain\" DESC, \"cid\", \"vid\"  " +
            ");";
    private String setSize = "UPDATE gains set size = (SELECT size v FROM video v WHERE id = video_id)";
    private String setRelatives = "UPDATE gains set r_gain = gain / size;";
    private String suppression = "DROP TABLE IF EXISTS gains";
    private Connector connector;

    public GainDB(Connector connector) {
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

    public void init() throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(init);
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement(setSize);
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement(setRelatives);
        preparedStatement.executeUpdate();
    }

    public List<int[]> getNextsBests() throws SQLException {
        String request = "SELECT g.cache_id, g.video_id, g.size \n" +
                "FROM gains g\n" +
                "INNER JOIN cache c ON c.id = g.cache_id\n" +
                "WHERE c.size > g.size + COALESCE((SELECT SUM(v.size) FROM stock s LEFT OUTER JOIN video v ON s.video_id = v.id WHERE s.cache_id = c.id), 0)\n" +
                "ORDER BY r_gain DESC LIMIT 100";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(request);
        ResultSet rs = preparedStatement.executeQuery();
        List<Integer> caches = new ArrayList<>();
        List<Integer> videos = new ArrayList<>();
        List<int[]> res = new ArrayList<>();
        while(rs.next()){
            int cache = rs.getInt("cache_id");
            int video = rs.getInt("video_id");
            int size = rs.getInt("size");
            if(caches.contains(cache) || videos.contains(video)){
                return res;
            }
            caches.add(cache);
            videos.add(video);
            res.add(new int[]{cache, video, size});
        }
        return res;
    }

    public int impactCaches(int cid, int vid, int size) throws SQLException{
        String select = "SELECT l.cache_id as \"cid\", sum(nb * (default_latency - latency)) AS \"diff\" FROM ( " +
                "SELECT * FROM request r WHERE endpoint_id IN ( " +
                "SELECT endpoint_id FROM latence WHERE cache_id = ? " +
                ") AND video_id = ? " +
                ") a " +
                "INNER JOIN endpoint e ON a.endpoint_id = e.id " +
                "INNER JOIN latence l ON l.endpoint_id = e.id " +
                "GROUP BY l.cache_id";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(select);
        preparedStatement.setInt(1, cid);
        preparedStatement.setInt(2, vid);
        ResultSet rs = preparedStatement.executeQuery();
        List<int[]> impacts = new ArrayList<>();
        String update = "UPDATE gains SET gain = gain - ?, r_gain = (gain - ?)/ size WHERE cache_id = ? and video_id = ?";
        while(rs.next()) {
            int cache = rs.getInt("cid");
            long perte = rs.getLong("diff");
            preparedStatement = connection.prepareStatement(update);
            preparedStatement.setLong(1, perte);
            preparedStatement.setLong(2, perte);
            preparedStatement.setInt(3, cache);
            preparedStatement.setInt(4, vid);
            preparedStatement.executeUpdate();
        }
        String clean = "DELETE FROM gains WHERE gain <= 0;";
        preparedStatement = connection.prepareStatement(clean);
        return preparedStatement.executeUpdate();
    }

    public int getNbRemaining() throws SQLException{
        String request = "SELECT COUNT(0) as \"remain\" FROM gains;";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(request);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()){
            return rs.getInt("remain");
        }
        return -1;
    }

    public int store(int[] a) throws SQLException{
        Connection connection = getConnection();
        String insert = "INSERT INTO stock (cache_id, video_id) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insert);
        preparedStatement.setInt(1, a[0]);
        preparedStatement.setInt(2, a[1]);
        preparedStatement.executeUpdate();

        return impactCaches(a[0], a[1], a[2]);
    }
}
