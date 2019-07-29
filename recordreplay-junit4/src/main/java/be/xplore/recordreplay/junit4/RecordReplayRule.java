package be.xplore.recordreplay.junit4;

import be.xplore.recordreplay.RecordReplay;
import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.config.RecordReplayConfig;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RecordReplayRule implements TestRule {

    private final RecordReplay recordReplay;

    public RecordReplayRule() {
        this(new RecordReplayConfig());
    }

    public RecordReplayRule(Configuration configuration) {
        recordReplay = new RecordReplay(configuration).recordReplay();
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                recordReplay.start();
                try {
                    base.evaluate();
                } finally {
                    recordReplay.stop();
                }
            }
        };
    }

    public RecordReplayRule forward() {
        recordReplay.forward();
        return this;
    }

    public RecordReplayRule record() {
        recordReplay.record();
        return this;
    }

    public RecordReplayRule replay() {
        recordReplay.replay();
        return this;
    }

    public RecordReplayRule recordReplay() {
        recordReplay.recordReplay();
        return this;
    }
}
