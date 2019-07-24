package be.xplore.recordreplay.junit4;

import be.xplore.fakes.model.Response;
import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RecordReplayRuleTest extends AbstractRuleTest {

    @ClassRule
    public static final RecordReplayRule RULE = new RecordReplayRule(CONFIG).recordReplay();

    @Test
    public void testRule() {
        Response response = executeRequest();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(CONFIG.repository().find().size()).isEqualTo(1);
        assertThat(response).isEqualTo(executeRequest());
    }

}
