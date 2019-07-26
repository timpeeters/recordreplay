package be.xplore.recordreplay.junit4;

import be.xplore.recordreplay.RecordReplay;
import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.jetty.RecordReplayJetty;
import be.xplore.recordreplay.proxy.ProxyManager;
import be.xplore.recordreplay.usecase.StubHandler;
import be.xplore.recordreplay.usecase.UseCase;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RecordReplayRule extends RecordReplay<RecordReplayRule> implements TestRule {

    private RecordReplayJetty recordReplay;
    private final ProxyManager proxyManager;


    public RecordReplayRule(Configuration configuration) {
        super(configuration);
        proxyManager = new ProxyManager();
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

    @Override
    protected void createRecordReplay(UseCase useCase) {
        stop();
        this.recordReplay = new RecordReplayJetty(getConfig().port(), new StubHandler(useCase));
        start();
    }
}
