package com.sfeir.hashcode.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bcornu on 2/23/17.
 */
public class Video {

    private static final Map<Integer, Video> videos = new HashMap<>();

    public static Map<Integer, Video> getVideos() {
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
        return null;
    }
}
