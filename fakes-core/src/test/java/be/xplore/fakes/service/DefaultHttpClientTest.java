package be.xplore.fakes.service;

import be.xplore.demorest.controller.UserController;
import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.QueryParams;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes =
        be.xplore.demorest.DemoRestApplication.class)
public class DefaultHttpClientTest {

    @Autowired
    Environment environment;
    @Autowired
    UserController controller;
    private String host;
    private final HttpClient client = new DefaultHttpClient();

    @Before
    public void initContext() {
        String port = environment.getProperty("local.server.port");
        this.host = String.format("http://localhost:%s", port);
    }

    @Test
    public void contextLoads() {
        Response response = client
                .execute(Request.Builder.get(host + "/user/list").queryParams(QueryParams.EMPTY)
                        .headers(Headers.builder().headerMap(Map
                                .of("Content-Type", Collections.singletonList("application/json"))).build())
                        .build());
        assertThat(response.getStatusCode()).isEqualTo(200);
    }
}
