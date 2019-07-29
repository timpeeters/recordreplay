package be.xplore.recordreplay.testdemo.repository;

import be.xplore.recordreplay.testdemo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo {
    Optional<User> findById(long id);

    List<User> findAll();

    User save(User user);

    void deleteById(long id);
}
