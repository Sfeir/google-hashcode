package com.sfeir.hashcode.algos;

import com.sfeir.hashcode.Validator;
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
        if(endpoint.getRequests().get(video) != null){
            int datacenterLatency = endpoint.getDatacenterLatency() * endpoint.getRequests().get(video);
            for (Cache cache : endpoint.getCaches().keySet()) {
                int cacheLatency = endpoint.getCaches().get(cache) * endpoint.getRequests().get(video);
                int score = datacenterLatency - cacheLatency;
                scores.put(cache,score);
            }
        }
        return scores;
    }

    public static int getScorePerVideo(Cache c, Video v){
        int res =0;
        for (Endpoint e : c.getEndpoints()) {
            res += e.getScore(v).get(c);
        }
        return res;
    }

    public static void cacheHighestScore(Video video) {
        System.out.println(String.format("start with video number %s",video.getId()));
        for (Cache cache : Cache.getCaches()) {
            for (Endpoint endpoint : Endpoint.getEndpoints()) {
                Map<Cache, Integer> score = endpoint.getScore(video);
                score.keySet().stream().filter(cacheToValidate -> Validator.canPutInCache(video, cacheToValidate)).forEach(cacheToValidate -> cache.addVideo(video));

            }
        }
        System.out.println(String.format("end with video number %s",video.getId()));
    }


}
