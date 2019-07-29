package be.xplore.recordreplay.junit5;

import be.xplore.recordreplay.RecordReplay;
import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.config.RecordReplayConfig;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public final class RecordReplayExtension implements BeforeAllCallback, AfterAllCallback {

    private RecordReplay recordReplay;

    public RecordReplayExtension() {
        this(new RecordReplayConfig());
    }

    public RecordReplayExtension(Configuration configuration) {
        this.recordReplay = new RecordReplay(configuration).recordReplay();
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        recordReplay.start();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        recordReplay.stop();
    }

    public RecordReplayExtension forward() {
        recordReplay.forward();
        return this;
    }

    public RecordReplayExtension record() {
        recordReplay.record();
        return this;
    }

    public RecordReplayExtension replay() {
        recordReplay.replay();
        return this;
    }

    public RecordReplayExtension recordReplay() {
        recordReplay.recordReplay();
        return this;
    }

}
