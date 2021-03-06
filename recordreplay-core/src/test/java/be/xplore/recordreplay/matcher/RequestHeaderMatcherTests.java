package be.xplore.recordreplay.matcher;

import be.xplore.recordreplay.model.Headers;
import be.xplore.recordreplay.model.Request;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeaderMatcherTests {

    private static final String KEY = "Key";
    private final RequestMatcher matcher = new RequestHeaderMatcher(false);
    private final RequestMatcher exactMatcher = new RequestHeaderMatcher(true);

    private final Headers sameHeaders1 = Headers.builder().headerMap(Map.of(KEY, List.of("Test1", "Test2"))).build();
    private final Headers sameHeaders2 = Headers.builder().headerMap(Map.of(KEY, List.of("Test1", "Test2"))).build();
    private final Headers diffHeaders =
            Headers.builder().headerMap(Map.of(KEY, List.of("Test3", "Test4", "Test5"))).build();
    private final Headers halfHeaders = Headers.builder().headerMap(Map.of(KEY, List.of("Test1", "Test8"))).build();
    private final Request sameRequest1 = Request.Builder.get("").headers(sameHeaders1).build();
    private final Request sameRequest2 = Request.Builder.get("").headers(sameHeaders2).build();
    private final Request diffRequest = Request.Builder.get("").headers(diffHeaders).build();
    private final Request halfRequest = Request.Builder.get("").headers(halfHeaders).build();

    @Test
    public void requestParamMatcherShouldReturnZeroOnTwoEmptyHeaders() {
        assertThat(matcher.matches(Request.Builder.post("").headers(Headers.EMPTY).build(),
                Request.Builder.post("").headers(Headers.EMPTY).build()).getDistance())
                .as("Correct result distance not calculated on two empty params")
                .isEqualTo(0);
    }

    @Test
    public void requestParamMatcherShouldReturnOneOnEmptyHeaders() {
        Request emptyHeadersRequest = Request.Builder
                .post("")
                .headers(Headers.EMPTY)
                .build();
        assertThat(matcher.matches(emptyHeadersRequest, sameRequest1).getDistance())
                .as("Correct result distance not calculated on single empty param\"")
                .isEqualTo(1);
        assertThat(matcher.matches(sameRequest1, emptyHeadersRequest).getDistance())
                .as("Correct result distance not calculated on single empty param\"")
                .isEqualTo(1);
    }


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

    @Test(expected = NoExactMatchFoundException.class)
    public void exactMatcherShouldThrowOnNonExactMatch() {
        exactMatcher.matches(sameRequest1, diffRequest);
    }


    @Test
    public void sameResultOnReversedParameters() {
        assertThat(matcher.matches(diffRequest, sameRequest1).getDistance())
                .as("Correct result distance not calculated on reversed parameters")
                .isEqualTo(1);
    }

    @Test
    public void ignoredHeadersAreIgnored() {
        RequestHeaderMatcher matcher = new RequestHeaderMatcher(List.of("content-type"), true);
        Headers headers = Headers.builder().applicationJson().header("azerty", "ytreza").build();
        Headers headers2 = Headers.builder().applicationXml().header("azerty", "ytreza").build();
        assertThat(matcher
                .matches(Request.Builder.get("").headers(headers).build(), Request.Builder.get("").headers(headers2)
                        .build()).getDistance()).isEqualTo(0);
    }

}
