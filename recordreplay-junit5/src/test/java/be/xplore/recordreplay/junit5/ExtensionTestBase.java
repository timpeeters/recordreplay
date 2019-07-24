package be.xplore.recordreplay.junit5;

import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.service.DefaultHttpClient;
import be.xplore.fakes.service.HttpClient;
import be.xplore.recordreplay.config.RecordReplayConfig;
import be.xplore.recordreplay.service.OkHttpClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes =
        be.xplore.demorest.DemoRestApplication.class)
class ExtensionTestBase {
    static final RecordReplayConfig CONFIG = new RecordReplayConfig().client(new OkHttpClient());
    private final HttpClient client = new DefaultHttpClient(CONFIG.host(), CONFIG.port());

    @LocalServerPort
    private int port;

    Response executeRequest() {
        return client.execute(Request.Builder
                .get(String.format("http://localhost:%d/user/list", port))
                .headers(Headers.builder().applicationJson().build()).build());
    }
}
