package be.xplore.recordreplay.http;

import be.xplore.recordreplay.util.ClassLocator;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RecordReplayJettyTests {

    @Test
    public void foundByLocator() {
        HttpServer httpServer = new ClassLocator<>(HttpServer.class).load();
        assertThat(httpServer).isExactlyInstanceOf(RecordReplayJetty.class);
    }

    @Test
    public void foundByLoadClass() {
        HttpServer httpServer = new ClassLocator<>(HttpServer.class).load(RecordReplayJetty.class);
        assertThat(httpServer).isExactlyInstanceOf(RecordReplayJetty.class);
    }

}