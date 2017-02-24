package com.sfeir.hashcode.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bcornu on 2/24/17.
 */
public class Requestor {

    private Connector connector;

    public Requestor(Connector connector) {
        this.connector = connector;
    }

    private Connection getConnection() throws SQLException {
        if (connector == null)
            throw new RuntimeException("you must setConnector before this");
        return connector.getConnection();
    }

    public int[] getNextBest() throws SQLException {
        String request = "SELECT \"cid\", \"vid\" FROM " +
                "(" +
                "SELECT c.id as \"cid\", r.video_id as \"vid\", sum((e.default_latency - l.latency)*r.nb/v.size)  as \"gain\"" +
                " FROM cache c " +
                " INNER JOIN latence l ON c.id = l.cache_id " +
                " INNER JOIN request_work r ON l.endpoint_id = r.endpoint_id " +
                " INNER JOIN endpoint e ON e.id = r.endpoint_id " +
                " INNER JOIN video v ON v.id = r.video_id " +
                " WHERE c.size > v.size + COALESCE((SELECT SUM(v.size) FROM stock s LEFT OUTER JOIN video v ON s.video_id = v.id WHERE s.cache_id = c.id), 0) " +
                " GROUP BY \"cid\", \"vid\" " +
                " ORDER BY \"gain\" DESC " +
                " LIMIT 1 " +
                ")a";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(request);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()){
            return new int[]{rs.getInt("cid"),rs.getInt("vid")};
        }
        return null;
    }

    public void store(int[] a) throws SQLException {
        Connection connection = getConnection();
        String insert = "INSERT INTO stock (cache_id, video_id) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insert);
        preparedStatement.setInt(1, a[0]);
        preparedStatement.setInt(2, a[1]);
        preparedStatement.executeUpdate();

        String delete = "DELETE FROM request_work r WHERE endpoint_id IN (SELECT endpoint_id FROM latence WHERE cache_id = ?) AND video_id = ?";
        preparedStatement = connection.prepareStatement(delete);
        preparedStatement.setInt(1, a[0]);
        preparedStatement.setInt(2, a[1]);
        preparedStatement.executeUpdate();
    }

    public Map<Integer,List<Integer>> getStock() throws SQLException{
        Map<Integer,List<Integer>> res = new HashMap<>();
        Connection connection = getConnection();
        String select = "SELECT cache_id, video_id from stock order by cache_id, video_id";
        PreparedStatement preparedStatement = connection.prepareStatement(select);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            int cid = rs.getInt("cache_id");
            int vid = rs.getInt("video_id");
            if(! res.containsKey(cid)){
                res.put(cid, new ArrayList<>());
            }
            res.get(cid).add(vid);
        }
        return res;
    }
}
