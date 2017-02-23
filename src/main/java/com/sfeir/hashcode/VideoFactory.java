package com.sfeir.hashcode;

import com.sfeir.hashcode.model.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sfeir-Lille on 23/02/17.
 */
public class VideoFactory {

    private final String init;

    public VideoFactory(String init){
        this.init = init;
    }

    public List<Video> getVideos() {
        List<Video> videos = new ArrayList<>();
        int i=0;
        for (String video : init.split(" ")) {
            videos.add(new Video(i++,Integer.valueOf(video)));
        }
        return videos;
    }

}
