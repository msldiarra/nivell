package com.jensen.nivell.models;


import com.couchbase.client.CouchbaseClient;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
public class ClientConnectionManager implements IConnectionManager {

    private CouchbaseClient client;
    private static Logger logger = Logger.getLogger(CouchbaseClient.class);

    ClientConnectionManager(){

        ArrayList<URI> nodes = getNodesUris();

        CouchbaseClient client = null;

        try {
            client = new CouchbaseClient(nodes, "default", "");
            logger.info("Connected to node " + nodes.get(0) + " on bucket default");
        } catch (IOException ex) {
            logger.error("Couldn't connect to database", ex);

            System.exit(1);
        }

        this.client = client;
    }

    public CouchbaseClient getClientInstance(){
        return client;
    }

    public void closeClientConnection(){
        this.client.shutdown();
    }

    private ArrayList<URI> getNodesUris() {
        ArrayList<URI> nodes = new ArrayList<URI>();
        nodes.add(URI.create("http://127.0.0.1:8091/pools"));
        return nodes;
    }


}
