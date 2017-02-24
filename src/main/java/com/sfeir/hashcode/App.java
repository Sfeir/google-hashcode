package com.sfeir.hashcode;

import com.sfeir.hashcode.algos.Basis;
import com.sfeir.hashcode.connection.*;
import com.sfeir.hashcode.model.Cache;
import com.sfeir.hashcode.model.Endpoint;
import com.sfeir.hashcode.model.Video;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 */
public class App {

    private static void clean_db(Connector connector) throws Exception {
        CacheDB cdb = new CacheDB(connector);
        cdb.deleteTable();
        cdb.createTable();

        VideoDB vdb = new VideoDB(connector);
        vdb.deleteTable();
        vdb.createTable();

        EndpointDB edb = new EndpointDB(connector);
        edb.deleteTable();
        edb.createTable();

        LatenceDB ldb = new LatenceDB(connector);
        ldb.deleteTable();
        ldb.createTable();

        RequestDB rdb = new RequestDB(connector);
        rdb.deleteTable();
        rdb.createTable();
    }

    private static void fill_db(Connector connector, String inputName) throws Exception {
        CacheDB cdb = new CacheDB(connector);
        VideoDB vdb = new VideoDB(connector);
        EndpointDB edb = new EndpointDB(connector);
        LatenceDB ldb = new LatenceDB(connector);
        RequestDB rdb = new RequestDB(connector);

        Endpoint.getEndpoints().clear();
        Cache.getCaches().clear();
        Video.getVideos().clear();

        System.out.println("PARSING FILE : " + inputName);

        // READ input
        Path inputPath = Paths.get(App.class.getResource("/" + inputName).toURI());
        List<String> lines = Files.readAllLines(inputPath, StandardCharsets.UTF_8);
        System.out.println("read Init");
        Init init = new Init(lines.remove(0));
        System.out.println("set caches");
        for (int i = 0; i < init.numberOfCaches(); i++) {
            System.out.println(i+"/"+init.numberOfCaches());
            Cache c = new Cache(i, init.cacheSize());
            Cache.getCaches().add(c);
            cdb.insert(c);
        }
        VideoFactory videoFactory = new VideoFactory(lines.remove(0));
        System.out.println("read videos");
        List<Video> videos = videoFactory.getVideos();
        System.out.println("set videos");
        int i =0;
        for (Video v : videos) {
            System.out.println(i+++"/"+videos.size());
            Video.getVideos().add(v);
            vdb.insert(v);
        }
        System.out.println("read endpoints");
        EndpointFactory endpointFactory = new EndpointFactory(lines, init.numberOfEndpoints());
        List<Endpoint> endpoints = endpointFactory.createEndpoints();
        System.out.println("set endpoints");
        i=0;
        for (Endpoint e : endpoints) {
            System.out.println(i+++"/"+endpoints.size());
            Endpoint.getEndpoints().add(e);
            edb.insert(e);
            for (Cache c : e.getCaches().keySet()) {
                ldb.insert(e.getId(), c.getId(), e.getCaches().get(c));
            }

        }
        System.out.println("read/set requests");
        for (String line : endpointFactory.getRemainsLines()) {
            System.out.println(i+++"/"+endpointFactory.getRemainsLines().size());
            String[] tmp = line.split(" ");
            int videoId = Integer.valueOf(tmp[0]);
            int endpointId = Integer.valueOf(tmp[1]);
            int nb = Integer.valueOf(tmp[2]);
            Endpoint.getEndpoints().get(endpointId).addRequest(videoId, nb);
            rdb.addRequest(endpointId, videoId, nb);
        }

    }

    private static String FOLDER_OUTPUT = "/tmp";

    public static void main(String[] args) throws Exception {
        boolean init =true;

        List<String> inputs = Arrays.asList("me_at_the_zoo.in", "trending_today.in", "videos_worth_spreading.in", "kittens.in");
        //List<String> inputs = Arrays.asList("me_at_the_zoo.in", "trending_today.in", "videos_worth_spreading.in");

        for (String inputName : inputs) {
            Connector connector = new Connector("jdbc:postgresql://localhost:5432/hashcode_"+inputName.split("\\.")[0], "hashcode", "password");

            if (init) {
                clean_db(connector);
                fill_db(connector, inputName);
                continue;
            }

            System.out.println("doYourJob");
            doYourJob(connector);

            System.out.println("set output");
            List<String> res = new ArrayList<>();
            res.add("" + Cache.getCaches().size());
            for (Cache c : Cache.getCaches()) {
                res.add(c.getOutput());
                System.out.println(c.getOutput());
            }
            Files.write(new File(FOLDER_OUTPUT, inputName).toPath(), res);
        }
    }

    private static void doYourJob(Connector connector) {
        Basis.run(connector);
    }
}
