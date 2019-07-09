package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryRepositoryTests {

    private Repository repo;

    @Before
    public void initRepo() {
        repo = new MemoryRepository();
    }

    @Test
    public void addValidStub() {
        repo.add(new Stub());
        assertThat(repo.find().size()).isEqualTo(1);
    }

    @Test(expected = NullPointerException.class)
    public void addNullStubShouldExcept() {
        Repository repo = new MemoryRepository();
        repo.add(null);
    }
}
