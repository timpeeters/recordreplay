package be.xplore.recordreplay;

import be.xplore.recordreplay.config.RecordReplayConfig;
import be.xplore.recordreplay.http.HttpClient;
import be.xplore.recordreplay.junit4.RecordReplayRule;
import be.xplore.recordreplay.model.Stub;
import be.xplore.recordreplay.repository.MemoryRepository;
import be.xplore.recordreplay.util.ClassLocator;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class IntegrationTestBase {
    private static final RecordReplayConfig CONFIG = new RecordReplayConfig().port(8181).target(null);
    @ClassRule
    public static RecordReplayRule recordReplayRule = new RecordReplayRule(CONFIG);
    private static final String HOST = "localhost";

    @LocalServerPort
    private int port;
    private HttpClient client;


    @Before
    public void initContext() {
        this.client = new ClassLocator<>(HttpClient.class).load();
        CONFIG.repository(new MemoryRepository());
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
        return String.format("http://%s:%d", HOST, port);
    }

    protected abstract List<Stub> stubsToTest();
}
