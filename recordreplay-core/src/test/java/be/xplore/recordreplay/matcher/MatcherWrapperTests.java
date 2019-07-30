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

public class MatcherWrapperTests {

    private Request request;
    private Response response;
    private Repository repo;
    private MatcherWrapper matcherWrapper;

    @Before
    public void initStubRepo() {
        request = Request.Builder.get("Test").build();
        response = Response.Builder.ok().body("ikkel").build();
        repo = new MemoryRepository();
        repo.add(new Stub(request,response));
    }

    @Before
    public void initMatcherWrapper(){
        matcherWrapper = new MatcherWrapper(List.of(
                new RequestMethodMatcher(false),
                new RequestPathMatcher(false),
                new RequestHeaderMatcher(false),
                new RequestParamMatcher(false),
                new RequestBodyMatcher(false)));
    }

    @Test
    public void matcherWrapperShouldReturnOptional() {
        assertThat(matcherWrapper.getResponse(request, repo.find()))
                .as("MatcherWrapper doesn't return optional of response")
                .isEqualTo(Optional.of(response));
    }

}
