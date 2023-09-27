package ua.dragunovskiy.users_api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.dragunovskiy.users_api.entity.User;
import ua.dragunovskiy.users_api.storage.UsersStorage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Value("${birth.constraint}")
    private String birthConstraint;
    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void createUser(User user) throws ParseException {
        if (user.getBirthdate().before(simpleDateFormat.parse(birthConstraint))) {
            User newUser = new User();
            newUser.setFirstname(user.getFirstname());
            newUser.setLastname(user.getLastname());
            newUser.setEmail(user.getEmail());
            newUser.setBirthdate(user.getBirthdate());
            UsersStorage.listOfUsers.add(newUser);
            System.out.println(UsersStorage.listOfUsers);
        }
    }


    // it's method for my own testing
    public List<User> getUsers() {
        return UsersStorage.listOfUsers;
    }

    public List<User> getUsersByBirthDate(Date from, Date to) {
        List<User> listOfSuitableUsers = new ArrayList<>();
        for (User user : UsersStorage.listOfUsers) {
            if (user.getBirthdate().after(from) && user.getBirthdate().before(to)) {
                listOfSuitableUsers.add(user);
            }
        }
        return listOfSuitableUsers;
    }

    public void updateFirstName(User user, String newName) {
        User searchingUser = findUser(user);
        searchingUser.setFirstname(newName);
    }

    public void updateAllFields(User user, String firstname, String lastname, String email, Date birthdate) {
        User searchingUser = findUser(user);
        searchingUser.setFirstname(firstname);
        searchingUser.setLastname(lastname);
        searchingUser.setEmail(email);
        searchingUser.setBirthdate(birthdate);
    }

    public void deleteUser(User user) {
        User userForDelete = findUser(user);
        UsersStorage.listOfUsers.remove(userForDelete);
    }

    public User findUser(User user) {
        return UsersStorage.listOfUsers.stream()
                .filter(el -> el.getFirstname().equals(user.getFirstname()))
                .findFirst().orElse(null);
    }
}
