package be.xplore.recordreplay.junit5;

import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.proxy.ProxyManager;
import be.xplore.recordreplay.usecase.ForwardRequestUseCase;
import be.xplore.recordreplay.usecase.RecordReplayUseCase;
import be.xplore.recordreplay.usecase.RecordUseCase;
import be.xplore.recordreplay.usecase.ReplayUseCase;
import be.xplore.recordreplay.usecase.StubHandler;
import be.xplore.recordreplay.usecase.UseCase;
import be.xplore.recordreplay.jetty.RecordReplayJetty;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static be.xplore.fakes.util.Assert.notNull;

public class RecordReplayExtension implements BeforeAllCallback, AfterAllCallback {

    private RecordReplayJetty recordReplay;
    private final Configuration configuration;
    private final ProxyManager proxyManager;

    public RecordReplayExtension(Configuration configuration) {
        this.configuration = notNull(configuration);
        this.recordReplay = new RecordReplayJetty(configuration
                .port(), new StubHandler(new RecordReplayUseCase(configuration.repository(), configuration
                .client(), configuration.matchers())));
        this.proxyManager = new ProxyManager();
    }


    public RecordReplayExtension forward() {
        createRecordReplay(new ForwardRequestUseCase(configuration.client()));
        start();
        return this;
    }

    public RecordReplayExtension record() {
        createRecordReplay(new RecordUseCase(configuration.repository(), configuration.client()));
        start();
        return this;
    }

    public RecordReplayExtension replay() {
        createRecordReplay(new ReplayUseCase(configuration.repository(), configuration.matchers()));
        start();
        return this;
    }

    public RecordReplayExtension recordReplay() {
        createRecordReplay(new RecordReplayUseCase(configuration.repository(), configuration.client(), configuration
                .matchers()));
        start();
        return this;
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        recordReplay.start();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        recordReplay.stop();
    }


    private void stop() {
        if (this.recordReplay != null) {
            this.recordReplay.stop();
        }
        proxyManager.deActivate();
    }

    private void start() {
        stop();
        if (this.recordReplay != null) {
            this.recordReplay.start();
            proxyManager.activate(recordReplay.getHost(), recordReplay.getPort());
        }
    }

    private void createRecordReplay(UseCase useCase) {
        stop();
        this.recordReplay = new RecordReplayJetty(configuration.port(), new StubHandler(useCase));
    }
}
