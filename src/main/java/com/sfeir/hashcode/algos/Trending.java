package com.sfeir.hashcode.algos;

import com.sfeir.hashcode.connection.CacheDB;
import com.sfeir.hashcode.connection.Connector;
import com.sfeir.hashcode.connection.VideoDB;
import com.sfeir.hashcode.model.Cache;
import com.sfeir.hashcode.model.Video;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bcornu on 2/23/17.
 */
public class Trending {

    public static List<String> run(Connector connector) throws SQLException {
        VideoDB vdb = new VideoDB(connector);
        CacheDB cdb = new CacheDB(connector);
        vdb.initWorkTable();
        List<Video> videos = vdb.getAll();
        int cacheSize = cdb.getCacheSize();
        int cacheNumber = cdb.getCacheCount();
        List<Cache> caches = new ArrayList<>();
        for (int i = 0; i < cacheNumber; i++) {
            caches.add(new Cache(i,cacheSize));
        }
        for (Cache cache : caches){
            List<Video> subset = getSize(cacheSize, videos, null);
            for (Video video : subset) {
                cache.addVideo(video);
                videos.removeAll(subset);
            }
            System.out.println("\nfilled: "+cache.getId());
        }
        for (Video video : videos) {
            System.out.println("remain "+video);
        }

//        for (Video video : videos) {
//            boolean added = false;
//            for (Cache cache : caches) {
//                if (cache.getRemainingSpace()>=video.getSize()){
//                    added=true;
//                    cache.addVideo(video);
//                    break;
//                }
//            }
//            if (!added)
//                System.out.println("cannot add "+video);
//        }
        System.out.println("DONE");

        List<String> res = new ArrayList<>();
        System.out.println("set output");
        res.add(cacheNumber+"");
        for (Cache c : caches) {
            res.add(c.getOutput());
            //System.out.println(c.getOutput());
        }
        return res;
    }

    private static List<Video> getSize(int target, List<Video> addable, List<Video> added){
        if(added==null)
            added = new ArrayList<>();
        System.out.print("\r"+target+" "+addable.size()+" "+added.size() );
        if(target==0)
            return added;
        for (Video video : addable) {
            int current = target;
            List<Video> subsetAddable = new ArrayList<>(addable);
            List<Video> subsetAdded = new ArrayList<>(added);
            if(video.getSize()<=current){
                subsetAddable.remove(video);
                subsetAdded.add(video);
                current-=video.getSize();
                try {
                    return getSize(current, subsetAddable, subsetAdded);
                }catch (RuntimeException re){
                    continue;
                }
            }
        }
        throw new RuntimeException("not fitted "+addable);
    }
}