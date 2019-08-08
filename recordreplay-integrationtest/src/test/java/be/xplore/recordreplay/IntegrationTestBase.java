package be.xplore.recordreplay;

import be.xplore.recordreplay.config.RecordReplayConfig;
import be.xplore.recordreplay.http.DefaultHttpClient;
import be.xplore.recordreplay.http.HttpClient;
import be.xplore.recordreplay.junit4.RecordReplayRule;
import be.xplore.recordreplay.model.Stub;
import be.xplore.recordreplay.repository.FileRepository;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class IntegrationTestBase {
    private static final String HOST = "localhost";
    private static final RecordReplayConfig CONFIG =
            new RecordReplayConfig()
                    .port(8181)
                    .target(null)
                    .host(HOST);
    @ClassRule
    public static RecordReplayRule recordReplayRule = new RecordReplayRule(CONFIG);

    @Rule
    public TemporaryFolder tempDir = new TemporaryFolder();

    @LocalServerPort
    private int port;
    private HttpClient client;


    @Before
    public void initContext() {
        this.client = new DefaultHttpClient();
        CONFIG.repository(new FileRepository(getTargetDir()));
    }

    private Path getTargetDir() {
        try {
            return tempDir.newFolder().toPath();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Test
    public void forwardRequestTest() {
        recordReplayRule.forward();
        for (Stub stub : stubsToTest()) {
            var response = client.execute(stub.getRequest());
            assertThat(response.getStatusCode()).isEqualTo(stub.getResponse().getStatusCode());
        }
    }

    @Test
    public void recordTest() {
        recordReplayRule.record();
        for (Stub stub : stubsToTest()) {
            var response = client.execute(stub.getRequest());
            assertThat(response.getStatusCode()).isEqualTo(stub.getResponse().getStatusCode());
        }
    }

    @Test
    public void replayEmptyRepoTest() {
        recordReplayRule.replay();
        for (Stub stub : stubsToTest()) {
            assertThat(client.execute(stub.getRequest()).getStatusCode()).isEqualTo(500);
        }
    }

    @Test
    public void recordReplayTest() {
        recordReplayRule.recordReplay();
        for (Stub stub : stubsToTest()) {
            assertThat(client.execute(stub.getRequest()))
                    .isEqualTo(client.execute(stub.getRequest()));
        }
    }

    protected String getBaseUrl() {
        return String.format("http://%s:%d", CONFIG.host(), port);
    }

    protected abstract List<Stub> stubsToTest();
}
