package com.sfeir.hashcode.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bcornu on 2/23/17.
 */
public class Video {

    private static final List<Video> videos = new ArrayList<>();

    public static List<Video> getVideos() {
        return videos;
    }

    private int id;
    private int size;

    public Video(int id, int size) {
        this.id = id;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int getId() {
        return id;
    }

    public static Video getVideo(int videoId) {
        for (Video v : videos) {
            if (v.getId() == videoId)
                return v;
        }
        throw new IllegalArgumentException("not existing endpoint id: " + videoId);
    }

    @Override
    public String toString() {
        return "video: "+id+" : "+size;
    }
}
