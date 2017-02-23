package com.sfeir.hashcode.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bcornu on 2/23/17.
 */
public class Cache {

    private static final Map<Integer, Cache> caches = new HashMap<>();

    public static Map<Integer, Cache> getCaches() {
        return caches;
    }

    private int id;
    private int size;
    private List<Integer> videos = new ArrayList<>();

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
        videos.add(videoId);
    }

    public int getRemainingSpace(){
        int res = size;
        for (int videoId:videos) {
            res-=Video.getVideos().get(videoId).getSize();
        }
        return res;
    }

    public String getOutput(){
        String res = ""+id;
        for (int videoId: videos) {
            res+=" "+videoId;
        }
        return res;
    }

    @Override
    public String toString() {
        return "CACHE "+getOutput();
    }
}
