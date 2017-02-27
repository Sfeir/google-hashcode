package com.sfeir.hashcode.algos;

import com.sfeir.hashcode.connection.*;
import com.sfeir.hashcode.model.Gain;

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
        StockDB s = new StockDB(connector);
        CacheDB c = new CacheDB(connector);
        int cacheSize = c.getCacheSize();
        List<Gain> gains;
        long startTime = System.currentTimeMillis();
        int max_optims = g.getNbRemaining();
        int nb_optims = max_optims;
        while(!(gains = g.getNextsBests()).isEmpty()){
            for (Gain gain : gains) {
                int availableSize = cacheSize - s.getUsedPlace(gain.getCacheId());
                if (availableSize >= gain.getSize()){
                    nb_optims -= g.store(gain);
                }
                nb_optims -= g.cleanLarges(gain.getCacheId(), availableSize);

            }

            long diff = (System.currentTimeMillis() - startTime);
            long current = max_optims - nb_optims;
            long eta = current == 0 ? 0 : (max_optims - current) * diff / current;
            String etaHms = current == 0 ? "N/A" :
                    String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(eta),
                            TimeUnit.MILLISECONDS.toMinutes(eta) % TimeUnit.HOURS.toMinutes(1),
                            TimeUnit.MILLISECONDS.toSeconds(eta) % TimeUnit.MINUTES.toSeconds(1));

            System.out.print("\rremain: "+nb_optims+"/"+max_optims+" optims ==> " + "ETA: "+etaHms+"....................");
        }
        System.out.println("\nNO MORE COMBINAISON FOUND");
    }
}