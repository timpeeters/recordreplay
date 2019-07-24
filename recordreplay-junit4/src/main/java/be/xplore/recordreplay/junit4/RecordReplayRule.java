package be.xplore.recordreplay.junit4;

import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.usecase.ForwardRequestUseCase;
import be.xplore.recordreplay.usecase.RecordReplayUseCase;
import be.xplore.recordreplay.usecase.RecordUseCase;
import be.xplore.recordreplay.usecase.ReplayUseCase;
import be.xplore.recordreplay.usecase.StubHandler;
import be.xplore.recordreplayjetty.RecordReplayJetty;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RecordReplayRule implements TestRule {

    private final Configuration CONFIG;
    private final RecordReplayJetty RECORDREPLAY;


    public RecordReplayRule(Configuration config) {
        this.CONFIG = config;
        this.RECORDREPLAY = new RecordReplayJetty(config.port());
    }

    public RecordReplayRule forward() {
        StubHandler.setCurrent(new StubHandler(
                new ForwardRequestUseCase(CONFIG.client())));
        return this;
    }

    public RecordReplayRule record() {
        StubHandler.setCurrent(new StubHandler(
                new RecordUseCase(CONFIG.repository(), CONFIG.client())));
        return this;
    }

    public RecordReplayRule replay() {
        StubHandler.setCurrent(new StubHandler(
                new ReplayUseCase(CONFIG.repository(), CONFIG.matchers())));
        return this;
    }

    public RecordReplayRule recordReplay() {
        StubHandler.setCurrent(new StubHandler(
                new RecordReplayUseCase(
                        new RecordUseCase(CONFIG.repository(), CONFIG.client()),
                        new ReplayUseCase(CONFIG.repository(), CONFIG.matchers()))));
        return this;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RECORDREPLAY.start();
                try {
                    base.evaluate();
                } finally {
                    RECORDREPLAY.stop();
                }
            }
        };
    }
}
