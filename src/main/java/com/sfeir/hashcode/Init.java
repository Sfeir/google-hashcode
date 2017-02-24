package com.sfeir.hashcode;

/**
 * Created by Sfeir-Lille on 23/02/17.
 */
public class Init {


    private final String initLine;
    private final int videos;
    private final int endpoints;
    private final int requests;
    private final int caches;
    private final int cacheSize;

    public Init(String initLine){
        this.initLine = initLine;
        videos = Integer.valueOf(initLine.split(" ")[0]);
        endpoints = Integer.valueOf(initLine.split(" ")[1]);
        requests = Integer.valueOf(initLine.split(" ")[2]);
        caches = Integer.valueOf(initLine.split(" ")[3]);
        cacheSize = Integer.valueOf(initLine.split(" ")[4]);
    }

    public int numberOfVideos(){
        return videos;
    }

    public int numberOfEndpoints(){
        return endpoints;
    }

    public int numberOfRequestDescription(){
        return requests;
    }

    public int numberOfCaches(){
        return caches;
    }

    public int cacheSize(){
        return cacheSize;
    }

}
