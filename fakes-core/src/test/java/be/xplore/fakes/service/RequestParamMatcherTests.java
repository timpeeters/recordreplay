package be.xplore.fakes.service;


import be.xplore.fakes.model.Request;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class RequestParamMatcherTests {

    private static List<String> sameParams1 = new ArrayList<>();
    private static List<String> sameParams2 = new ArrayList<>();
    private static List<String> diffParams = new ArrayList<>();
    private static List<String> halfParams = new ArrayList<>();

    private static Request sameRequest1;
    private static Request sameRequest2;
    private static Request diffRequest;
    private static Request halfRequest;

    private final RequestMatcher matcher = new RequestParamMatcher();


    @BeforeClass
    public static void setUpSameParamLists() {
        sameParams1.add("paramTest1");
        sameParams1.add("paramTest2");
        sameParams2.add("paramTest1");
        sameParams2.add("paramTest2");
    }

    @BeforeClass
    public static void setUpDiffParamLists() {
        diffParams.add("paramTest3");
        diffParams.add("paramTest4");
        halfParams.add("paramTest1");
        halfParams.add("paramTest6");
    }

    @BeforeClass
    public static void initRequests() {
        sameRequest1 = new Request()
                .setParams(sameParams1);
        sameRequest2 = new Request()
                .setParams(sameParams2);
        diffRequest = new Request()
                .setParams(diffParams);
        halfRequest = new Request()
                .setParams(halfParams);
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
