package be.xplore.recordreplay.junit4;

import be.xplore.fakes.config.Configuration;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RecordReplayRule implements TestRule {

    public RecordReplayJetty recordreplay;

    public RecordReplayRule(Configuration config) {

    }

    ;

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {

                try

            {
                base.evaluate();
            }finally

            {

            }
        };
    }
}
