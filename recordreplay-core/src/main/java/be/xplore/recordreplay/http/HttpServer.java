package be.xplore.recordreplay.http;

import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.usecase.StubHandler;

public interface HttpServer {

    void start(Configuration config, StubHandler stubHandler);

    void stop();

    boolean isRunning();

    String getHost();

    int getPort();

}
