package be.xplore.demorest.controller;

import be.xplore.demorest.model.User;
import be.xplore.demorest.service.UserService;
import be.xplore.demorest.service.exceptions.UserValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
     * Gets all the groups from the database.
     * Filters can be passed via JSON
     *
     * {
     * "key1": "value",
     * "key2": "value"
     * }
     *
     * Possible key values:
     * - firstname
     * - lastname
     * - role
     *
     */

    @GetMapping("/list")
    public ResponseEntity findAllUsers(@RequestBody(required = false) Map<String, String> filters) {
        List<User> users = userService.searchUsers(filters);
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/")
    public ResponseEntity findUserByID(@RequestParam Long id){
        User foundUser;
        try {
            foundUser = userService.searchUserById(id);
        } catch (UserValidationException uve) {
            return ResponseEntity.badRequest().body(uve.getMessage());
        }
        return ResponseEntity.ok(foundUser);
    }

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody User user) {
        User createdUser;
        try {
            createdUser = userService.createUser(user);
        } catch (UserValidationException uve) {
            return ResponseEntity.unprocessableEntity().body(uve.getMessage());
        }
        return ResponseEntity.ok().body(createdUser);
    }

    @PutMapping("/update")
    public ResponseEntity updateUser(@RequestBody(required = true) User user) {
        User updatedUser;
        try {
            updatedUser = userService.updateUser(user);
        } catch (UserValidationException uve) {
            return ResponseEntity.unprocessableEntity().body(uve.getMessage());
        }
        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(@RequestParam Long id) {
        try {
            userService.deleteUserById(id);
        } catch (UserValidationException uve) {
            return ResponseEntity.badRequest().body(uve.getMessage());
        }
        return ResponseEntity.ok().body("User deleted successfully");
    }

}
