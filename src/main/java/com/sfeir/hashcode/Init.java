package com.sfeir.hashcode;

/**
 * Created by Sfeir-Lille on 23/02/17.
 */
public class Init {


    private final String initLine;

    public Init(String initLine){
        this.initLine = initLine;
    }

    public int numberOfVideos(){
        return Integer.valueOf(initLine.split(" ")[0]);
    }

    public int numberOfEndpoints(){
        return Integer.valueOf(initLine.split(" ")[1]);
    }

    public int numberOfRequestDescription(){
        return Integer.valueOf(initLine.split(" ")[2]);
    }

    public int numberOfCaches(){
        return Integer.valueOf(initLine.split(" ")[3]);
    }

    public int cacheSize(){
        return Integer.valueOf(initLine.split(" ")[4]);
    }

}
