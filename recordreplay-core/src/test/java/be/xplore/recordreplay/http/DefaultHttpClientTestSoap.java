package be.xplore.recordreplay.http;

import be.xplore.recordreplay.testdemo.DemoSoapApplication;
import be.xplore.recordreplay.model.Headers;
import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.RequestMethod;
import be.xplore.recordreplay.model.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes =
        DemoSoapApplication.class)
public class DefaultHttpClientTestSoap {

    @LocalServerPort
    private String port;
    private String host;
    private final HttpClient client = new DefaultHttpClient();

    @Before
    public void initContext() {
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
