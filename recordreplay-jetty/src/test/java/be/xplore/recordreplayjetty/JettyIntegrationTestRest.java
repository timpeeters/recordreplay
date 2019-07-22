package be.xplore.recordreplayjetty;

import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SuppressWarnings("PMD.AvoidUsingHardCodedIP")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes =
        be.xplore.demorest.DemoRestApplication.class)
public class JettyIntegrationTestRest extends IntegrationTestBase {
    @Override
    protected List<Stub> stubsToTest() {
        return List.of(
                new Stub(Request.Builder.get(getBaseUrl() + "/user/list")
                        .headers(Headers.builder().applicationJson().build()).build(),
                        Response.ok())
        );
    }
}
