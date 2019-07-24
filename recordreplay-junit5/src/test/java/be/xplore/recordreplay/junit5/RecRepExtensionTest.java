package be.xplore.recordreplay.junit5;

import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.service.DefaultHttpClient;
import be.xplore.fakes.service.HttpClient;
import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.config.RecordReplayConfig;
import be.xplore.recordreplay.service.OkHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes =
        be.xplore.demorest.DemoRestApplication.class)
public class RecRepExtensionTest {
    @LocalServerPort
    private int port;

    private static final Configuration CONFIG = new RecordReplayConfig().client(new OkHttpClient());
    private HttpClient client = new DefaultHttpClient(CONFIG.host(), CONFIG.port());

    @RegisterExtension
    static RecordReplayExtension recordReplay = new RecordReplayExtension(CONFIG).forward();

    @Test
    void testIntercept() {
        Response response = client
                .execute(Request.Builder
                        .get(String.format("http://localhost:%d/user/list", port))
                        .headers(Headers.builder().applicationJson().build()).build());
        assertThat(response.getStatusCode()).isEqualTo(200);
    }
}
