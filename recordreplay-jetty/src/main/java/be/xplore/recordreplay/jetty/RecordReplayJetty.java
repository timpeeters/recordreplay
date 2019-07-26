package be.xplore.recordreplay.jetty;

import be.xplore.recordreplay.servlet.RecordReplayHttpServlet;
import be.xplore.recordreplay.usecase.StubHandler;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class RecordReplayJetty {
    private final Server server;
    private boolean running;

    public RecordReplayJetty(int port, StubHandler stubHandler) {
        this.server = new Server();
        this.server.addConnector(newConnector(port));
        this.server.setHandler(getHandlerList(newContextHandler(stubHandler)));
    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public void start() {
        try {
            if (!running) {
                server.start();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Jetty-server couldn't start", e);
        }
        running = true;
    }

    public void stop() {
        tryStop();
        tryJoinThreads();
    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    private void tryStop() {
        try {
            if (running) {
                server.stop();
                running = false;
            }
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

    public String getHost() {
        return server.getURI().getHost();
    }

    public int getPort() {
        return server.getURI().getPort();
    }

    private Connector newConnector(int port) {
        ServerConnector connector = new ServerConnector(this.server, new HttpConnectionFactory(getHttpConfiguration()));
        connector.setPort(port);
        connector.setIdleTimeout(20_000);
        connector.setStopTimeout(30_000);
        return connector;
    }

    private HttpConfiguration getHttpConfiguration() {
        HttpConfiguration configuration = new HttpConfiguration();
        configuration.setSendServerVersion(false);
        configuration.setSendDateHeader(false);
        configuration.setSendXPoweredBy(false);
        return configuration;
    }

    private ServletContextHandler newContextHandler(StubHandler stubHandler) {
        ServletContextHandler context = new ServletContextHandler();
        ServletHolder servlet = new ServletHolder(new RecordReplayHttpServlet(stubHandler));
        context.addServlet(servlet, "/*");
        return context;
    }

    private HandlerList getHandlerList(ServletContextHandler context) {
        HandlerList handlers = new HandlerList();
        handlers.addHandler(context);
        return handlers;
    }
}
