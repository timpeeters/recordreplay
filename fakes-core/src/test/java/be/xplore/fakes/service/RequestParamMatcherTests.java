package be.xplore.fakes.service;


import be.xplore.fakes.model.Request;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class RequestParamMatcherTests {

    private static final String SAME_KEY_1 = "paramKey1";
    private static final String SAME_KEY_2 = "paramKey2";

    private static Map<String, List<String>> sameParams1 = new HashMap<>();
    private static Map<String, List<String>> sameParams2 = new HashMap<>();
    private static Map<String, List<String>> diffParams = new HashMap<>();
    private static Map<String, List<String>> halfParams = new HashMap<>();

    private static List<String> sameParamValues = new ArrayList<>();
    private static List<String> diffParamValues = new ArrayList<>();
    private static List<String> halfParamValues = new ArrayList<>();

    private static Request sameRequest1;
    private static Request sameRequest2;
    private static Request diffRequest;
    private static Request halfRequest;

    private final RequestMatcher matcher = new RequestParamMatcher();


    @BeforeClass
    public static void fillSameParamValues() {
        sameParamValues.add("test1");
        sameParamValues.add("test2");
        sameParamValues.add("test3");
        sameParamValues.add("test4");
    }

    @BeforeClass
    public static void fillDiffParamValues() {
        diffParamValues.add("test5");
        diffParamValues.add("test6");
    }

    @BeforeClass
    public static void fillHalfParamValues() {
        halfParamValues.add("test1");
        halfParamValues.add("test2");
    }

    @BeforeClass
    public static void fillSameParamMaps() {
        sameParams1.put(SAME_KEY_1, sameParamValues);
        sameParams1.put(SAME_KEY_2, sameParamValues);
        sameParams2.put(SAME_KEY_1, sameParamValues);
        sameParams2.put(SAME_KEY_2, sameParamValues);
    }

    @BeforeClass
    public static void fillUpDiffParamList() {
        diffParams.put(SAME_KEY_1, diffParamValues);
        diffParams.put("paramKey3", diffParamValues);
        halfParams.put(SAME_KEY_1, halfParamValues);
        halfParams.put(SAME_KEY_2, halfParamValues);
    }

    @BeforeClass
    public static void initRequests() {
        sameRequest1 = Request.Builder.post("").params(sameParams1).build();
        sameRequest2 = Request.Builder.post("").params(sameParams2).build();
        diffRequest  = Request.Builder.post("").params(diffParams).build();
        halfRequest  = Request.Builder.post("").params(halfParams).build();
    }

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
