package com.sfeir.hashcode.model;

/**
 * Created by bcornu on 2/23/17.
 */
public class Cache {

    private int id;
    private int size;

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
}
