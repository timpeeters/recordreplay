package be.xplore.fakes.service;

import be.xplore.fakes.model.Request;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeaderMatcherTests {

    private static List<String> sameHeaders1 = new ArrayList<>();
    private static List<String> sameHeaders2 = new ArrayList<>();
    private static List<String> diffHeaders = new ArrayList<>();
    private static List<String> halfHeaders = new ArrayList<>();

    private static Request sameRequest1;
    private static Request sameRequest2;
    private static Request diffRequest;
    private static Request halfRequest;

    private final RequestMatcher matcher = new RequestHeaderMatcher();

    @BeforeClass
    public static void fillSameHeaderLists() {
        sameHeaders1.add("headerTest1");
        sameHeaders1.add("headerTest2");
        sameHeaders2.add("headerTest1");
        sameHeaders2.add("headerTest2");
    }

    @BeforeClass
    public static void fillUpDiffHeaderList() {
        diffHeaders.add("headerTest3");
        diffHeaders.add("headerTest4");
        halfHeaders.add("headerTest1");
        halfHeaders.add("headerTest6");
    }

    @BeforeClass
    public static void initRequests() {
        sameRequest1 = new Request()
                .setHeaders(sameHeaders1);
        sameRequest2 = new Request()
                .setHeaders(sameHeaders2);
        diffRequest = new Request()
                .setHeaders(diffHeaders);
        halfRequest = new Request()
                .setHeaders(halfHeaders);
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
