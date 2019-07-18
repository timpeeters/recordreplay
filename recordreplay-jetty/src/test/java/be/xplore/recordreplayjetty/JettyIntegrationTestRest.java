package be.xplore.recordreplayjetty;

import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.service.DefaultHttpClient;
import be.xplore.fakes.service.HttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

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
        recordReplayJetty = new RecordReplayJetty(port + 1);
        client = new DefaultHttpClient();
    }

    @Test
    public void contextLoads() {
        recordReplayJetty.start();
        Request getUsersRequest = Request.Builder.get(String.format("http://localhost:%d%s", port+1, "/user/list"))
                .headers(Headers.builder().applicationJson().build())
                .build();
        var Response = client.execute(getUsersRequest);
        recordReplayJetty.stop();
    }
}
