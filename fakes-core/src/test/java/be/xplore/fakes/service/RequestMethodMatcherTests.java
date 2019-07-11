package be.xplore.fakes.service;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.RequestMethod;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestMethodMatcherTests {

    private final RequestMatcher matcher = new RequestMethodMatcher();

    private final Request sameRequest1 = Request.builder().method(RequestMethod.GET).build();
    private final Request sameRequest2 = Request.builder().method(RequestMethod.GET).build();
    private final Request diffRequest = Request.builder().method(RequestMethod.POST).build();

    @Test
    public void requestMethodMatcherShouldReturnResultZeroOnMatch() {
        assertThat(matcher.matches(sameRequest1, sameRequest2).getDistance())
                .as("Correct result distance not calculated")
                .isEqualTo(0);
    }

    @Test
    public void requestMethodMatcherShouldReturnResultOneOnCompleteMismatch() {
        assertThat(matcher.matches(sameRequest1, diffRequest).getDistance())
                .as("Correct result distance not calculated")
                .isEqualTo(1);
    }

}
