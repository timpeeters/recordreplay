package be.xplore.recordreplay.junit5;

import be.xplore.recordreplay.config.RecordReplayConfig;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.repository.MemoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RecRepUseCaseExtensionTest extends ExtensionTestBase {
    private static final MemoryRepository REPO = new MemoryRepository();
    @RegisterExtension
    static RecordReplayExtension recordReplay = new RecordReplayExtension(new RecordReplayConfig().repository(REPO))
            .recordReplay();

    @Test
    void testExtension() {
        Response response = executeRequest();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(REPO.find().size()).isEqualTo(1);
        assertThat(response).isEqualTo(executeRequest());
    }
}
