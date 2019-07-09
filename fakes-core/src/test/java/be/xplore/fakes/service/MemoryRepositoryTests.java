package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MemoryRepositoryTests {

    private Repository repo;

    @Before
    public void initRepo() {
        repo = new MemoryRepository();
    }

    @Test
    public void addValidStub() {
        repo.add(new Stub());
        assertEquals(1, repo.find().size());
    }

    @Test(expected = NullPointerException.class)
    public void addNullStubShouldExcept() {
        Repository repo = new MemoryRepository();
        repo.add(null);
    }
}
