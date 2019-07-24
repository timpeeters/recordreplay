package be.xplore.recordreplay.junit5;

import be.xplore.fakes.model.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ForwardUseCaseExtensionTest extends ExtensionTestBase {
    @RegisterExtension
    static RecordReplayExtension recordReplay = new RecordReplayExtension(CONFIG).forward();

    @Test
    void testExtension() {
        Response response = executeRequest();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }
}