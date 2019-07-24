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

    private Configuration config;
    private RecordReplayJetty recordReplay;
    private StubHandler stubhandler;

    public RecordReplayRule(Configuration config) {
        this.config = config;
    }

    public RecordReplayRule forward() {
        stubhandler = new StubHandler(
                new ForwardRequestUseCase(config.client()));
        StubHandler.setCurrent(stubhandler);
        return this;
    }

    public RecordReplayRule record() {
        stubhandler = new StubHandler(
                new RecordUseCase(config.repository(), config.client()));
        StubHandler.setCurrent(stubhandler);
        return this;
    }

    public RecordReplayRule replay() {
        stubhandler = new StubHandler(
                new ReplayUseCase(config.repository(), config.matchers()));
        StubHandler.setCurrent(stubhandler);
        return this;
    }

    public RecordReplayRule recordReplay() {
        stubhandler = new StubHandler(
                new RecordReplayUseCase(
                        new RecordUseCase(config.repository(), config.client()),
                        new ReplayUseCase(config.repository(), config.matchers())));
        StubHandler.setCurrent(stubhandler);
        return this;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                recordReplay = new RecordReplayJetty(config.port());
                recordReplay.start();
                try {
                    base.evaluate(); // This will run the test.
                } finally {
                    recordReplay.stop();
                }
            }
        };
    }
}
