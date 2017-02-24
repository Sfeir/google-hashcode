package com.sfeir.hashcode.algos;

import com.sfeir.hashcode.connection.*;

import java.sql.SQLException;

/**
 * Created by bcornu on 2/23/17.
 */
public class Basis {

    public static void run(Connector connector) throws SQLException {
        Requestor r = new Requestor(connector);
        int[] a;
        int nb_requests = r.getNbRemaining();
        while((a = r.getNextBest()) != null){
            System.out.println("stored: "+a[1] +" in "+a[0]);
            r.store(a);
            System.out.println("remain: "+r.getNbRemaining()+"/"+nb_requests+" requests");
            System.out.println("==============================");
        }
    }
}