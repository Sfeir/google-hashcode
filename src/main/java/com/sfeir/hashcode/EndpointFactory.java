package com.sfeir.hashcode;

import com.sfeir.hashcode.model.Endpoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonyproum on 23/02/17.
 */
public class EndpointFactory {

    List<String> lines;
    private int numberOfEndpoints;

    public EndpointFactory(List<String> lines,int numberOfEndpoints){
        this.lines = lines;
        this.numberOfEndpoints = numberOfEndpoints;
    }

    private Endpoint createEndpoint(int id,String firstLine){
       return new Endpoint(id,Integer.valueOf(firstLine.split(" ")[0]));
    }

    List<Endpoint> createEndpoints(){
        List<Endpoint> endpoints = new ArrayList<>();
        int i = 0;
        while (i < numberOfEndpoints) {
            String line = lines.remove(0);
            Endpoint endpoint = createEndpoint(i++, line.split(" ")[0]);
            int y = 0;
            while (y < Integer.valueOf(line.split(" ")[1])) {
                String cacheDefinition = lines.remove(0);
                endpoint.addCache(Integer.valueOf(cacheDefinition.split(" ")[0]),Integer.valueOf(cacheDefinition.split(" ")[1]));
                y++;
            }
        }
        return endpoints;
    }

    List<String> getRemainsLines(){
        return lines;
    }

}
