package be.xplore.recordreplay.junit4;

import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.service.MemoryRepository;
import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReplayRuleTest extends RuleTestBase {

    private static final MemoryRepository REPO = new MemoryRepository();

    @ClassRule
    public static final RecordReplayRule RULE =
            new RecordReplayRule(CONFIG.repository(REPO)).replay();

    @Test
    public void testRule() {
        Response response = executeRequest();
        assertThat(response.getStatusCode()).isEqualTo(500);
        assertThat(response.getBody()).contains("NoSuchElement");
        assertThat(REPO.find().size()).isEqualTo(0);
    }

}
