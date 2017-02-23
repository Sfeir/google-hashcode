package com.sfeir.hashcode;

/**
 * Created by bcornu on 2/23/17.
 */
public class Video {

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
}
