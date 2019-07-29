package be.xplore.recordreplay.matcher;


import be.xplore.recordreplay.model.Request;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestBodyMatcherTests {

    private final RequestMatcher bestMatcher = new RequestBodyMatcher(false);
    private final RequestMatcher exactMatcher = new RequestBodyMatcher(true);

    private Request sameRequest1;
    private Request sameRequest2;
    private Request halfRequest;
    private Request diffRequest;

    @Before
    public void setupTest() {
        sameRequest1 = Request.Builder.post("").body("test").build();
        sameRequest2 = Request.Builder.post("").body("test").build();
        halfRequest = Request.Builder.post("").body("te").build();
        diffRequest = Request.Builder.post("").body("diff").build();
    }

    @Test
    public void requestPathMatcherShouldReturnResultZeroOnMatch() {
        assertThat(bestMatcher.matches(sameRequest1, sameRequest2).getDistance())
                .as("Correct result distance not calculated")
                .isEqualTo(0);
    }

    @Test
    public void shouldReturnHalfonHalfMatch(){
        assertThat(bestMatcher.matches(sameRequest1, halfRequest).getDistance())
                .as("Correct distance result not calculated")
                .isEqualTo(0.50);
    }

    @Test
    public void requestPathMatcherShouldReturnResultOneOnCompleteMismatch() {
        assertThat(bestMatcher.matches(sameRequest1, diffRequest).getDistance())
                .as("Correct result distance not calculated")
                .isEqualTo(1);
    }
}
