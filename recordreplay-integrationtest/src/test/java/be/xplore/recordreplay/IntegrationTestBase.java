package be.xplore.recordreplay;

import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.DefaultHttpClient;
import be.xplore.recordreplay.config.RecordReplayConfig;
import be.xplore.recordreplay.junit4.RecordReplayRule;
import be.xplore.recordreplay.service.OkHttpClient;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

public abstract class IntegrationTestBase {
    private static final String HOST = "localhost";

    @LocalServerPort
    private int port;
    private DefaultHttpClient client;

    @Rule
    public RecordReplayRule recordReplayRule = new RecordReplayRule(new RecordReplayConfig()
            .client(new OkHttpClient())
            .port(port))
            .recordReplay();

    @Before
    public void initContext() {
        this.client = new DefaultHttpClient();
    }

    @Test
    public void forwardRequestTest() {
        for (Stub stub : stubsToTest()) {
            var response = client.execute(stub.getRequest());
            AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(stub.getResponse().getStatusCode());
        }
    }

    @Test
    public void recordTest() {
        for (Stub stub : stubsToTest()) {
            var response = client.execute(stub.getRequest());
            AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(stub.getResponse().getStatusCode());
        }
    }

    @Test
    public void replayEmptyRepoTest() {
        for (Stub stub : stubsToTest()) {
            AssertionsForClassTypes.assertThat(client.execute(stub.getRequest()).getStatusCode()).isEqualTo(500);
        }
    }

    @Test
    public void recordReplayTest() {
        for (Stub stub : stubsToTest()) {
            AssertionsForClassTypes.assertThat(client.execute(stub.getRequest()))
                    .isEqualTo(client.execute(stub.getRequest()));
        }
    }

    protected String getBaseUrl() {
        return String.format("http://%s:%d", HOST, port);
    }

    protected abstract List<Stub> stubsToTest();
}
