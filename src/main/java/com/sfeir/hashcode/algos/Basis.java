package com.sfeir.hashcode.algos;

import com.sfeir.hashcode.connection.*;
import com.sfeir.hashcode.model.Cache;
import com.sfeir.hashcode.model.Endpoint;
import com.sfeir.hashcode.model.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by bcornu on 2/23/17.
 */
public class Basis {

    public static void run(Connector connector) {

        CacheDB cdb = new CacheDB(connector);
        VideoDB vdb = new VideoDB(connector);
        EndpointDB edb = new EndpointDB(connector);
        LatenceDB ldb = new LatenceDB(connector);
        RequestDB rdb = new RequestDB(connector);

        int nbCache = Cache.getCaches().size();
        for (Cache c: Cache.getCaches()) {
            System.out.println(c+"/"+nbCache);
            if (!c.getEndpoints().isEmpty()) {
                List<Video> videosForEP = new ArrayList<>();
                for (Endpoint endpoint : c.getEndpoints()) {
                    videosForEP.addAll(endpoint.getRequests().keySet());
                }
                Set<ScorePerVideo> scores = new TreeSet<>();
                for (Video v : videosForEP) {
                    int score4v = c.getScore(v);
                    scores.add(new ScorePerVideo(score4v, v));
                }
                for (ScorePerVideo score : scores) {
                    if (c.getRemainingSpace() > score.getVideoSize()) {
                        c.addVideo(score.getVideo());
                    }
                }
            }
        }
    }
}

class ScorePerVideo implements  Comparable{

    private Integer score;
    private Video v;
    private Float ratio;

    ScorePerVideo(int score, Video v) {
        this.score = score;
        this.v = v;
        ratio = (float)score/(float)v.getSize();
    }

    @Override
    public int compareTo(Object o) {
        if(!(o instanceof ScorePerVideo))
            throw new IllegalArgumentException();
        return ((ScorePerVideo) o).ratio.compareTo(this.ratio);
    }

    @Override
    public String toString() {
        return score + " for "+ v.getId();
    }

    public int getVideoSize() {
        return v.getSize();
    }

    public Video getVideo() {
        return v;
    }
}