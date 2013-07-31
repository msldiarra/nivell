package com.jensen.nivell;

import com.google.inject.Injector;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory;
import org.apache.log4j.Logger;

import javax.ws.rs.core.UriBuilder;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

public class GrizzlyServer {
    private static Logger logger = Logger.getLogger(GrizzlyServer.class);

    private static URI getBaseURI() {

        Properties properties = new Properties();

        try {

            properties.load(GrizzlyServer.class.getClassLoader().getResourceAsStream("grizzly.properties"));
            String host = properties.getProperty("server.host");
            String port = properties.getProperty("server.port");

            return UriBuilder.fromUri("http://" + host + "/").port(Integer.parseInt(port)).build();

       } catch (IOException e) {

            logger.error("Exception Occurred" + e.getMessage());
            return UriBuilder.fromUri("http://localhost/").port(9998).build();
        }


    }

    public static final URI BASE_URI = getBaseURI();

    public static org.glassfish.grizzly.http.server.HttpServer startServer(Injector injector) throws IOException {

        logger.info("Starting grizzly...");

        ResourceConfig rc = new PackagesResourceConfig("com.jensen.nivell.resources");
        return GrizzlyServerFactory.createHttpServer(BASE_URI, rc, new GuiceComponentProviderFactory(rc, injector));
    }
}
