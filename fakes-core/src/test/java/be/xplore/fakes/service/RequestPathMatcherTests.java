package be.xplore.fakes.service;

import be.xplore.fakes.model.Request;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestPathMatcherTests {

    private final RequestMatcher matcher = new RequestPathMatcher();

    private final Request sameRequest1 = Request.builder().path("/test").build();
    private final Request sameRequest2 = Request.builder().path("/test").build();
    private final Request diffRequest = Request.builder().path("/diff").build();

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
