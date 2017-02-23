package com.sfeir.hashcode.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bcornu on 2/23/17.
 */
public class Cache {

    private static final List<Cache> caches = new ArrayList<>();

    public static List<Cache> getCaches() {
        return caches;
    }

    private int id;
    private int size;
    private List<Video> videos = new ArrayList<>();
    private List<Endpoint> endpoints = null;
    private List<Integer> endpointsIDs = new ArrayList<>();

    public Cache(int id, int size) {
        this.id = id;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int getId() {
        return id;
    }

    public void addVideo(int videoId){
        videos.add(Video.getVideo(videoId));
    }

    public void addVideo(Video v){
        videos.add(v);
    }

    public int getRemainingSpace(){
        int res = size;
        for (Video video:videos) {
            res-=video.getSize();
        }
        return res;
    }

    public String getOutput(){
        String res = ""+id;
        for (Video video: videos) {
            res+=" "+video.getId();
        }
        return res;
    }

    @Override
    public String toString() {
        return "CACHE "+getOutput();
    }

    public void addEndpoint(int endpoint, int latence){
        endpointsIDs.add(endpoint);
    }

    public List<Endpoint> getEndpoints() {
        if(endpoints == null){
            endpoints = new ArrayList<>();
            for (Integer endpointsID : endpointsIDs) {
                endpoints.add(Endpoint.getEndpoint(endpointsID));
            }
        }
        return endpoints;
    }

    public static Cache getCache(int cache) {
        for (Cache c : caches)
            if (c.getId() == cache)
                return c;
        throw new IllegalArgumentException("not existing cache id: " + cache);
    }

    public int getScore(Video v){
        int res =0;
        for (Endpoint e : getEndpoints()) {
            if(e.getRequests().containsKey(v)){
                System.out.println("ENDPOINT "+e);
                System.out.println("LATENCY "+e.getDatacenterLatency());
                System.out.println("CACHES "+e.getCaches());
                System.out.println("SEARCHED "+this);
                System.out.println("CACHE "+e.getCaches().get(this));
                int gain = (e.getDatacenterLatency()-e.getCaches().get(this));
                res+=gain*e.getRequests().get(v);
            }
        }
        return res;
    }

}
