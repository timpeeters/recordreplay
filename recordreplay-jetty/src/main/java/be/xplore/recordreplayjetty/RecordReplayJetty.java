package be.xplore.recordreplayjetty;

import be.xplore.recordreplay.proxy.ProxyManager;
import be.xplore.recordreplay.service.RecordReplayHttpServlet;
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
    private final ProxyManager proxyManager;
    private boolean running;

    public RecordReplayJetty(int port, StubHandler stubHandler) {
        this.server = new Server();
        this.server.addConnector(newConnector(port));
        this.server.setHandler(getHandlerList(newContextHandler(stubHandler)));
        this.proxyManager = new ProxyManager();
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
        proxyManager.activate(server.getURI().getHost(), server.getURI().getPort());
        running = true;
    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public void stop() {
        try {
            if (running) {
                server.stop();
                tryJoinThreads();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Jetty-server couldn't stop", e);
        } finally {
            proxyManager.deActivate();
        }
        running = false;
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
