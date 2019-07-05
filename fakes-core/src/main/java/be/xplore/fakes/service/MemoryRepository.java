package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.except.InvalidStubException;

import java.util.ArrayList;
import java.util.List;

public class MemoryRepository implements Repository {
    private final List<Stub> stubs;

    public MemoryRepository() {
        this.stubs = new ArrayList<>();
    }

    @Override
    public void add(Stub stub) throws InvalidStubException{
        if (stub == null){
            throw new InvalidStubException("Repositories do not accept null Stubs!");
        }
        stubs.add(stub);
    }

    @Override
    public List<Stub> find() {
        return stubs;
    }
}
