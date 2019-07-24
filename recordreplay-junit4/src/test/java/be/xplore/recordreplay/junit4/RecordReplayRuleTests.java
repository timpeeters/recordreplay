package be.xplore.recordreplay.junit4;

import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.service.DefaultHttpClient;
import be.xplore.fakes.service.HttpClient;
import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.config.RecordReplayConfig;
import be.xplore.recordreplay.service.OkHttpClient;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes =
        be.xplore.demorest.DemoRestApplication.class)
public class RecordReplayRuleTests {

    private static final Configuration CONFIG = new RecordReplayConfig().client(new OkHttpClient());

    @ClassRule
    public static final RecordReplayRule RULE = new RecordReplayRule(CONFIG).forward();

    @LocalServerPort
    private int port;

    private final HttpClient client = new DefaultHttpClient(CONFIG.host(), CONFIG.port());


    @Test
    public void testIntercept() {
        Response response = client
                .execute(Request.Builder
                        .get(String.format("http://localhost:%d/user/list", port))
                        .headers(Headers.builder().applicationJson().build()).build());
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

}

