package be.xplore.demorest.service;

import be.xplore.demorest.model.User;
import be.xplore.demorest.service.exceptions.UserValidationException;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<User> searchUsers(Map<String, String> filters);

    User searchUserById(Long id) throws UserValidationException;

    User createUser(User user) throws UserValidationException;

    User updateUser(User user) throws UserValidationException;

    void deleteUserById(Long id) throws UserValidationException;


}
