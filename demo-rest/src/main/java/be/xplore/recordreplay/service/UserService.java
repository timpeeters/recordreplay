package be.xplore.recordreplay.service;

import be.xplore.recordreplay.model.User;

import java.util.List;

public interface UserService {

    List<User> searchUsers();

    User searchUserById(Long id);

    User createUser(User user);

    User updateUser(User user);

    void deleteUserById(Long id);

}
