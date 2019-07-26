package be.xplore.recordreplay.repo;

import be.xplore.recordreplay.model.Headers;
import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.RequestMethod;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.DemoRestApplication;
import be.xplore.recordreplay.service.DefaultHttpClient;
import be.xplore.recordreplay.service.HttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes =
        DemoRestApplication.class)
public class DefaultHttpClientTestRest {

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
        Request getUsersRequest = Request.Builder.get(host + "/user/list")
                .headers(Headers.builder().applicationJson().build())
                .build();
        Response response = client.execute(getUsersRequest);
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void testPostRequest() {
        Request getUsersRequest = Request.Builder.post(host + "/user/create")
                .headers(Headers.builder().applicationJson().build())
                .body("{\"id\":0,\"firstName\":\"fvgbhj\",\"lastName\":\"dtrfuhnjk\",\"role\":\"erdtfhjkl\"}")
                .build();
        Response response = client.execute(getUsersRequest);
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void testPutRequest() {
        Request getUsersRequest = Request.Builder.put(host + "/user/update")
                .headers(Headers.builder().applicationJson().build())
                .body("{\"id\":0,\"role\":\"updatedrole\"}")
                .build();
        Response response = client.execute(getUsersRequest);
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void testDeleteRequest() {
        Request getUsersRequest = Request.builder()
                .method(RequestMethod.DELETE)
                .path(host + "/user/delete?id=0")
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

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidUrl() {
        Request getUsersRequest = Request.Builder.post(host + "user/list")
                .headers(Headers.builder().applicationJson().build())
                .build();
        Response response = client.execute(getUsersRequest);
        assertThat(response.getStatusCode()).isEqualTo(405);
    }
}