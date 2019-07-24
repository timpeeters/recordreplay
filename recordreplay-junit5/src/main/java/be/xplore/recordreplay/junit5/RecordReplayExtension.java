package be.xplore.recordreplay.junit5;

import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.usecase.ForwardRequestUseCase;
import be.xplore.recordreplay.usecase.RecordReplayUseCase;
import be.xplore.recordreplay.usecase.RecordUseCase;
import be.xplore.recordreplay.usecase.ReplayUseCase;
import be.xplore.recordreplay.usecase.StubHandler;
import be.xplore.recordreplayjetty.RecordReplayJetty;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static be.xplore.fakes.util.Assert.notNull;

public class RecordReplayExtension implements BeforeAllCallback, AfterAllCallback {

    private final RecordReplayJetty recordReplayJetty;
    private final Configuration configuration;

    public RecordReplayExtension(Configuration configuration) {
        this.configuration = notNull(configuration);
        recordReplayJetty = new RecordReplayJetty(configuration.port());
    }



    @Override
    public void beforeAll(ExtensionContext context) {
        recordReplayJetty.start();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        recordReplayJetty.stop();
    }

    public RecordReplayExtension record() {
        StubHandler.setCurrent(new StubHandler(new RecordUseCase(configuration.repository(), configuration.client())));
        return this;
    }

    public RecordReplayExtension forward() {
        StubHandler.setCurrent(new StubHandler(new ForwardRequestUseCase(configuration.client())));
        return this;
    }

    public RecordReplayExtension replay() {
        StubHandler.setCurrent(new StubHandler(new ReplayUseCase(configuration.repository(), configuration.matchers())));
        return this;
    }

    public RecordReplayExtension recordReplay() {
        StubHandler.setCurrent(new StubHandler(new RecordReplayUseCase(
                new RecordUseCase(configuration.repository(), configuration.client()),
                new ReplayUseCase(configuration.repository(), configuration.matchers())
        )));
        return this;
    }
}
