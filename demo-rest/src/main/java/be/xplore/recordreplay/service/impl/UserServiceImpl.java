package be.xplore.recordreplay.service.impl;

import be.xplore.recordreplay.model.User;
import be.xplore.recordreplay.repository.UserRepo;
import be.xplore.recordreplay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        validateUser(user);
        return userRepo.save(user);
    }

    @Override
    public User updateUser(User user) {
        validateUser(user);
        return userRepo.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found for given id");
        }
        userRepo.deleteById(id);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User passed can not be null");
        }
    }

}
