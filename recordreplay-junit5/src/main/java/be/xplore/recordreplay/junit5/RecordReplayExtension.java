package be.xplore.recordreplay.junit5;

import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.usecase.StubHandler;
import be.xplore.recordreplayjetty.RecordReplayJetty;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class RecordReplayExtension implements BeforeAllCallback, AfterAllCallback {

    private final RecordReplayJetty recordReplayJetty;

    public RecordReplayExtension(Configuration configuration) {
        StubHandler.setCurrent(new StubHandler(configuration.useCase()));
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
}
