package ua.dragunovskiy.users_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.dragunovskiy.users_api.entity.User;
import ua.dragunovskiy.users_api.service.UserService;
import ua.dragunovskiy.users_api.storage.UsersStorage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;

    @Mock
    private SimpleDateFormat dateFormat;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService();
        userService.simpleDateFormat = dateFormat;
    }

    @Test
    public void testCreateUser() throws ParseException {
        User user = new User();
        user.setBirthdate(new Date());

        when(dateFormat.parse(anyString())).thenReturn(new Date());
        userService.createUser(user);

        List<User> users = UsersStorage.listOfUsers;
        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
    }

    @Test
    public void testGetUsers() {
        User user1 = new User();
        User user2 = new User();
        UsersStorage.listOfUsers.addAll(Arrays.asList(user1, user2));

        List<User> result = userService.getUsers();

        assertEquals(2, result.size());
        assertTrue(result.contains(user1));
        assertTrue(result.contains(user2));
    }

    @Test
    public void testGetUsersByBirthDate() {
        User user1 = new User();
        user1.setBirthdate(new Date());
        User user2 = new User();
        user2.setBirthdate(new Date(System.currentTimeMillis() - 86400000)); // One day ago
        UsersStorage.listOfUsers.addAll(Arrays.asList(user1, user2));

        Date fromDate = new Date(System.currentTimeMillis() - 100000000); // Some date in the past
        Date toDate = new Date();

        List<User> result = userService.getUsersByBirthDate(fromDate, toDate);

        assertEquals(1, result.size());
        assertTrue(result.contains(user2));
    }

    @Test
    public void testUpdateFirstName() {
        User user = new User();
        UsersStorage.listOfUsers.add(user);

        userService.updateFirstName(user, "NewName");

        assertEquals("NewName", user.getFirstname());
    }

    @Test
    public void testUpdateAllFields() {
        User user = new User();
        UsersStorage.listOfUsers.add(user);

        userService.updateAllFields(user, "NewFirst", "NewLast", "newemail@example.com", new Date());

        assertEquals("NewFirst", user.getFirstname());
        assertEquals("NewLast", user.getLastname());
        assertEquals("newemail@example.com", user.getEmail());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        UsersStorage.listOfUsers.add(user);

        userService.deleteUser(user);

        assertTrue(UsersStorage.listOfUsers.isEmpty());
    }

    @Test
    public void testFindUser() {
        User user = new User();
        user.setFirstname("John");
        UsersStorage.listOfUsers.add(user);

        User foundUser = userService.findUser(user);

        assertNotNull(foundUser);
        assertEquals("John", foundUser.getFirstname());
    }
}