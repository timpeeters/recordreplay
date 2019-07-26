package be.xplore.recordreplay.repo;

import be.xplore.fakes.model.Stub;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemoryRepository implements Repository {
    private final List<Stub> stubs;

    public MemoryRepository() {
        this.stubs = new ArrayList<>();
    }

    @Override
    public void add(Stub stub) {
        Objects.requireNonNull(stub, "Stub is null!");
        stubs.add(stub);
    }

    @Override
    public List<Stub> find() {
        return stubs;
    }
}
