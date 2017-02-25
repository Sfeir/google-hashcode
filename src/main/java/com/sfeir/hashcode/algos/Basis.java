package com.sfeir.hashcode.algos;

import com.sfeir.hashcode.connection.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by bcornu on 2/23/17.
 */
public class Basis {

    public static void run(Connector connector) throws SQLException {
        Requestor r = new Requestor(connector);
        List<int[]> a;
        int nb_requests = r.getNbRemaining();
        while(!(a = r.getNextsBests()).isEmpty()){
            for (int[] b : a) {
                System.out.println("stored: "+b[1] +" in "+b[0]);
                r.store(b);
            }
            System.out.println("remain: "+r.getNbRemaining()+"/"+nb_requests+" requests");
            System.out.println("==============================");
        }
        System.out.println("NO MORE COMBINAISON FOUND");
    }
}