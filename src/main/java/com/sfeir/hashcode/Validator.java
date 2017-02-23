package com.sfeir.hashcode;

import com.sfeir.hashcode.model.Cache;
import com.sfeir.hashcode.model.Video;

/**
 * Created by Sfeir-Lille on 23/02/17.
 */
public class Validator {

    public static boolean canPutInCache(Video video, Cache cache){
        return cache.getRemainingSpace()>video.getSize();
    }

}
