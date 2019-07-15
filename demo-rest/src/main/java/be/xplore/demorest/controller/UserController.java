package be.xplore.demorest.controller;

import be.xplore.demorest.model.User;
import be.xplore.demorest.service.UserService;
import be.xplore.demorest.service.exceptions.UserValidationException;
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

    @GetMapping("/user")
    public ResponseEntity findUserById(@RequestParam Long id) {
        User foundUser;
        try {
            foundUser = userService.searchUserById(id);
        } catch (UserValidationException uve) {
            return ResponseEntity.badRequest().body(uve.getMessage());
        }
        return ResponseEntity.ok(foundUser);
    }

    @PostMapping("/user/create")
    public ResponseEntity createUser(@Valid @RequestBody User user) {
        User createdUser;
        try {
            createdUser = userService.createUser(user);
        } catch (UserValidationException uve) {
            return ResponseEntity.unprocessableEntity().body(uve.getMessage());
        }
        return ResponseEntity.ok().body(createdUser);
    }

    @PutMapping("/user/update")
    public ResponseEntity updateUser(@Valid @RequestBody(required = true) User user) {
        User updatedUser;
        try {
            updatedUser = userService.updateUser(user);
        } catch (UserValidationException uve) {
            return ResponseEntity.unprocessableEntity().body(uve.getMessage());
        }
        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity deleteUser(@RequestParam Long id) {
        try {
            userService.deleteUserById(id);
        } catch (UserValidationException uve) {
            return ResponseEntity.badRequest().body(uve.getMessage());
        }
        return ResponseEntity.ok().body("User deleted successfully");
    }

}
