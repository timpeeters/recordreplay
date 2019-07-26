package be.xplore.recordreplay.service;

import be.xplore.recordreplay.model.User;
import be.xplore.recordreplay.service.exceptions.UserValidationException;

import java.util.List;

public interface UserService {

    List<User> searchUsers();

    User searchUserById(Long id) throws UserValidationException;

    User createUser(User user) throws UserValidationException;

    User updateUser(User user) throws UserValidationException;

    void deleteUserById(Long id) throws UserValidationException;

}
