package be.xplore.recordreplay.junit5;

import be.xplore.recordreplay.model.Headers;
import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.service.DefaultHttpClient;
import be.xplore.recordreplay.service.HttpClient;
import be.xplore.recordreplay.DemoRestApplication;
import be.xplore.recordreplay.config.RecordReplayConfig;
import be.xplore.recordreplay.repo.OkHttpClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes =
        DemoRestApplication.class)
class ExtensionTestBase {
    static final RecordReplayConfig CONFIG = new RecordReplayConfig().client(OkHttpClient.noProxy());
    private final HttpClient client = new DefaultHttpClient();

    @LocalServerPort
    private int port;

    Response executeRequest() {
        return client.execute(Request.Builder
                .get(String.format("http://localhost:%d/user/list", port))
                .headers(Headers.builder().applicationJson().build()).build());
    }
}