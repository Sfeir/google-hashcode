package com.sfeir.hashcode.model;

import com.sfeir.hashcode.algos.Scoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bcornu on 2/23/17.
 */
public class Endpoint {

    private static final List<Endpoint> endpoints = new ArrayList<>();

    public static List<Endpoint> getEndpoints() {
        return endpoints;
    }

    private int id;
    private int datacenterLatency;
    private Map<Cache, Integer> caches = new HashMap<>();
    private Map<Video, Integer> requests = new HashMap<>();

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
        requests.put(Video.getVideo(video), nb);
    }

    public void addCache(int cache, int latence){
        Cache c = Cache.getCache(cache);
        caches.put(c, latence);
        c.addEndpoint(this.getId(), latence);
    }

    public Map<Cache, Integer> getCaches() {
        return caches;
    }

    public List<Cache> getAvailableCaches(int videoId) {
        return null;
    }

    public static Endpoint getEndpoint(int endpoint) {
        for (Endpoint e : endpoints) {
            if (e.getId() == endpoint)
                return e;
        }
        throw new IllegalArgumentException("not existing endpoint id: " + endpoint);
    }

    public Map<Video, Integer> getRequests() {
        return requests;
    }

    public Map<Cache,Integer> getScore(Video video){
        return Scoring.getScore(video,this);
    }

    @Override
    public String toString() {
        return getId()+"";
    }
}
