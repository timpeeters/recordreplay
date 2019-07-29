package be.xplore.recordreplay.testdemo.service;


import be.xplore.recordreplay.testdemo.model.User;

import java.util.List;

public interface UserService {

    List<User> searchUsers();

    User searchUserById(Long id);

    User createUser(User user);

    User updateUser(User user);

    void deleteUserById(Long id);

}
