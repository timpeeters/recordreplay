package be.xplore.fakes.service;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.model.RequestMethod;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestMethodMatcherTests {

    private final RequestMatcher matcher = new RequestMethodMatcher();

    private final Request sameRequest1 = new Request()
            .setMethod(RequestMethod.GET);
    private final Request sameRequest2 = new Request()
            .setMethod(RequestMethod.GET);
    private final Request diffRequest = new Request()
            .setMethod(RequestMethod.POST);

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
