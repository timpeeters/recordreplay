package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;

import java.util.ArrayList;
import java.util.List;

public class MemoryRepository implements Repository {
    private List<Stub> stubs;

    public MemoryRepository() {
        this.stubs = new ArrayList<>();
    }

    @Override
    public void add(Stub stub) throws NullPointerException {
        if (stub == null){
            throw new NullPointerException("Repositories do not accept null Stubs!");
        }
        stubs.add(stub);
    }

    @Override
    public List<Stub> find() {
        return stubs;
    }
}
