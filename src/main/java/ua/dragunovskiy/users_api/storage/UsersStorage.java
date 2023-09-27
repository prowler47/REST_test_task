package ua.dragunovskiy.users_api.storage;

import org.springframework.stereotype.Component;
import ua.dragunovskiy.users_api.entity.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsersStorage {
    public static List<User> listOfUsers = new ArrayList<>();

}
