package com.sfeir.hashcode.algos;

import com.sfeir.hashcode.model.Cache;
import com.sfeir.hashcode.model.Endpoint;
import com.sfeir.hashcode.model.Video;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sfeir-Lille on 23/02/17.
 */
public class Scoring {

    public static Map<Cache,Integer> getScore(Video video, Endpoint endpoint){
        Map<Cache,Integer> scores = new HashMap<>();
        int datacenterLatency = video.getSize() * endpoint.getDatacenterLatency() * endpoint.getRequests().get(video);
        for (Cache cache : endpoint.getCaches().keySet()) {
            int cacheLatency = video.getSize() * endpoint.getCaches().get(cache) * endpoint.getRequests().get(video);
            int score = datacenterLatency - cacheLatency;
            scores.put(cache,score);
        }
        return scores;
    }

    public static int getScore(Cache c, Video v){
        int res =0;
        for (Endpoint e : c.getEndpoints()) {
            res += e.getScore(v).get(c);
        }
        return res;
    }

}
