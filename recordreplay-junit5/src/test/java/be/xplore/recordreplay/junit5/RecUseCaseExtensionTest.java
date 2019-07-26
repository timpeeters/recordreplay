package be.xplore.recordreplay.junit5;

import be.xplore.fakes.model.Response;
import be.xplore.recordreplay.repo.MemoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RecUseCaseExtensionTest extends ExtensionTestBase {
    private static final MemoryRepository REPO = new MemoryRepository();
    @RegisterExtension
    static RecordReplayExtension recordReplay = new RecordReplayExtension(
            CONFIG.repository(REPO))
            .record();

    @Test
    void testExtension() {
        Response response = executeRequest();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(REPO.find().size()).isEqualTo(1);
    }
}
