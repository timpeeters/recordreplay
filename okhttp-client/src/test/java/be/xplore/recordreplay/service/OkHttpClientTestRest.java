package be.xplore.recordreplay.service;

import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.service.HttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes =
        be.xplore.demorest.DemoRestApplication.class)
public class OkHttpClientTestRest {

    @Autowired
    private Environment environment;
    private String host;
    private final HttpClient client = new OkHttpClient();

    @Before
    public void initContext() {
        String port = environment.getProperty("local.server.port");
        this.host = String.format("http://localhost:%s", port);

    }

    @Test
    public void testGetRequest() {
        Request getUsersRequest = Request.Builder.get(host + "/user/list")
                .headers(Headers.builder().applicationJson().build())
                .build();
        Response response = client.execute(getUsersRequest);
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void testInvalidRequest() {
        Request getUsersRequest = Request.Builder.post(host + "/user/list")
                .headers(Headers.builder().applicationJson().build())
                .build();
        Response response = client.execute(getUsersRequest);
        assertThat(response.getStatusCode()).isEqualTo(405);
    }

}
