package be.xplore.recordreplay.matcher;

import be.xplore.recordreplay.model.QueryParams;
import be.xplore.recordreplay.model.Request;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class RequestParamMatcherTests {

    private static final String KEY = "Key";
    private final RequestMatcher matcher = new RequestParamMatcher(false);

    private final QueryParams sameQueryParams1 =
            QueryParams.builder().params(Map.of(KEY, List.of("Test1", "Test2"))).build();
    private final QueryParams sameQueryParams2 =
            QueryParams.builder().params(Map.of(KEY, List.of("Test1", "Test2"))).build();
    private final QueryParams diffQueryParams =
            QueryParams.builder().params(Map.of(KEY, List.of("Test3", "Test4", "Test5"))).build();
    private final QueryParams halfQueryParams =
            QueryParams.builder().params(Map.of(KEY, List.of("Test1", "Test8"))).build();

    private final Request sameRequest1 = Request.Builder.post("").queryParams(sameQueryParams1).build();
    private final Request sameRequest2 = Request.Builder.post("").queryParams(sameQueryParams2).build();
    private final Request diffRequest = Request.Builder.post("").queryParams(diffQueryParams).build();
    private final Request halfRequest = Request.Builder.post("").queryParams(halfQueryParams).build();

    @Test
    public void requestParamMatcherShouldReturnZeroOnTwoEmptyParams() {
        assertThat(matcher.matches(Request.Builder.post("").queryParams(QueryParams.EMPTY).build(),
                Request.Builder.post("").queryParams(QueryParams.EMPTY).build()).getDistance())
                .as("Correct result distance not calculated on two empty params")
                .isEqualTo(0);
    }

    @Test
    public void requestParamMatcherShouldReturnOneOnEmptyParam() {
        assertThat(matcher.matches(Request.Builder
                .post("")
                .queryParams(QueryParams.EMPTY)
                .build(), sameRequest1).getDistance())
                .as("Correct result distance not calculated on single empty param\"")
                .isEqualTo(1);
    }

    @Test
    public void requestParamMatcherShouldReturnResultZeroOnCompleteMatch() {
        assertThat(matcher.matches(sameRequest1, sameRequest2).getDistance())
                .as("Correct result distance not calculated on match")
                .isEqualTo(0);
    }


    @Test
    public void requestParamMatcherShouldReturnResultOneOnCompleteMismatch() {
        assertThat(matcher.matches(sameRequest1, diffRequest).getDistance())
                .as("Correct result distance not calculated on mismatch")
                .isEqualTo(1);
    }

    @Test
    public void requestParamMatcherShouldReturnResultHalfOnHalfMatch() {
        assertThat(matcher.matches(sameRequest1, halfRequest).getDistance())
                .as("Correct result distance not calculated on half match")
                .isEqualTo(0.5);
    }

    @Test
    public void sameResultOnReversedParameters() {
        assertThat(matcher.matches(diffRequest, sameRequest1).getDistance())
                .as("Correct result distance not calculated on reversed parameters")
                .isEqualTo(1);
    }

}
