package be.xplore.recordreplay.junit5;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class RecordReplayExtension implements BeforeAllCallback, AfterAllCallback {

    @Override
    public void afterAll(ExtensionContext context) {

    }

    @Override
    public void beforeAll(ExtensionContext context) {

    }
}
