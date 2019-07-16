package be.xplore.recordreplayjetty;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

public class RecordReplayJetty {
    private static final int PORT = 8080;
    private Server server;

    public void start() {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(PORT);
        server.setConnectors(new Connector[]{connector});
    }

}
