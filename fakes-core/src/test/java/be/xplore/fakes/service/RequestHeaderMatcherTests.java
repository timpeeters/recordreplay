package be.xplore.fakes.service;

import be.xplore.fakes.model.Request;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeaderMatcherTests {

    private static final String SAME_KEY_1 = "headerKey1";
    private static final String SAME_KEY_2 = "headerKey2";

    private static Map<String, List<String>> sameHeaders1 = new HashMap<>();
    private static Map<String, List<String>> sameHeaders2 = new HashMap<>();
    private static Map<String, List<String>> diffHeaders = new HashMap<>();
    private static Map<String, List<String>> halfHeaders = new HashMap<>();

    private static List<String> sameHeaderValues = new ArrayList<>();
    private static List<String> diffHeaderValues = new ArrayList<>();
    private static List<String> halfHeaderValues = new ArrayList<>();

    private static Request sameRequest1;
    private static Request sameRequest2;
    private static Request diffRequest;
    private static Request halfRequest;

    private final RequestMatcher matcher = new RequestHeaderMatcher();


    @BeforeClass
    public static void fillSameHeaderValues() {
        sameHeaderValues.add("test1");
        sameHeaderValues.add("test2");
        sameHeaderValues.add("test3");
        sameHeaderValues.add("test4");
    }

    @BeforeClass
    public static void fillDiffHeaderValues() {
        diffHeaderValues.add("test5");
        diffHeaderValues.add("test6");
    }

    @BeforeClass
    public static void fillHalfHeaderValues() {
        halfHeaderValues.add("test1");
        halfHeaderValues.add("test2");
    }

    @BeforeClass
    public static void fillSameHeaderMaps() {
        sameHeaders1.put(SAME_KEY_1, sameHeaderValues);
        sameHeaders1.put(SAME_KEY_2, sameHeaderValues);
        sameHeaders2.put(SAME_KEY_1, sameHeaderValues);
        sameHeaders2.put(SAME_KEY_2, sameHeaderValues);
    }

    @BeforeClass
    public static void fillUpDiffHeaderList() {
        diffHeaders.put(SAME_KEY_1, diffHeaderValues);
        diffHeaders.put("headerKey3", diffHeaderValues);
        halfHeaders.put(SAME_KEY_1, halfHeaderValues);
        halfHeaders.put(SAME_KEY_2, halfHeaderValues);
    }

    @BeforeClass
    public static void initRequests() {
        sameRequest1 = Request.Builder.post("").headers(sameHeaders1).build();
        sameRequest2 = Request.Builder.post("").headers(sameHeaders2).build();
        diffRequest  = Request.Builder.post("").headers(diffHeaders).build();
        halfRequest  = Request.Builder.post("").headers(halfHeaders).build();
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
}
