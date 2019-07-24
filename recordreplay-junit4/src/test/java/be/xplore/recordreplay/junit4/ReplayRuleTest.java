package be.xplore.recordreplay.junit4;

import be.xplore.fakes.model.Response;
import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReplayRuleTest extends AbstractRuleTest {

    @ClassRule
    public static final RecordReplayRule RULE = new RecordReplayRule(CONFIG).replay();

    @Test
    void testRule() {
        Response response = executeRequest();
        assertThat(response.getStatusCode()).isEqualTo(500);
        assertThat(response.getBody()).contains("NoSuchElement");
        assertThat(CONFIG.repository().find().size()).isEqualTo(0);
    }

}
