package com.sfeir.hashcode.model;

import java.util.HashMap;
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
