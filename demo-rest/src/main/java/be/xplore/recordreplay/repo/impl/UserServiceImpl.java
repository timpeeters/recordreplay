package be.xplore.recordreplay.repo.impl;

import be.xplore.recordreplay.model.User;
import be.xplore.recordreplay.repository.UserRepo;
import be.xplore.recordreplay.repo.UserService;
import be.xplore.recordreplay.repo.exceptions.UserNotFoundException;
import be.xplore.recordreplay.repo.exceptions.UserValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public User searchUserById(Long id) throws UserValidationException {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return userOptional.get();
    }

    @Override
    public User createUser(User user) throws UserValidationException {
        validateUser(user);
        return userRepo.save(user);
    }

    @Override
    public User updateUser(User user) throws UserValidationException {
        validateUser(user);
        return userRepo.save(user);
    }

    @Override
    public void deleteUserById(Long id) throws UserValidationException {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserValidationException("User not found for given id");
        }
        userRepo.deleteById(id);
    }

    private void validateUser(User user) throws UserValidationException {
        if (user == null) {
            throw new UserValidationException("User passed can not be null");
        }
    }

}
