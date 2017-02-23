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
        int datacenterLatency = video.getSize() * endpoint.getDatacenterLatency();
        for (Cache cache : endpoint.getCaches().keySet()) {
            int cacheLatency = video.getSize() * endpoint.getCaches().get(cache);
            int score = datacenterLatency - cacheLatency;
            scores.put(cache,score);
        }
        return scores;
    }

}