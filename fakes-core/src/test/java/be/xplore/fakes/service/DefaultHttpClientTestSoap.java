package be.xplore.fakes.service;

import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.RequestMethod;
import be.xplore.fakes.model.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes =
        be.xplore.demosoap.DemoSoapApplication.class)
public class DefaultHttpClientTestSoap {
    @Autowired
    private Environment environment;
    private String host;
    private final HttpClient client = new DefaultHttpClient();

    @Before
    public void initContext() {
        String port = environment.getProperty("local.server.port");
        this.host = String.format("http://localhost:%s", port);
    }

    @Test
    public void testGetRequest() {
        Request request = Request.builder().method(RequestMethod.GET).path(host + "/services/user")
                .headers(Headers.builder().header("Content-Type", "application/soap+xml").build()).build();
        Response response = client.execute(request);
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void testInvalidRequestPath() {
        Request request = Request.builder().method(RequestMethod.GET).path(host + "/services/iubny")
                .headers(Headers.builder().header("Content-Type", "application/soap+xml").build()).build();
        Response response = client.execute(request);
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    public void testInvalidRequestMethod() {
        Request request = Request.builder().method(RequestMethod.TRACE).path(host + "/services/user")
                .headers(Headers.builder().header("Content-Type", "application/soap+xml").build()).build();
        Response response = client.execute(request);
        assertThat(response.getStatusCode()).isEqualTo(405);
    }
}