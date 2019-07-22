package be.xplore.recordreplayjetty;

import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.DefaultHttpClient;
import be.xplore.fakes.service.HttpClient;
import be.xplore.recordreplay.service.AbstractHttpServlet;
import be.xplore.recordreplay.service.ForwardingHttpServlet;
import be.xplore.recordreplay.service.RecordHttpServlet;
import be.xplore.recordreplay.service.RecordReplayHttpServlet;
import be.xplore.recordreplay.service.ReplayHttpServlet;
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
    private int jettyPort;


    @Before
    public void initContext() {
        this.jettyPort = port + 1;
        this.client = new DefaultHttpClient(HOST, jettyPort);
    }

    @After
    public void stopJetty() {
        recordReplayJetty.stop();
    }

    @Test
    public void forwardRequestTest() {
        initJetty(ForwardingHttpServlet.class);
        for (Stub stub : stubsToTest()) {
            var response = client.execute(stub.getRequest());
            assertThat(response.getStatusCode()).isEqualTo(stub.getResponse().getStatusCode());
        }
    }

    @Test
    public void recordTest() {
        initJetty(RecordHttpServlet.class);
        for (Stub stub : stubsToTest()) {
            var response = client.execute(stub.getRequest());
            assertThat(response.getStatusCode()).isEqualTo(stub.getResponse().getStatusCode());
        }
    }

    @Test
    public void replayEmptyRepoTest() {
        initJetty(ReplayHttpServlet.class);
        for (Stub stub : stubsToTest()) {
            assertThat(client.execute(stub.getRequest()).getStatusCode()).isEqualTo(500);
        }
    }

    @Test
    public void recordReplayTest() {
        initJetty(RecordReplayHttpServlet.class);
        for (Stub stub : stubsToTest()) {
            assertThat(client.execute(stub.getRequest()))
                    .isEqualTo(client.execute(stub.getRequest()));
        }
    }

    private void initJetty(Class<? extends AbstractHttpServlet> servlet) {
        recordReplayJetty = new RecordReplayJetty(jettyPort, servlet);
        recordReplayJetty.start();
    }

    protected String getBaseUrl() {
        return String.format("http://%s:%d", HOST, port);
    }

    protected abstract List<Stub> stubsToTest();
}
