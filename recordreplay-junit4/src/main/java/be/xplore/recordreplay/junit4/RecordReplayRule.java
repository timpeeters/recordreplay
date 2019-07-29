package be.xplore.recordreplay.junit4;

import be.xplore.recordreplay.RecordReplay;
import be.xplore.recordreplay.config.Configuration;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RecordReplayRule implements TestRule {

    private final RecordReplay recordReplay;

    public RecordReplayRule(Configuration configuration) {
        recordReplay = new RecordReplay(configuration);
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

    public RecordReplayRule replay() {
        recordReplay.replay();
        return this;
    }

    public RecordReplayRule forward() {
        recordReplay.forward();
        return this;
    }

    public RecordReplayRule record() {
        recordReplay.record();
        return this;
    }

    public RecordReplayRule recordReplay() {
        recordReplay.record();
        return this;
    }
}
