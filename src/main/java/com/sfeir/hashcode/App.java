package com.sfeir.hashcode;

import com.sfeir.hashcode.algos.Basis;
import com.sfeir.hashcode.connection.*;
import com.sfeir.hashcode.model.Cache;
import com.sfeir.hashcode.model.Endpoint;
import com.sfeir.hashcode.model.Video;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    private static void createWorkDB(Connector connector) throws Exception {
        RequestDB rdb = new RequestDB(connector);
        rdb.initWorkTable();

        StockDB sdb = new StockDB(connector);
        sdb.deleteTable();
        sdb.createTable();
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
        try (BufferedReader br = new BufferedReader(new FileReader(new File(App.class.getResource("/" + inputName).toURI())))) {
            String line;
            // process the line.
            System.out.println("read Init");
            Init init = new Init(br.readLine());
            System.out.println("set caches");
            for (int i = 0; i < init.numberOfCaches(); i++) {
                cdb.insert(new Cache(i, init.cacheSize()));
                System.out.print("\r"+(i+1)+"/"+init.numberOfCaches());
            }
            int i=0;
            String[] videos = br.readLine().split(" ");
            System.out.println("\nset videos");
            for (String s : videos) {
                vdb.insert(new Video(i++,Integer.valueOf(s)));
                System.out.print("\r"+i+"/"+videos.length);
            }
            System.out.println("\nset endpoints");
            for (int j = 0; j < init.numberOfEndpoints(); j++) {
                String[] s = br.readLine().split(" ");
                Endpoint e = new Endpoint(j, Integer.parseInt(s[0]));
                edb.insert(e);
                for (int k = 0; k < Integer.parseInt(s[1]); k++) {
                    String[] s2 = br.readLine().split(" ");
                    ldb.insert(e.getId(), Integer.parseInt(s2[0]), Integer.parseInt(s2[1]));
                }
                System.out.print("\r"+(j+1)+"/"+init.numberOfEndpoints());
            }
            i=0;
            System.out.println("\nread/set requests");
            while ((line = br.readLine()) != null) {
                String[] s = line.split(" ");
                int videoId = Integer.valueOf(s[0]);
                int endpointId = Integer.valueOf(s[1]);
                int nb = Integer.valueOf(s[2]);
                rdb.addRequest(endpointId, videoId, nb);
                System.out.print("\r"+ ++i+"/"+init.numberOfRequestDescription());
            }
            System.out.println("\nDONE");
        }
    }

    private static String FOLDER_OUTPUT = "/tmp";

    public static void main(String[] args) throws Exception {
        boolean init =false;

        List<String> inputs = Arrays.asList("videos_worth_spreading.in");
        //List<String> inputs = Arrays.asList("me_at_the_zoo.in", "trending_today.in", "videos_worth_spreading.in");

        for (String inputName : inputs) {
            System.out.println(inputName);
            Connector connector = new Connector("jdbc:postgresql://localhost:5432/hashcode_"+inputName.split("\\.")[0], "hashcode", "password");

            if (init) {
                clean_db(connector);
                fill_db(connector, inputName);
                continue;
            }
            //createWorkDB(connector);

            doYourJob(connector);

            Requestor r = new Requestor(connector);
            Map<Integer, List<Integer>> cached = r.getStock();
            String[] res = new String[cached.keySet().size()+1];
            res[0] = ""+cached.keySet().size();
            int i = 0;
            for (Integer c : cached.keySet()) {
                res[++i] = c+"";
                for (Integer v : cached.get(c)) {
                    res[i] += " "+v;
                }
            }
//            System.out.println("set output");
//            for (Cache c : Cache.getCaches()) {
//                res.add(c.getOutput());
//                System.out.println(c.getOutput());
//            }
            Files.write(new File(FOLDER_OUTPUT, inputName).toPath(), Arrays.asList(res));
        }
    }

    private static void doYourJob(Connector connector) throws SQLException {
        Basis.run(connector);
    }
}
