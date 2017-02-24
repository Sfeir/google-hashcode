package com.sfeir.hashcode.model;

/**
 * Created by bcornu on 2/24/17.
 */
public class Request {
    private Endpoint e;
    private Video v;
    private int nb;

    public Request(Endpoint e, Video v, int nb) {
        this.e = e;
        this.v = v;
        this.nb = nb;
    }

    public Endpoint getEndpoint() {
        return e;
    }

    public Video getVideo() {
        return v;
    }

    public int getNb() {
        return nb;
    }
}
