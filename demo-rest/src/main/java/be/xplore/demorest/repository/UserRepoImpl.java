package be.xplore.demorest.repository;

import be.xplore.demorest.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepoImpl implements UserRepo {

    private final List<User> users;

    public UserRepoImpl() {
        users = new ArrayList<>();
        save(new User().setFirstName("Stijn").setLastName("Schack").setRole(""));
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.of(users.get((int) id));
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(users);
    }

    @Override
    public User save(User user) {
        users.add(new User(user)
                .setId((long) users.size()));
        return new User(users.get(users.size() - 1));
    }

    @Override
    public void deleteById(long id) {
        if (id < users.size()) {
            users.remove((int) id);
        }
    }
}
