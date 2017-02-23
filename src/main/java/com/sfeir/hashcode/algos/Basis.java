package com.sfeir.hashcode.algos;

import com.sfeir.hashcode.model.Cache;
import com.sfeir.hashcode.model.Endpoint;
import com.sfeir.hashcode.model.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bcornu on 2/23/17.
 */
public class Basis {

    public static void run() {
        for (Cache c: Cache.getCaches()) {
            if (!c.getEndpoints().isEmpty()) {
                List<Video> videosForEP = new ArrayList<>();
                for (Endpoint endpoint : c.getEndpoints()) {
                    videosForEP.addAll(endpoint.getRequests().keySet());
                }
                for (Video v : videosForEP) {
                    int score4v = c.getScore(v);
                    System.out.println(score4v);
                    System.out.println(Scoring.getScorePerVideo(c,v));
                }
            }
        }
    }
}
