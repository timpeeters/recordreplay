package be.xplore.recordreplayjetty;

import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.DefaultHttpClient;
import be.xplore.fakes.service.HttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("PMD.AvoidUsingHardCodedIP")
public abstract class IntegrationTestBase {
    private static final String HOST = "localhost";

    @LocalServerPort
    private int port;
    private HttpClient client;
    private RecordReplayJetty recordReplayJetty;


    @Before
    public void initContext() {
        int jettyPort = port + 1;
        recordReplayJetty = new RecordReplayJetty(jettyPort);
        recordReplayJetty.start();
        this.client = new DefaultHttpClient(HOST, jettyPort);
    }

    @After
    public void stopJetty() {
        recordReplayJetty.stop();
    }

    @Test
    public void forwardRequestTest() {
        for (Stub stub : stubsToTest()) {
            var response = client.execute(stub.getRequest());
            assertThat(response.getStatusCode()).isEqualTo(stub.getResponse().getStatusCode());
        }
    }

    @Test
    public void recordTest() {
        for (Stub stub : stubsToTest()) {
            var response = client.execute(stub.getRequest());
            assertThat(response.getStatusCode()).isEqualTo(stub.getResponse().getStatusCode());
        }
    }

    @Test
    public void replayEmptyRepoTest() {
        for (Stub stub : stubsToTest()) {
            assertThat(client.execute(stub.getRequest()).getStatusCode()).isEqualTo(500);
        }
    }

    @Test
    public void recordReplayTest() {
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
