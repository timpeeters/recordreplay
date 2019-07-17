package be.xplore.recordreplayjetty;

import be.xplore.recordreplay.service.DefaultHttpServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class RecordReplayJetty {

    private static final int PORT = 9090;
    private final Server server = new Server(PORT);
    private final Class<DefaultHttpServlet> servletClass = DefaultHttpServlet.class;
    private final ServletHandler handler = new ServletHandler();

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public void start() {
        server.setHandler(handler);
        handler.addServletWithMapping(servletClass, "/*");
        try {
            server.start();
        } catch (Exception e) {
            throw new IllegalStateException("Jetty-server couldn't start", e);
        }
    }
}
