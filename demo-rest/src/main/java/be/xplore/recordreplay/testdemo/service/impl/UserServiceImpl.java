package be.xplore.recordreplay.testdemo.service.impl;

import be.xplore.recordreplay.testdemo.model.User;
import be.xplore.recordreplay.testdemo.repository.UserRepo;
import be.xplore.recordreplay.testdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> searchUsers() {
        return userRepo.findAll();
    }

    @Override
    public User searchUserById(Long id) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isEmpty()) {
            throw new NoSuchElementException(String.valueOf(id));
        }
        return userOptional.get();
    }

    @Override
    public User createUser(User user) {
        return userRepo.save(requireNonNull(user));
    }

    @Override
    public User updateUser(User user) {
        return userRepo.save(requireNonNull(user));
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found for given id");
        }
        userRepo.deleteById(id);
    }

}
