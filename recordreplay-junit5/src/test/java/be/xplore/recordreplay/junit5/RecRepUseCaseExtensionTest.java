package be.xplore.recordreplay.junit5;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.service.MemoryRepository;
import be.xplore.fakes.service.RequestBodyMatcher;
import be.xplore.fakes.service.RequestHeaderMatcher;
import be.xplore.fakes.service.RequestMethodMatcher;
import be.xplore.fakes.service.RequestParamMatcher;
import be.xplore.fakes.service.RequestPathMatcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RecRepUseCaseExtensionTest extends ExtensionTestBase {
    private static final MemoryRepository REPO = new MemoryRepository();
    @RegisterExtension
    static RecordReplayExtension recordReplay = new RecordReplayExtension(CONFIG
            .repository(REPO)
            .matchers(List.of(
                    new RequestMethodMatcher(),
                    new RequestPathMatcher(),
                    new RequestHeaderMatcher(),
                    new RequestParamMatcher(),
                    new RequestBodyMatcher()))
    ).recordReplay();

    @Test
    void testExtension() {
        Response response = executeRequest();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(REPO.find().size()).isEqualTo(1);
        assertThat(response).isEqualTo(executeRequest());
    }
}
