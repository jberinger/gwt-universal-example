package net.sprd.gwt;

import java.io.IOException;
import java.net.URI;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.http.server.accesslog.AccessLogAppender;
import org.glassfish.grizzly.http.server.accesslog.AccessLogFormat;
import org.glassfish.grizzly.http.server.accesslog.AccessLogProbe;
import org.glassfish.grizzly.http.server.accesslog.ApacheLogFormat;
import org.glassfish.grizzly.http.server.accesslog.StreamAppender;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import net.sprd.gwt.shared.version.GwtuTestVersion;

/**
 * Main class.
 *
 */
public class GwtUniversalTestMain {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://127.0.0.1:4242/page/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.example package
        final ResourceConfig rc = new ResourceConfig().packages("net.sprd.gwt.server.rest").register(JacksonFeature.class);
        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc, false);
        
        String resourceBase = "./target/gwt-universal-test-"+GwtuTestVersion.VERSION+"/gwtutest";
        StaticHttpHandler staticHttpHandler = new StaticHttpHandler(resourceBase);
        server.getServerConfiguration().addHttpHandler(staticHttpHandler, "/gwtutest");
        
        enableAccessLog(server);
        
        ensureLogging();
        
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return server;
    }

    public static void ensureLogging() {
        Logger l = Logger.getLogger("org.glassfish.grizzly.http.server.HttpHandler");
        l.setLevel(Level.FINE);
        l.setUseParentHandlers(false);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        l.addHandler(ch);
    }
    
    public static void enableAccessLog(HttpServer httpServer) {
        AccessLogAppender appender = new StreamAppender(System.out);
        AccessLogFormat format = ApacheLogFormat.COMBINED;
        int statusThreshold = AccessLogProbe.DEFAULT_STATUS_THRESHOLD;
        AccessLogProbe alp = new AccessLogProbe(appender, format, statusThreshold);
        ServerConfiguration sc = httpServer.getServerConfiguration();
        sc.getMonitoringConfig().getWebServerConfig().addProbes(alp);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        startServer();
        
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
    }
}

