package be.xplore.recordreplay.repo;

import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.RequestMethod;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Stub;
import be.xplore.recordreplay.service.MemoryRepository;
import be.xplore.recordreplay.service.Repository;
import be.xplore.recordreplay.service.RequestBodyMatcher;
import be.xplore.recordreplay.service.RequestHeaderMatcher;
import be.xplore.recordreplay.service.RequestMatcher;
import be.xplore.recordreplay.service.RequestMethodMatcher;
import be.xplore.recordreplay.service.RequestParamMatcher;
import be.xplore.recordreplay.service.RequestPathMatcher;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryRepositoryTests {

    private static final Stub STUB = new Stub(
            Request.builder().method(RequestMethod.GET).path("/abc").build(),
            Response.ok());
    private final List<RequestMatcher> matchers = new ArrayList<>();
    private Repository repo;

    @Before
    public void initRepo() {
        repo = new MemoryRepository();
    }

    @Before
    public void initMatchers() {
        matchers.add(new RequestHeaderMatcher());
        matchers.add(new RequestParamMatcher());
        matchers.add(new RequestBodyMatcher());
        matchers.add(new RequestMethodMatcher());
        matchers.add(new RequestPathMatcher());
    }

    @Test
    public void addValidStub() {
        repo.add(STUB);
        assertThat(repo.find().size()).isEqualTo(1);
        assertThat(repo.find().get(0)).isEqualTo(STUB);
    }

    @Test
    public void findExactResponseShouldReturnOptionalResponse() {
        repo.add(STUB);
        assertThat(repo.findExactResponse(STUB.getRequest(), matchers)).isEqualTo(Optional.of(STUB.getResponse()));
    }

    @Test(expected = NullPointerException.class)
    public void addNullStubShouldExcept() {
        Repository repo = new MemoryRepository();
        repo.add(null);
    }
}
