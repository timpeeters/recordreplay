package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MemoryRepositoryTests {
    @Test
    public void addValidStub() {
        Repository repo = new MemoryRepository();
        repo.add(new Stub());
        assertEquals(1, repo.find().size());
    }

    @Test(expected = NullPointerException.class)
    public void addNullStubShouldExcept() {
        Repository repo = new MemoryRepository();
        repo.add(null);
    }
}
