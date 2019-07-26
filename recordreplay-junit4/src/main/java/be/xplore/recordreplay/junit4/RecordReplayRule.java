package be.xplore.recordreplay.junit4;

import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.proxy.ProxyManager;
import be.xplore.recordreplay.usecase.ForwardRequestUseCase;
import be.xplore.recordreplay.usecase.RecordReplayUseCase;
import be.xplore.recordreplay.usecase.RecordUseCase;
import be.xplore.recordreplay.usecase.ReplayUseCase;
import be.xplore.recordreplay.usecase.StubHandler;
import be.xplore.recordreplay.usecase.UseCase;
import be.xplore.recordreplay.jetty.RecordReplayJetty;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RecordReplayRule implements TestRule {

    private final Configuration config;
    private RecordReplayJetty recordReplay;
    private final ProxyManager proxyManager;


    public RecordReplayRule(Configuration config) {
        this.config = config;
        proxyManager = new ProxyManager();
    }

    public RecordReplayRule forward() {
        createRecordReplay(new ForwardRequestUseCase(config.client()));
        return this;
    }

    public RecordReplayRule record() {
        createRecordReplay(new RecordUseCase(config.repository(), config.client()));
        return this;
    }

    public RecordReplayRule replay() {
        createRecordReplay(new ReplayUseCase(config.repository(), config.matchers()));
        return this;
    }

    public RecordReplayRule recordReplay() {
        createRecordReplay(new RecordReplayUseCase(config.repository(), config.client(), config.matchers()));
        return this;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                start();
                try {
                    base.evaluate();
                } finally {
                    stop();
                }
            }
        };
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
        this.recordReplay = new RecordReplayJetty(config.port(), new StubHandler(useCase));
        start();
    }
}
