package be.xplore.recordreplayjetty;

import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.DefaultHttpClient;
import be.xplore.fakes.service.HttpClient;
import be.xplore.fakes.service.MemoryRepository;
import be.xplore.fakes.service.RequestBodyMatcher;
import be.xplore.fakes.service.RequestHeaderMatcher;
import be.xplore.fakes.service.RequestMatcher;
import be.xplore.fakes.service.RequestMethodMatcher;
import be.xplore.fakes.service.RequestParamMatcher;
import be.xplore.fakes.service.RequestPathMatcher;
import be.xplore.recordreplay.service.OkHttpClient;
import be.xplore.recordreplay.usecase.ForwardRequestUseCase;
import be.xplore.recordreplay.usecase.RecordReplayUseCase;
import be.xplore.recordreplay.usecase.RecordUseCase;
import be.xplore.recordreplay.usecase.ReplayUseCase;
import be.xplore.recordreplay.usecase.StubHandler;
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
    private RecordUseCase recordUseCase;
    private ReplayUseCase replayUseCase;
    private RecordReplayUseCase recordReplayUseCase;
    private ForwardRequestUseCase forwardUseCase;


    @Before
    public void initContext() {
        this.jettyPort = port + 1;
        this.client = new DefaultHttpClient(HOST, jettyPort);
    }

    @Before
    public void initUseCases() {
        this.forwardUseCase = new ForwardRequestUseCase(new OkHttpClient());
        this.recordUseCase = new RecordUseCase(new MemoryRepository(), new OkHttpClient());
        this.replayUseCase = new ReplayUseCase(new MemoryRepository(), getMatchers());
        this.recordReplayUseCase = new RecordReplayUseCase(recordUseCase, replayUseCase);
    }

    @After
    public void stopJetty() {
        recordReplayJetty.stop();
    }

    @Test
    public void forwardRequestTest() {
        initJetty(new StubHandler(forwardUseCase));
        for (Stub stub : stubsToTest()) {
            var response = client.execute(stub.getRequest());
            assertThat(response.getStatusCode()).isEqualTo(stub.getResponse().getStatusCode());
        }
    }

    @Test
    public void recordTest() {
        initJetty(new StubHandler(recordUseCase));
        for (Stub stub : stubsToTest()) {
            var response = client.execute(stub.getRequest());
            assertThat(response.getStatusCode()).isEqualTo(stub.getResponse().getStatusCode());
        }
    }

    @Test
    public void replayEmptyRepoTest() {
        initJetty(new StubHandler(replayUseCase));
        for (Stub stub : stubsToTest()) {
            assertThat(client.execute(stub.getRequest()).getStatusCode()).isEqualTo(500);
        }
    }

    @Test
    public void recordReplayTest() {
        initJetty(new StubHandler(recordReplayUseCase));
        for (Stub stub : stubsToTest()) {
            assertThat(client.execute(stub.getRequest()))
                    .isEqualTo(client.execute(stub.getRequest()));
        }
    }

    private void initJetty(StubHandler handler) {
        recordReplayJetty = new RecordReplayJetty(jettyPort, handler);
        recordReplayJetty.start();
    }

    protected String getBaseUrl() {
        return String.format("http://%s:%d", HOST, port);
    }

    private List<RequestMatcher> getMatchers() {
        return List.of(
                new RequestMethodMatcher(),
                new RequestPathMatcher(),
                new RequestHeaderMatcher(),
                new RequestParamMatcher(),
                new RequestBodyMatcher());
    }

    protected abstract List<Stub> stubsToTest();
}
