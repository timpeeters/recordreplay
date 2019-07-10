package be.xplore.demorest.repository;

import be.xplore.demorest.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo {
    Optional<User> findById(long id);

    List<User> findAll();

    User save(User user);

    void deleteById(long id);
}
