package com.sfeir.hashcode.algos;

import com.sfeir.hashcode.model.Cache;
import com.sfeir.hashcode.model.Video;

/**
 * Created by bcornu on 2/23/17.
 */
public class Basis {




    public static void run() {
        for (Cache c: Cache.getCaches()) {
            if (!c.getEndpoints().isEmpty()) {
                Video v = c.getEndpoints().get(0).getRequests().keySet().iterator().next();
                c.addVideo(v);
            }
        }
    }
}
