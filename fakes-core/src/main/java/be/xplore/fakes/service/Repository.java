package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.except.InvalidStubException;

import java.util.List;

public interface Repository {
    void add(Stub stub) throws InvalidStubException;

    List<Stub> find();
}
