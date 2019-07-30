package be.xplore.recordreplay.repository;

import be.xplore.recordreplay.model.Request;
import be.xplore.recordreplay.model.RequestMethod;
import be.xplore.recordreplay.model.Response;
import be.xplore.recordreplay.model.Stub;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryRepositoryTests {

    private static final Stub STUB = new Stub(
            Request.builder().method(RequestMethod.GET).path("/abc").build(),
            Response.ok());
    private Repository repo;

    @Before
    public void initRepo() {
        repo = new MemoryRepository();
    }

    @Test
    public void addValidStub() {
        repo.add(STUB);
        assertThat(repo.find().size()).isEqualTo(1);
        assertThat(repo.find().get(0)).isEqualTo(STUB);
    }

    @Test
    public void findStubs() {
        repo.add(STUB);
        assertThat(repo.find())
                .as("Repo does not return list")
                .isInstanceOf(List.class);
    }

    @Test(expected = NullPointerException.class)
    public void addNullStubShouldExcept() {
        Repository repo = new MemoryRepository();
        repo.add(null);
    }
}
