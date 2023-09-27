package ua.dragunovskiy.users_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.dragunovskiy.users_api.entity.User;
import ua.dragunovskiy.users_api.exception.MyInvalidUserDataException;
import ua.dragunovskiy.users_api.exception.MyUserNotFoundException;
import ua.dragunovskiy.users_api.service.UserService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public void createUser(@RequestBody User user) throws ParseException {
        if (user == null) {
            throw new MyUserNotFoundException("User not found");
        }
        userService.createUser(user);
    }

    @GetMapping("/list")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/listByBirthdate/{from}/{to}")
    public List<User> getUsersByBirthDate(@PathVariable("from") Date from, @PathVariable("to") Date to) {
       return userService.getUsersByBirthDate(from, to);
    }

    @PutMapping("/updateName/{newName}")
    public void updateFirstName(@RequestBody User user, @PathVariable("newName") String newName) {
        if (user == null) {
            throw new MyUserNotFoundException("User not found");
        }
        if (newName == null) {
            throw new MyInvalidUserDataException("Name is invalid");
        }
        userService.updateFirstName(user, newName);
    }

    @PutMapping("/updateAll/{newName}/{newLastName}/{newEmail}/{newBirthDate}")
    public void updateAllUserFields(@RequestBody User user, @PathVariable("newName") String newName,
                                    @PathVariable("newLastName") String newLastName, @PathVariable("newEmail") String newEmail,
                                    @PathVariable("newBirthDate") Date newBirthDate) {
        if (user == null) {
            throw new MyUserNotFoundException("User not found");
        }
        if (newName == null || newLastName == null || newEmail == null || newBirthDate == null) {
            throw new MyInvalidUserDataException("User data is invalid");
        }
        userService.updateAllFields(user, newName, newLastName, newEmail, newBirthDate);
    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(@RequestBody User user) {
        if (user == null) {
            throw new MyUserNotFoundException("User not found");
        }
        userService.deleteUser(user);
    }
}
