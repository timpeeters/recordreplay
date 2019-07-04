package be.xplore.fakes.service;

import be.xplore.fakes.model.Stub;

import java.util.List;

public interface Repository {
    void add(Stub stub) throws NullPointerException;

    List<Stub> find();
}
