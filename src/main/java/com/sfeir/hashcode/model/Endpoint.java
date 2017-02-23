package com.sfeir.hashcode.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bcornu on 2/23/17.
 */
public class Endpoint {

    private static final Map<Integer, Endpoint> endpoints = new HashMap<>();

    public static Map<Integer, Endpoint> getEndpoints() {
        return endpoints;
    }

    private int id;
    private int datacenterLatency;
    private Map<Integer, Integer> caches = new HashMap<>();
    private Map<Integer, Integer> requests = new HashMap<>();

    public Endpoint(int id, int datacenterLatency) {
        this.id = id;
        this.datacenterLatency = datacenterLatency;
    }

    public int getDatacenterLatency() {
        return datacenterLatency;
    }

    public int getId() {
        return id;
    }

    public void addRequest(int video, int nb){
        requests.put(video, nb);
    }

    public void addCache(int cache, int latence){
        caches.put(cache, latence);
    }

    public Map<Integer, Integer> getCaches() {
        return caches;
    }

    public List<Integer> getAvailableCaches(int videoId){
        List<Integer> res = new ArrayList<>();
        int videoSize = Video.getVideos().get(videoId).getSize();
        for (Integer id:caches.keySet()) {
            if(videoSize < Cache.getCaches().get(id).getRemainingSpace())
                res.add(id);
        }
        return res;
    }
}
