package be.xplore.recordreplay.matcher;

import be.xplore.recordreplay.model.Request;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestPathMatcherTests {

    private final RequestMatcher matcher = new RequestPathMatcher();

    private Request sameRequest1;
    private Request sameRequest2;
    private Request diffRequest;

    @Before
    public void setupTest() {
        sameRequest1 = Request.Builder.put("/same").build();
        sameRequest2 = Request.Builder.put("/same").build();
        diffRequest  = Request.Builder.get("/diff").build();
    }

    @Test
    public void requestPathMatcherShouldReturnResultZeroOnMatch(){
        assertThat(matcher.matches(sameRequest1,sameRequest2).getDistance())
                .as("Correct result distance not calculated")
                .isEqualTo(0);
    }

    @Test
    public void requestPathMatcherShouldReturnResultOneOnCompleteMismatch(){
        assertThat(matcher.matches(sameRequest1,diffRequest).getDistance())
                .as("Correct result distance not calculated")
                .isEqualTo(1);
    }
}
