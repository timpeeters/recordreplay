package be.xplore.recordreplay.junit5;

import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.service.MemoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RepUseCaseExtensionTest extends ExtensionTestBase {
    private static final MemoryRepository REPO = new MemoryRepository();
    @RegisterExtension
    static RecordReplayExtension recordReplay = new RecordReplayExtension(
            CONFIG.repository(REPO))
            .replay();

    @Test
    void testExtension() {
        Response response = executeRequest();
        assertThat(response.getStatusCode()).isEqualTo(500);
        assertThat(response.getBody()).contains("NoSuchElement");
        assertThat(REPO.find().size()).isEqualTo(0);
    }
}
