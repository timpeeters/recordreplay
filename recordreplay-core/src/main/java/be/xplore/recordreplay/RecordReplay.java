package be.xplore.recordreplay;

import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.http.HttpServer;
import be.xplore.recordreplay.proxy.ProxyManager;
import be.xplore.recordreplay.usecase.ForwardRequestUseCase;
import be.xplore.recordreplay.usecase.RecordReplayUseCase;
import be.xplore.recordreplay.usecase.RecordUseCase;
import be.xplore.recordreplay.usecase.ReplayUseCase;
import be.xplore.recordreplay.usecase.StubHandler;
import be.xplore.recordreplay.util.ClassLocator;

public final class RecordReplay {
    private final Configuration configuration;
    private  StubHandler stubHandler;
    private HttpServer recordReplayServer;
    private final ProxyManager proxyManager;

    public RecordReplay(Configuration configuration) {
        this.configuration = configuration;
        this.recordReplayServer = new ClassLocator<>(HttpServer.class).load();
        this.proxyManager = new ProxyManager();
    }

    public RecordReplay forward() {
        createRecordReplay(new StubHandler(new ForwardRequestUseCase(configuration.client(), configuration
                .target())));
        return this;
    }

    public RecordReplay record() {
        createRecordReplay(new StubHandler(
                new RecordUseCase(configuration.repository(), configuration.client())));
        return this;
    }

    public RecordReplay replay() {
        createRecordReplay(new StubHandler(
                new ReplayUseCase(
                        configuration.repository(),
                        configuration.matchers())));
        return this;
    }

    public RecordReplay recordReplay() {
        createRecordReplay(new StubHandler(new RecordReplayUseCase(configuration)));
        return this;
    }

    public void start() {
        if (!recordReplayServer.isRunning()) {
            configureProxy();
        }
        this.recordReplayServer.start(configuration, stubHandler);
    }

    public void stop() {
        proxyManager.deActivate();
        this.recordReplayServer.stop();
    }

    private void createRecordReplay(StubHandler stubHandler) {
        stop();
        this.stubHandler = stubHandler;
        this.recordReplayServer = new ClassLocator<>(HttpServer.class).load();
        start();
    }

    private void configureProxy() {
        if (configuration.target() == null) {
            this.proxyManager.activate(configuration.host(), configuration.port());
        } else {
            this.proxyManager.deActivate();
        }
    }
}
