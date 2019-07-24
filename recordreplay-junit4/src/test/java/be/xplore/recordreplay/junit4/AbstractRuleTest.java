package be.xplore.recordreplay.junit4;

import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.service.DefaultHttpClient;
import be.xplore.fakes.service.HttpClient;
import be.xplore.fakes.service.MemoryRepository;
import be.xplore.fakes.service.RequestBodyMatcher;
import be.xplore.fakes.service.RequestHeaderMatcher;
import be.xplore.fakes.service.RequestMethodMatcher;
import be.xplore.fakes.service.RequestParamMatcher;
import be.xplore.fakes.service.RequestPathMatcher;
import be.xplore.recordreplay.config.RecordReplayConfig;
import be.xplore.recordreplay.service.OkHttpClient;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes =
        be.xplore.demorest.DemoRestApplication.class)
public abstract class AbstractRuleTest {

    static final MemoryRepository REPO = new MemoryRepository();
    static final RecordReplayConfig CONFIG = new RecordReplayConfig()
            .client(new OkHttpClient())
            .repository(REPO)
            .matchers(List.of(
                    new RequestMethodMatcher(),
                    new RequestPathMatcher(),
                    new RequestHeaderMatcher(),
                    new RequestParamMatcher(),
                    new RequestBodyMatcher()));
    private final HttpClient client = new DefaultHttpClient(CONFIG.host(), CONFIG.port());

    @LocalServerPort
    private int port;

    Response executeRequest() {
        return client.execute(Request.Builder
                .get(String.format("http://localhost:%d/user/list", port))
                .headers(Headers.builder().applicationJson().build()).build());
    }

}
