package com.jensen.nivell.resources;

import com.couchbase.client.CouchbaseClient;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jensen.nivell.GrizzlyServer;
import com.jensen.nivell.models.IConnectionManager;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.glassfish.grizzly.http.server.HttpServer;

import java.io.IOException;

import static org.mockito.Mockito.mock;

public class ResourceITTemplate {

    protected static final String SERVER_LAUNCHING_MESSAGE = "Jersey app started with WADL available at "
            + "%sapplication.wadl\nTry out %nivell\nHit enter to stop it...";
    protected static HttpServer server;
    protected static CouchbaseClient client;
    protected static Injector injector;
    protected static IConnectionManager connectionManager;
    protected static WebResource webResource;
    protected static String testDate;

    protected static void startService() throws IOException {

        injector = Guice.createInjector(new RepositoryTestModule());
        server = GrizzlyServer.startServer(injector);
        connectionManager = injector.getInstance(IConnectionManager.class);
        System.out.println(String.format(SERVER_LAUNCHING_MESSAGE,
                GrizzlyServer.BASE_URI, GrizzlyServer.BASE_URI));
    }

    protected static WebResource webResource(){
        Client WebResourceClient = Client.create();
        return WebResourceClient.resource(GrizzlyServer.BASE_URI);
    }
}
