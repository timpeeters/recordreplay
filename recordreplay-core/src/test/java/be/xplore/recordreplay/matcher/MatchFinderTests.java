package be.xplore.recordreplay.matcher;

import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Stub;
import be.xplore.recordreplay.repository.MemoryRepository;
import be.xplore.recordreplay.repository.Repository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchFinderTests {

    private Request request;
    private Response response;
    private Repository repo;
    private MatchFinder bestMatchFinder;
    private MatchFinder exactMatchFinder;

    @Before
    public void initStubRepo() {
        request = Request.Builder.get("Test").build();
        response = Response.Builder.ok().body("ikkel").build();
        repo = new MemoryRepository();
        repo.add(new Stub(request, response));
    }

    @Before
    public void initBestMatcherWrapper() {
        bestMatchFinder = new MatchFinder(List.of(
                new RequestMethodMatcher(false),
                new RequestPathMatcher(false),
                new RequestHeaderMatcher(false),
                new RequestParamMatcher(false),
                new RequestBodyMatcher(false)));
    }

    @Before
    public void initExactMatcherWrapper() {
        exactMatchFinder = new MatchFinder(List.of(
                new RequestMethodMatcher(true),
                new RequestPathMatcher(true),
                new RequestHeaderMatcher(true),
                new RequestParamMatcher(true),
                new RequestBodyMatcher(true)));
    }

    @Test
    public void matcherWrapperShouldReturnOptional() {
        assertThat(bestMatchFinder.getResponse(request, repo.find()))
                .as("MatcherWrapper doesn't return optional of response")
                .isEqualTo(Optional.of(response));
    }

    @Test
    public void matcherWrapperShouldReturnEmptyOptional() {
        assertThat(exactMatchFinder.getResponse(Request.Builder.post("test").build(), repo.find()))
                .as("MatcherWrapper doesn't return empty optional")
                .isEmpty();
    }

}
