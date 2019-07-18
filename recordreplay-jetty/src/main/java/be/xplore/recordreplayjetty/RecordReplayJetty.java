package be.xplore.recordreplayjetty;

import be.xplore.recordreplay.service.DefaultHttpServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class RecordReplayJetty {
    private final Server server;

    public RecordReplayJetty(int port) {
        this.server = new Server();
        this.server.addConnector(newConnector(port));
        ServletContextHandler context = newContextHandler();
        this.server.setHandler(getHandlerList(context));
    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public void start() {
        try {
            server.start();
            //  tryJoinThreads();
        } catch (Exception e) {
            throw new IllegalStateException("Jetty-server couldn't start", e);
        }
    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public void stop() {
        try {
            server.stop();
            tryJoinThreads();
        } catch (Exception e) {
            throw new IllegalStateException("Jetty-server couldn't stop", e);
        }
    }

    private void tryJoinThreads() {
        try {
            server.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Thread interrupted", e);
        }
    }

    private Connector newConnector(int port) {
        ServerConnector connector = new ServerConnector(this.server, new HttpConnectionFactory(getHttpConfiguration()));
        connector.setPort(port);
        connector.setIdleTimeout(2);
        return connector;
    }

    private HttpConfiguration getHttpConfiguration() {
        HttpConfiguration configuration = new HttpConfiguration();
        configuration.setSendServerVersion(false);
        configuration.setSendDateHeader(false);
        configuration.setSendXPoweredBy(false);
        return configuration;
    }

    private ServletContextHandler newContextHandler() {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/*");
        context.addServlet(DefaultHttpServlet.class, "/*");
        return context;
    }

    private HandlerList getHandlerList(ServletContextHandler context) {
        HandlerList handlers = new HandlerList();
        handlers.addHandler(context);
        return handlers;
    }
}
