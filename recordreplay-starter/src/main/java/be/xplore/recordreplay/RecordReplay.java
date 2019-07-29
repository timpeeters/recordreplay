package be.xplore.recordreplay;

import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.jetty.RecordReplayJetty;
import be.xplore.recordreplay.proxy.ProxyManager;
import be.xplore.recordreplay.usecase.ForwardRequestUseCase;
import be.xplore.recordreplay.usecase.RecordReplayUseCase;
import be.xplore.recordreplay.usecase.RecordUseCase;
import be.xplore.recordreplay.usecase.ReplayUseCase;
import be.xplore.recordreplay.usecase.StubHandler;

public class RecordReplay {
    private final Configuration configuration;
    private RecordReplayJetty recordReplayServer;
    private final ProxyManager proxyManager;

    public RecordReplay(Configuration configuration) {
        this.configuration = configuration;
        this.recordReplayServer = new RecordReplayJetty(configuration.port(),
                new StubHandler(new RecordReplayUseCase(
                        configuration.repository(), configuration.client(), configuration.matchers()
                )));
        this.proxyManager = new ProxyManager();
    }

    public RecordReplay(Configuration configuration, StubHandler stubHandler) {
        this.configuration = configuration;
        this.recordReplayServer = new RecordReplayJetty(configuration.port(), stubHandler);
        this.proxyManager = new ProxyManager();
    }

    public RecordReplay forward() {
        createRecordReplay(new StubHandler(new ForwardRequestUseCase(configuration.client())));
        return this;
    }

    public RecordReplay record() {
        createRecordReplay(new StubHandler(new RecordUseCase(configuration.repository(), configuration.client())));
        return this;
    }

    public RecordReplay replay() {
        createRecordReplay(new StubHandler(new ReplayUseCase(configuration.repository(), configuration.matchers())));
        return this;
    }

    public RecordReplay recordReplay() {
        createRecordReplay(new StubHandler(new RecordReplayUseCase(configuration.repository(), configuration
                .client(), configuration
                .matchers())));
        return this;
    }

    public void start() {
        stop();
        if (this.recordReplayServer != null) {
            this.recordReplayServer.start();
            proxyManager.activate(recordReplayServer.getHost(), recordReplayServer.getPort());
        }
    }

    public void stop() {
        proxyManager.deActivate();
        if (this.recordReplayServer != null) {
            this.recordReplayServer.stop();
        }
    }

    private void createRecordReplay(StubHandler stubHandler) {
        stop();
        this.recordReplayServer = new RecordReplayJetty(configuration.port(), stubHandler);
        start();
    }
}
