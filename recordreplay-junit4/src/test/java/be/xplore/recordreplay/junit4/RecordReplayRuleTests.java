package be.xplore.recordreplay.junit4;

import be.xplore.recordreplay.config.Configuration;
import be.xplore.recordreplay.config.RecordReplayConfig;
import be.xplore.recordreplay.service.OkHttpClient;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes =
        be.xplore.demorest.DemoRestApplication.class)
public class RecordReplayRuleTests {

    private static Configuration config = new RecordReplayConfig().client(new OkHttpClient());

    @ClassRule
    public static final RecordReplayRule RULE = new RecordReplayRule(config).forward();

}

