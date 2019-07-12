package be.xplore.fakes.service;

import be.xplore.fakes.model.Headers;
import be.xplore.fakes.model.Request;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeaderMatcherTests {

    private static final String KEY = "Key";
    private final RequestMatcher matcher = new RequestHeaderMatcher();

    private final Headers sameHeaders1 = Headers.builder().headerMap(Map.of(KEY, List.of("Test1", "Test2"))).build();
    private final Headers sameHeaders2 = Headers.builder().headerMap(Map.of(KEY, List.of("Test1", "Test2"))).build();
    private final Headers diffHeaders = Headers.builder().headerMap(Map.of(KEY, List.of("Test3", "Test4"))).build();
    private final Headers halfHeaders = Headers.builder().headerMap(Map.of(KEY, List.of("Test1", "Test8"))).build();
    private final Request sameRequest1 = Request.Builder.get("").headers(sameHeaders1).build();
    private final Request sameRequest2 = Request.Builder.get("").headers(sameHeaders2).build();
    private final Request diffRequest = Request.Builder.get("").headers(diffHeaders).build();
    private final Request halfRequest = Request.Builder.get("").headers(halfHeaders).build();

    @Test
    public void requestHeaderMatcherShouldReturnResultZeroOnCompleteMatch() {
        assertThat(matcher.matches(sameRequest1, sameRequest2).getDistance())
                .as("Correct result distance not calculated")
                .isEqualTo(0);
    }

    @Test
    public void requestHeaderMatcherShouldReturnResultOneOnCompleteMismatch() {
        assertThat(matcher.matches(sameRequest1, diffRequest).getDistance())
                .as("Correct result distance not calculated")
                .isEqualTo(1);
    }

    @Test
    public void requestHeaderMatcherShouldReturnResultHalfOnHalfMatch() {
        assertThat(matcher.matches(sameRequest1, halfRequest).getDistance())
                .as("Correct result distance not calculated")
                .isEqualTo(0.5);
    }
}
