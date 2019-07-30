package be.xplore.recordreplay.repository;

import be.xplore.recordreplay.model.Stub;

import java.util.List;

public interface Repository {
    void add(Stub stub);

    List<Stub> find();

}
