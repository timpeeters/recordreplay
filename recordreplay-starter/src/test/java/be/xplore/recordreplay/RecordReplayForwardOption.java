package be.xplore.recordreplay;

import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.config.RecordReplayConfig;
import be.xplore.recordreplay.http.DefaultHttpClient;
import be.xplore.recordreplay.http.HttpClient;
import be.xplore.recordreplay.model.Headers;
import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.testdemo.DemoRestApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes =
        DemoRestApplication.class)
public class RecordReplayForwardOption {
    private static final String HOST = "localhost";
    private static RecordReplay recordReplay;

    private HttpClient client;
    private Configuration configuration;


    @LocalServerPort
    private int port;

    @Before
    public void init() {
        client = new DefaultHttpClient();
        configuration = new RecordReplayConfig().target(String.format("http://%s:%d/user/list", HOST, port));
        recordReplay = new RecordReplay(configuration);
    }

    @Test
    public void testTargetHostOption() {
        recordReplay.forward();
        Response r = client
                .execute(Request.Builder.get(String.format("http://%s:%d", HOST, configuration.port())).headers(Headers
                        .builder().applicationJson().build()).build());
        assertThat(r.getStatusCode()).isEqualTo(200);
        assertThat(r.getBody()).containsIgnoringCase("john");
    }
}
