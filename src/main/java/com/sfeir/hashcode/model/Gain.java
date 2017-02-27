package com.sfeir.hashcode.model;

/**
 * Created by bcornu on 2/27/17.
 */
public class Gain {

    private int cid;
    private int vid;
    private int size;

    public Gain(int cid, int vid, int size) {
        this.cid = cid;
        this.vid = vid;
        this.size = size;
    }

    public int getCacheId() {
        return cid;
    }

    public int getVideoID() {
        return vid;
    }

    public int getSize() {
        return size;
    }
}
