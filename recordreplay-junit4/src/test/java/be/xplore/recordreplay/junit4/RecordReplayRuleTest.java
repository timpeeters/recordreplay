package be.xplore.recordreplay.junit4;

import be.xplore.fakes.model.Response;
import be.xplore.recordreplay.repo.MemoryRepository;
import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RecordReplayRuleTest extends RuleTestBase {

    private static final MemoryRepository REPO = new MemoryRepository();

    @ClassRule
    public static final RecordReplayRule RULE =
            new RecordReplayRule(CONFIG.repository(REPO)).recordReplay();

    @Test
    public void testRule() {
        Response response = executeRequest();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(REPO.find().size()).isEqualTo(1);
        assertThat(response).isEqualTo(executeRequest());
    }

}
