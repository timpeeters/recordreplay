package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.except.InvalidStubException;
import be.xplore.fakes.service.except.RepositoryException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MemoryRepositoryTests {
    @Test
    public void addValidStub() throws InvalidStubException, RepositoryException {
        Repository repo = new MemoryRepository();
        repo.add(new Stub());
        assertEquals(1, repo.find().size());
    }

    @Test(expected = InvalidStubException.class)
    public void addNullStubShouldExcept() throws InvalidStubException, RepositoryException {
        Repository repo = new MemoryRepository();
        repo.add(null);
    }
}
