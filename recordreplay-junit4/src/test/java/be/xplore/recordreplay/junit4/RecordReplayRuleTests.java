package be.xplore.recordreplay.junit4;

import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.QueryParams;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.RequestMethod;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.config.RecordReplayConfig;
import be.xplore.recordreplay.service.OkHttpClient;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes =
        be.xplore.demorest.DemoRestApplication.class)
public class RecordReplayRuleTests {

    private static Configuration config = new RecordReplayConfig().client(new OkHttpClient());

    @ClassRule
    public static final RecordReplayRule RULE = new RecordReplayRule(config).forward();

    protected String getBaseUrl() {
        return String.format("http://%s:%d", HOST, port);
    }

    protected List<Stub> stubsToTest() {
        return List.of(
                new Stub(Request.Builder.get(getBaseUrl() + "/user/list")
                        .headers(Headers.builder().applicationJson().build()).build(),
                        Response.ok()),

                new Stub(Request.Builder.get(getBaseUrl() + "/user")
                        .queryParams(idZeroParams())
                        .headers(Headers.builder().applicationJson().build()).build(),
                        Response.ok()),

                new Stub(Request.Builder.get(getBaseUrl() + "/user")
                        .queryParams(QueryParams.builder().param("id", "999").build())
                        .headers(Headers.builder().applicationJson().build()).build(),
                        Response.notFound()),

                new Stub(Request.Builder.post(getBaseUrl() + "/user/create")
                        .body("{\"id\":0,\"firstName\":\"fvgbhj\",\"lastName\":\"dtrfuhnjk\",\"role\":\"erdtfhjkl\"}")
                        .headers(Headers.builder().applicationJson().build()).build(),
                        Response.ok()),

                new Stub(Request.builder().method(RequestMethod.DELETE).path(getBaseUrl() + "/user/delete")
                        .queryParams(idZeroParams())
                        .headers(Headers.builder().applicationJson().build()).build(),
                        Response.ok()),

                new Stub(Request.builder().method(RequestMethod.DELETE).path(getBaseUrl() + "/user/delete")
                        .headers(Headers.builder().applicationJson().build())
                        .queryParams(QueryParams.builder().param("id", "999").build()).build(),
                        Response.builder().statusCode(400).build())
        );
    }

}