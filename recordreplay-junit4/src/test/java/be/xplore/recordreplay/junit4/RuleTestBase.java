package be.xplore.recordreplay.junit4;

import be.xplore.recordreplay.DemoRestApplication;
import be.xplore.recordreplay.config.RecordReplayConfig;
import be.xplore.recordreplay.model.Headers;
import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.http.DefaultHttpClient;
import be.xplore.recordreplay.http.HttpClient;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes =
        DemoRestApplication.class)
abstract class RuleTestBase {

    static final RecordReplayConfig CONFIG = new RecordReplayConfig();
    private final HttpClient client = new DefaultHttpClient(CONFIG.host(), CONFIG.port());

    @LocalServerPort
    private int port;

    Response executeRequest() {
        return client.execute(Request.Builder
                .get(String.format("http://localhost:%d/user/list", port))
                .headers(Headers.builder().applicationJson().build()).build());
    }

}
