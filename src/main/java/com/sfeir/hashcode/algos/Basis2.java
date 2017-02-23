package com.sfeir.hashcode.algos;

import com.sfeir.hashcode.model.Video;

/**
 * Created by tonyproum on 23/02/17.
 */
public class Basis2 {
    public static void run(){
        Video.getVideos().forEach(Scoring::cacheHighestScore);
    }

}
