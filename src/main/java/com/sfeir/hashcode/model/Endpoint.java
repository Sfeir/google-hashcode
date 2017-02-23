package com.sfeir.hashcode.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bcornu on 2/23/17.
 */
public class Endpoint {

    private int id;
    private int datacenterLatency;
    private Map<Cache, Integer> caches = new HashMap<>();

    public Endpoint(int id, int datacenterLatency) {
        this.id = id;
        this.datacenterLatency = datacenterLatency;
    }

    public int getDatacenterLatency() {
        return datacenterLatency;
    }

    public int getId() {
        return id;
    }

    public Map<Cache, Integer> getCaches() {
        return caches;
    }
}
