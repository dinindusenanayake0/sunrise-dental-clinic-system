package com.dental.system.service;

import com.dental.system.dao.InUserDAO;
import com.dental.system.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;
    private FakeUserDAO fakeUserDAO;

    @BeforeEach
    void setUp() {
        fakeUserDAO = new FakeUserDAO();
        userService = new UserService(fakeUserDAO);
    }


    private User createValidUser() {

        return new User(
                1,
                "admin",
                "admin123",
                "Administrator"
        );
    }


    @Test
    void loginWithValidCredentialsShouldReturnUser() {

        User user = createValidUser();

        user.setPassword(
                BCrypt.hashpw(
                        "admin123",
                        BCrypt.gensalt()
                )
        );

        fakeUserDAO.loginUser = user;

        User result =
                userService.login(
                        "admin",
                        "admin123"
                );

        assertNotNull(result);

        assertEquals(
                "admin",
                result.getUsername()
        );
    }


    @Test
    void loginWithInvalidPasswordShouldReturnNull() {

        User user = createValidUser();

        user.setPassword(
                BCrypt.hashpw(
                        "admin123",
                        BCrypt.gensalt()
                )
        );

        fakeUserDAO.loginUser = user;

        User result =
                userService.login(
                        "admin",
                        "wrongPassword"
                );

        assertNull(result);
    }


    @Test
    void loginWithNullUsernameShouldReturnNull() {

        User result =
                userService.login(
                        null,
                        "admin123"
                );

        assertNull(result);
    }


    @Test
    void loginWithEmptyUsernameShouldReturnNull() {

        User result =
                userService.login(
                        "",
                        "admin123"
                );

        assertNull(result);
    }


    @Test
    void loginWithNullPasswordShouldReturnNull() {

        User result =
                userService.login(
                        "admin",
                        null
                );

        assertNull(result);
    }


    @Test
    void loginWithEmptyPasswordShouldReturnNull() {

        User result =
                userService.login(
                        "admin",
                        ""
                );

        assertNull(result);
    }


    @Test
    void addValidAdministratorShouldReturnTrue() {

        User user = createValidUser();

        boolean result =
                userService.addUser(user);

        assertTrue(result);

        assertEquals(
                "Administrator",
                user.getRole()
        );

        assertNotEquals(
                "admin123",
                user.getPassword()
        );

        assertTrue(
                BCrypt.checkpw(
                        "admin123",
                        user.getPassword()
                )
        );
    }


    @Test
    void addValidNormalUserShouldReturnTrue() {

        User user = new User(
                2,
                "staff",
                "staff123",
                "USER"
        );

        boolean result =
                userService.addUser(user);

        assertTrue(result);

        assertEquals(
                "User",
                user.getRole()
        );

        assertTrue(
                BCrypt.checkpw(
                        "staff123",
                        user.getPassword()
                )
        );
    }


    @Test
    void addNullUserShouldReturnFalse() {

        boolean result =
                userService.addUser(null);

        assertFalse(result);
    }


    @Test
    void addUserWithEmptyUsernameShouldReturnFalse() {

        User user = createValidUser();

        user.setUsername("");

        boolean result =
                userService.addUser(user);

        assertFalse(result);
    }


    @Test
    void addUserWithEmptyPasswordShouldReturnFalse() {

        User user = createValidUser();

        user.setPassword("");

        boolean result =
                userService.addUser(user);

        assertFalse(result);
    }


    @Test
    void addUserWithInvalidRoleShouldReturnFalse() {

        User user = createValidUser();

        user.setRole("Manager");

        boolean result =
                userService.addUser(user);

        assertFalse(result);
    }


    @Test
    void addUserWithDuplicateUsernameShouldReturnFalse() {

        User user = createValidUser();

        fakeUserDAO.existingUserByUsername =
                new User(
                        2,
                        "admin",
                        "password",
                        "User"
                );

        boolean result =
                userService.addUser(user);

        assertFalse(result);
    }


    @Test
    void addUserShouldTrimUsername() {

        User user = new User(
                2,
                "  staff  ",
                "staff123",
                "USER"
        );

        boolean result =
                userService.addUser(user);

        assertTrue(result);

        assertEquals(
                "staff",
                user.getUsername()
        );
    }


    @Test
    void getUserByValidIdShouldReturnUser() {

        User user = createValidUser();

        fakeUserDAO.userById = user;

        User result =
                userService.getUserById(1);

        assertNotNull(result);

        assertEquals(
                1,
                result.getUserId()
        );
    }


    @Test
    void getUserByInvalidIdShouldReturnNull() {

        User result =
                userService.getUserById(0);

        assertNull(result);
    }


    @Test
    void getAllUsersShouldReturnUsers() {

        fakeUserDAO.users.add(
                createValidUser()
        );

        List<User> result =
                userService.getAllUsers();

        assertNotNull(result);

        assertEquals(
                1,
                result.size()
        );
    }


    @Test
    void getAllUsersWhenDaoReturnsNullShouldReturnEmptyList() {

        fakeUserDAO.returnNullUserList = true;

        List<User> result =
                userService.getAllUsers();

        assertNotNull(result);

        assertTrue(
                result.isEmpty()
        );
    }


    @Test
    void updateValidUserShouldReturnTrue() {

        User user = createValidUser();

        fakeUserDAO.existingUserByUsername =
                user;

        boolean result =
                userService.updateUser(user);

        assertTrue(result);

        assertTrue(
                BCrypt.checkpw(
                        "admin123",
                        user.getPassword()
                )
        );
    }


    @Test
    void updateNullUserShouldReturnFalse() {

        boolean result =
                userService.updateUser(null);

        assertFalse(result);
    }


    @Test
    void updateUserWithInvalidIdShouldReturnFalse() {

        User user = createValidUser();

        user.setUserId(0);

        boolean result =
                userService.updateUser(user);

        assertFalse(result);
    }


    @Test
    void updateUserWithDuplicateUsernameShouldReturnFalse() {

        User user = createValidUser();

        user.setUserId(1);

        fakeUserDAO.existingUserByUsername =
                new User(
                        2,
                        "admin",
                        "password",
                        "User"
                );

        boolean result =
                userService.updateUser(user);

        assertFalse(result);
    }


    @Test
    void updateUserWithInvalidRoleShouldReturnFalse() {

        User user = createValidUser();

        user.setRole("Manager");

        boolean result =
                userService.updateUser(user);

        assertFalse(result);
    }


    @Test
    void updateUserShouldNormalizeRole() {

        User user = createValidUser();

        user.setRole("USER");

        boolean result =
                userService.updateUser(user);

        assertTrue(result);

        assertEquals(
                "User",
                user.getRole()
        );
    }


    @Test
    void deleteUserWithValidIdShouldReturnTrue() {

        boolean result =
                userService.deleteUser(1);

        assertTrue(result);
    }


    @Test
    void deleteUserWithInvalidIdShouldReturnFalse() {

        boolean result =
                userService.deleteUser(0);

        assertFalse(result);
    }


    private static class FakeUserDAO
            implements InUserDAO {

        User loginUser;
        User userById;
        User existingUserByUsername;

        boolean returnNullUserList = false;

        List<User> users =
                new ArrayList<>();


        @Override
        public User login(
                String username,
                String password
        ) {
            return loginUser;
        }


        @Override
        public boolean addUser(
                User user
        ) {
            return true;
        }


        @Override
        public User getUserById(
                int userId
        ) {
            return userById;
        }


        @Override
        public boolean updateUser(
                User user
        ) {
            return true;
        }


        @Override
        public boolean deleteUser(
                int userId
        ) {
            return true;
        }


        @Override
        public List<User> getAllUsers() {

            if (returnNullUserList) {
                return null;
            }

            return users;
        }


        @Override
        public User getUserByUsername(
                String username
        ) {
            return existingUserByUsername;
        }
    }
}