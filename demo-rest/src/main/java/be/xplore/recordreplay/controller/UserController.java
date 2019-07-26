package be.xplore.recordreplay.controller;

import be.xplore.recordreplay.model.User;
import be.xplore.recordreplay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/list")
    public ResponseEntity findAllUsers() {
        List<User> users = userService.searchUsers();
        return ResponseEntity.ok().body(users);
    }

    @SuppressWarnings("checkstyle:ReturnCount")
    @GetMapping("/user")
    public ResponseEntity findUserById(@RequestParam Long id) {
        User foundUser;
        try {
            foundUser = userService.searchUserById(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundUser);
    }

    @PostMapping("/user/create")
    public ResponseEntity createUser(@Valid @RequestBody User user) {
        User createdUser;
        try {
            createdUser = userService.createUser(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
        return ResponseEntity.ok().body(createdUser);
    }

    @PutMapping("/user/update")
    public ResponseEntity updateUser(@Valid @RequestBody(required = true) User user) {
        User updatedUser;
        try {
            updatedUser = userService.updateUser(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity deleteUser(@RequestParam Long id) {
        try {
            userService.deleteUserById(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("User deleted successfully");
    }

}
