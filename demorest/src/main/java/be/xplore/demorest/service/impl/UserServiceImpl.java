package be.xplore.demorest.service.impl;

import be.xplore.demorest.model.User;
import be.xplore.demorest.repository.UserRepo;
import be.xplore.demorest.service.UserService;
import be.xplore.demorest.service.exceptions.UserValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> searchUsers(Map<String, String> filters) {
        if (filters == null || filters.isEmpty()) {
            return userRepo.findAll();
        } else {
            return userRepo.findAll(buildExample(filters));
        }
    }

    @Override
    public User searchUserById(Long id) throws UserValidationException {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserValidationException("User not found for given id");
        }
        return userOptional.get();
    }

    @Override
    public User createUser(User user) throws UserValidationException {
        validateUser(user, true);
        return userRepo.save(user);
    }

    @Override
    public User updateUser(User user) throws UserValidationException {
        validateUser(user, false);
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

    private void validateUser(User user, boolean isNew) throws UserValidationException {
        if (user == null) {
            throw new UserValidationException("User passed can not be null");
        }
        if (isNew && (user.getId() != null)) {
            throw new UserValidationException("UserID can not have a value when calling the new user endpoint");
        }
        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            throw new UserValidationException("Invalid FirstName");
        }
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            throw new UserValidationException("Invalid LastName");
        }
    }

    private Example<User> buildExample(Map<String, String> filters) {
        User example = exampleBuilder(filters);
        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase(true)
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        return Example.of(example, matcher);
    }

    private User exampleBuilder(Map<String, String> filters) {
        User example = new User();
        if (filters.containsKey("firstname")) {
            example.setFirstName(filters.get("firstname"));
        }
        if (filters.containsKey("lastname")) {
            example.setLastName(filters.get("lastname"));
        }
        if (filters.containsKey("role")) {
            example.setRole(filters.get("role"));
        }
        return example;
    }
}
