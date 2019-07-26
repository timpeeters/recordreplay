package be.xplore.recordreplay.junit5;

import be.xplore.recordreplay.RecordReplay;
import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.jetty.RecordReplayJetty;
import be.xplore.recordreplay.proxy.ProxyManager;
import be.xplore.recordreplay.usecase.RecordReplayUseCase;
import be.xplore.recordreplay.usecase.StubHandler;
import be.xplore.recordreplay.usecase.UseCase;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class RecordReplayExtension extends RecordReplay<RecordReplayExtension> implements BeforeAllCallback,
        AfterAllCallback {

    private RecordReplayJetty recordReplay;
    private final ProxyManager proxyManager;

    public RecordReplayExtension(Configuration configuration) {
        super(configuration);
        this.recordReplay = new RecordReplayJetty(configuration.port(),
                new StubHandler(new RecordReplayUseCase(configuration.repository(),
                        configuration.client(), configuration.matchers())
                )
        );
        this.proxyManager = new ProxyManager();
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
        proxyManager.deActivate();
        recordReplay.stop();
    }

    private void start() {
        stop();
        recordReplay.start();
        proxyManager.activate(recordReplay.getHost(), recordReplay.getPort());
    }

    @Override
    protected void createRecordReplay(UseCase useCase) {
        stop();
        this.recordReplay = new RecordReplayJetty(getConfig().port(), new StubHandler(useCase));
        start();
    }
}
