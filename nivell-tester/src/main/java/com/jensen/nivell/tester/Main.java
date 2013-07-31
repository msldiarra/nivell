package com.jensen.nivell.tester;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.couchbase.client.ClusterManager;
import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.clustermanager.FlushResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class Main {

    private static Logger logger = Logger.getLogger(Main.class);
    protected static WebResource webResource;
    private static URI BASE_URI = UriBuilder.fromUri("http://localhost/").port(9998).build();
    private static int currentNumber = 500000;

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        logger.info("Starting tests...");

       webResource = webResource();
        long start = System.nanoTime();
        while (currentNumber < 510000) {

            ClientResponse addResponse = webResource.path("tank/add/"+ currentNumber +"/Sokorodji/5000/8.00/-89.23/-179.89")
                    .accept(MediaType.TEXT_PLAIN)
                    .post(ClientResponse.class);

            logger.info("Adding tank with identifier ("+ currentNumber + ") response status : " + addResponse.getClientResponseStatus());

            ClientResponse getResponse = webResource.path("tank/get/"+ currentNumber).get(ClientResponse.class);

            logger.info("Getting tank with identifier ("+ currentNumber + ") response status : " + getResponse.getClientResponseStatus());
            logger.info("Tank value : " + getResponse.getEntity(String.class));

            currentNumber++;
        }
        long elapsedTime = System.nanoTime() - start;
        logger.info("elapsed time = " + elapsedTime);
        List<URI> uris = new ArrayList<URI>();
        uris.add(URI.create("http://localhost:8091/pools"));
        /*ClusterManager manager = new ClusterManager(uris, "Administrator", "jensen@123");
        FlushResponse response = manager.flushBucket("default");*/
        CouchbaseClient client = new CouchbaseClient(uris, "default", "");
        //Boolean response = client.flush().get();
/*
        logger.info("Flush responded : "+response.toString());
*/
        logger.info("Hit enter to stop");
        System.in.read();
        System.exit(0);



    }

    protected static WebResource webResource(){
        Client WebResourceClient = Client.create();
        return WebResourceClient.resource(BASE_URI);
    }
}
