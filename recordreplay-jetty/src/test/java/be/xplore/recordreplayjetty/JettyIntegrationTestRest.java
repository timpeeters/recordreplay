package be.xplore.recordreplayjetty;

import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.service.DefaultHttpClient;
import be.xplore.fakes.service.HttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("PMD.AvoidUsingHardCodedIP")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes =
        be.xplore.demorest.DemoRestApplication.class)
public class JettyIntegrationTestRest {
    private RecordReplayJetty recordReplayJetty;
    private HttpClient client;
    @LocalServerPort
    private int port;

    @Before
    public void initJetty() {
        int jettyPort = port + 1;
        recordReplayJetty = new RecordReplayJetty(jettyPort);
        client = new DefaultHttpClient("0.0.0.0", jettyPort);
        recordReplayJetty.start();
    }

    @After
    public void stopJetty() {
        recordReplayJetty.stop();
    }

    @Test
    public void sendRequest() {
        Request getUsersRequest = Request.Builder.get(String.format("http://0.0.0.0:%d%s", port, "/user/list"))
                .headers(Headers.builder().applicationJson().build())
                .build();
        var response = client.execute(getUsersRequest);
        assertThat(response.getStatusCode()).isEqualTo(200);
    }
}
