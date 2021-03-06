package be.xplore.recordreplay.testdemo.repository.impl;


import be.xplore.recordreplay.testdemo.model.User;
import be.xplore.recordreplay.testdemo.repository.UserRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepoImpl implements UserRepo {

    private final List<User> users;

    public UserRepoImpl() {
        users = new ArrayList<>();
        save(new User().setFirstName("John").setLastName("Doe").setRole("unknown"));
    }

    @Override
    public Optional<User> findById(long id) {
        if (id >= users.size()) {
            return Optional.empty();
        }
        return Optional.of(users.get((int) id));
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(users);
    }

    @Override
    public User save(User user) {
        users.add(new User(user)
                .setId(users.size()));
        return new User(users.get(users.size() - 1));
    }

    @Override
    public void deleteById(long id) {
        if (id < users.size()) {
            users.remove((int) id);
        }
    }
}
