package be.xplore.recordreplay;

import be.xplore.recordreplay.model.Headers;
import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Stub;
import be.xplore.recordreplay.testdemo.DemoSoapApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes =
        DemoSoapApplication.class)
public class IntegrationTestSoap extends IntegrationTestBase {
    private static final String USER_ENDPOINT = "/services/user";

    @Override
    protected List<Stub> stubsToTest() {
        return List.of(
                new Stub(Request.Builder.get(getBaseUrl() + "/findUsers")
                        .headers(Headers.builder().applicationXml().build()).build(),
                        Response.ok())
        );
    }

    @Override
    protected String getBaseUrl() {
        return super.getBaseUrl() + USER_ENDPOINT;
    }
}
