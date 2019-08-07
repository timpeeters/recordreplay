package be.xplore.recordreplay.http;

import be.xplore.recordreplay.usecase.StubHandler;

public interface HttpServer {

    HttpServer init(int port, StubHandler stubHandler);

    void start();

    void stop();

    boolean isRunning();

    String getHost();

    int getPort();

}
