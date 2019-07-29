package be.xplore.recordreplay;

import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.jetty.RecordReplayJetty;
import be.xplore.recordreplay.proxy.ProxyManager;
import be.xplore.recordreplay.usecase.ForwardRequestUseCase;
import be.xplore.recordreplay.usecase.RecordReplayUseCase;
import be.xplore.recordreplay.usecase.RecordUseCase;
import be.xplore.recordreplay.usecase.ReplayUseCase;
import be.xplore.recordreplay.usecase.StubHandler;
import be.xplore.recordreplay.usecase.UseCase;

public abstract class RecordReplay {
    private final Configuration configuration;
    private RecordReplayJetty recordReplay;
    private final ProxyManager proxyManager;

    protected RecordReplay(Configuration configuration) {
        this.configuration = configuration;
        this.proxyManager = new ProxyManager();
    }

    public RecordReplay forward() {
        createRecordReplay(new ForwardRequestUseCase(configuration.client()));
        return this;
    }

    public RecordReplay record() {
        createRecordReplay(new RecordUseCase(configuration.repository(), configuration.client()));
        return this;
    }

    public RecordReplay replay() {
        createRecordReplay(new ReplayUseCase(configuration.repository(), configuration.matchers()));
        return this;
    }

    public RecordReplay recordReplay() {
        createRecordReplay(new RecordReplayUseCase(configuration.repository(), configuration.client(), configuration
                .matchers()));
        return this;
    }

    private void start() {
        stop();
        if (this.recordReplay != null) {
            this.recordReplay.start();
            proxyManager.activate(recordReplay.getHost(), recordReplay.getPort());
        }
    }

    private void stop() {
        if (this.recordReplay != null) {
            this.recordReplay.stop();
        }
        proxyManager.deActivate();
    }

    private void createRecordReplay(UseCase useCase) {
        stop();
        this.recordReplay = new RecordReplayJetty(configuration.port(), new StubHandler(useCase));
        start();
    }
}
