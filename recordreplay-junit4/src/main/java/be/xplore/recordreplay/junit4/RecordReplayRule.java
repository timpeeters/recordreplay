package be.xplore.recordreplay.junit4;

import be.xplore.recordreplay.RecordReplay;
import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.config.RecordReplayConfig;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public final class RecordReplayRule implements TestRule {

    private final RecordReplay recordReplayStarter;

    public RecordReplayRule() {
        this(new RecordReplayConfig());
    }

    public RecordReplayRule(Configuration configuration) {
        this.recordReplayStarter = new RecordReplay(configuration).recordReplay();
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                recordReplayStarter.start();
                try {
                    base.evaluate();
                } finally {
                    recordReplayStarter.stop();
                }
            }
        };
    }

    public RecordReplayRule forward() {
        recordReplayStarter.forward();
        return this;
    }

    public RecordReplayRule record() {
        recordReplayStarter.record();
        return this;
    }

    public RecordReplayRule replay() {
        recordReplayStarter.replay();
        return this;
    }

    public RecordReplayRule recordReplay() {
        recordReplayStarter.recordReplay();
        return this;
    }
}
