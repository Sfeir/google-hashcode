package com.sfeir.hashcode.algos;

import com.sfeir.hashcode.connection.*;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by bcornu on 2/23/17.
 */
public class Basis {

    public static void run(Connector connector) throws SQLException {
        GainDB g = new GainDB(connector);
        List<int[]> a;
        long startTime = System.currentTimeMillis();
        int max_optims = g.getNbRemaining();
        int nb_optims = g.getNbRemaining();
        while(!(a = g.getNextsBests()).isEmpty()){
            for (int[] b : a) {
                System.out.println("stored: "+b[1] +" in "+b[0]);
                nb_optims -= g.store(b);
            }
            System.out.println("remain: "+nb_optims+"/"+max_optims+" optims");

            long diff = (System.currentTimeMillis() - startTime);
            long current = max_optims - nb_optims;
            long eta = current == 0 ? 0 : (max_optims - current) * diff / current;
            String etaHms = current == 0 ? "N/A" :
                    String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(eta),
                            TimeUnit.MILLISECONDS.toMinutes(eta) % TimeUnit.HOURS.toMinutes(1),
                            TimeUnit.MILLISECONDS.toSeconds(eta) % TimeUnit.MINUTES.toSeconds(1));

            System.out.println("ETA: "+etaHms);
            System.out.println("==============================");
        }
        System.out.println("NO MORE COMBINAISON FOUND");
    }
}