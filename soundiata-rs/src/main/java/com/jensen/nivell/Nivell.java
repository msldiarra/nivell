package com.jensen.nivell;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jensen.nivell.models.RepositoryModule;
import com.jensen.nivell.models.IConnectionManager;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.threadpool.GrizzlyExecutorService;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;

import java.io.IOException;

public class Nivell {

    private static Logger logger = Logger.getLogger(Nivell.class);
    private static Injector injector = Guice.createInjector(new RepositoryModule());

    public static void main(String[] args) throws IOException {

        HttpServer server = GrizzlyServer.startServer(injector);
        ThreadPoolConfig config = ThreadPoolConfig.defaultConfig().
                setPoolName("mypool").
                setCorePoolSize(10).
                setMaxPoolSize(10000);

        // reconfigure the thread pool
        NetworkListener listener = server.getListeners().iterator().next();
        logger.info(listener.toString());
        GrizzlyExecutorService threadPool = (GrizzlyExecutorService) listener.getTransport().getWorkerThreadPool();
        threadPool.reconfigure(config);
        logger.info(threadPool.getConfiguration());

        logger.info("Jersey app started with WADL available at "
                + GrizzlyServer.BASE_URI + "application.wadl" +
                "\nTry out Nivell\nHit enter to stop it...");
        System.in.read();
        IConnectionManager connectionManager = injector.getInstance(IConnectionManager.class);
        connectionManager.closeClientConnection();
        threadPool.shutdown();
        logger.info("treadPool is shutdown : " + threadPool.isShutdown());
        server.stop();
    }
}
