package be.xplore.fakes.service;

import be.xplore.fakes.model.QueryParams;
import be.xplore.fakes.model.Request;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class RequestParamMatcherTests {

    private static final String KEY = "Key";
    private final RequestMatcher matcher = new RequestParamMatcher();

    private final QueryParams sameQueryParams1 =
            QueryParams.builder().params(Map.of(KEY, List.of("Test1", "Test2"))).build();
    private final QueryParams sameQueryParams2 =
            QueryParams.builder().params(Map.of(KEY, List.of("Test1", "Test2"))).build();
    private final QueryParams diffQueryParams =
            QueryParams.builder().params(Map.of(KEY, List.of("Test3", "Test4"))).build();
    private final QueryParams halfQueryParams =
            QueryParams.builder().params(Map.of(KEY, List.of("Test1", "Test8"))).build();

    private final Request sameRequest1 = Request.Builder.post("").queryParams(sameQueryParams1).build();
    private final Request sameRequest2 = Request.Builder.post("").queryParams(sameQueryParams2).build();
    private final Request diffRequest = Request.Builder.post("").queryParams(diffQueryParams).build();
    private final Request halfRequest = Request.Builder.post("").queryParams(halfQueryParams).build();


    @Test
    public void requestParamMatcherShouldReturnResultZeroOnCompleteMatch() {
        assertThat(matcher.matches(sameRequest1, sameRequest2).getDistance())
                .as("Correct result distance not calculated")
                .isEqualTo(0);
    }

    @Test
    public void requestParamMatcherShouldReturnResultOneOnCompleteMismatch() {
        assertThat(matcher.matches(sameRequest1, diffRequest).getDistance())
                .as("Correct result distance not calculated")
                .isEqualTo(1);
    }

    @Test
    public void requestParamMatcherShouldReturnResultHalfOnHalfMatch() {
        assertThat(matcher.matches(sameRequest1, halfRequest).getDistance())
                .as("Correct result distance not calculated")
                .isEqualTo(0.5);
    }

}
