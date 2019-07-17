package be.xplore.recordreplayjetty;

import be.xplore.recordreplay.service.DefaultHttpServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class RecordReplayJetty {
    private final Server server;

    public RecordReplayJetty(int port, String path) {
        this.server = new Server(port);
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(DefaultHttpServlet.class, path);
        this.server.setHandler(handler);
    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public void start() throws InterruptedException {
        try {
            server.start();
        } catch (Exception e) {
            throw new IllegalStateException("Jetty-server couldn't start", e);
        } finally {
            server.join();
        }
    }
}
